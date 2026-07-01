package com.jusoft.bookingengine.architecture;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.Test;

/** Enforces the domain's architecture decisions (docs/design-decisions.md). */
class ArchitectureRulesTest {

    private static final String BASE_PACKAGE = "com.jusoft.bookingengine";

    // timer is a shared kernel of value types, not a domain component (see timer/COMPONENT.md);
    // exempt from the api/ boundary rules.
    private static final String SHARED_KERNEL_PACKAGE = "..component.timer..";
    private static final String SHARED_KERNEL_COMPONENT = "timer";

    private static final JavaClasses DOMAIN_CLASSES =
            new ClassFileImporter()
                    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                    .importPackages(BASE_PACKAGE);

    // ── ADR-003: Manual dependency injection configuration ──────────────────────────────

    @Test
    void no_component_scanning() {
        ArchRule rule =
                noClasses()
                        .should()
                        .beAnnotatedWith("org.springframework.context.annotation.ComponentScan")
                        .as("ADR-003: beans are wired explicitly; @ComponentScan is forbidden");
        rule.check(DOMAIN_CLASSES);
    }

    @Test
    void no_spring_stereotype_auto_detection() {
        ArchRule rule =
                noClasses()
                        .should()
                        .beAnnotatedWith("org.springframework.stereotype.Component")
                        .orShould()
                        .beAnnotatedWith("org.springframework.stereotype.Service")
                        .orShould()
                        .beAnnotatedWith("org.springframework.stereotype.Repository")
                        .as(
                                "ADR-003: no @Component/@Service/@Repository auto-detection; only @Configuration");
        rule.check(DOMAIN_CLASSES);
    }

    @Test
    void only_configuration_classes_may_use_field_injection() {
        ArchRule rule =
                noFields()
                        .that()
                        .areDeclaredInClassesThat()
                        .areNotAnnotatedWith("org.springframework.context.annotation.Configuration")
                        .should()
                        .beAnnotatedWith("org.springframework.beans.factory.annotation.Autowired")
                        .as("ADR-003: constructor injection outside @Configuration classes");
        rule.check(DOMAIN_CLASSES);
    }

    // ── ADR-006: Component-based architecture (package-private internals) ────────────────

    @Test
    void in_memory_repositories_are_not_public() {
        ArchRule rule =
                classes()
                        .that()
                        .haveSimpleNameEndingWith("RepositoryInMemory")
                        .should()
                        .notHaveModifier(JavaModifier.PUBLIC)
                        .as("ADR-006: repository implementations are package-private");
        rule.check(DOMAIN_CLASSES);
    }

    @Test
    void component_implementations_are_not_public() {
        ArchRule rule =
                classes()
                        .that()
                        .haveSimpleNameEndingWith("ManagerComponentImpl")
                        .should()
                        .notHaveModifier(JavaModifier.PUBLIC)
                        .as("ADR-006: component implementations are package-private");
        rule.check(DOMAIN_CLASSES);
    }

    @Test
    void api_packages_do_not_depend_on_implementation_details() {
        ArchRule rule =
                noClasses()
                        .that()
                        .resideInAPackage("..component..")
                        .and()
                        .resideInAPackage("..api..")
                        .should()
                        .dependOnClassesThat(
                                resideInAPackage("..component..")
                                        .and(resideOutsideOfPackage("..api.."))
                                        .and(resideOutsideOfPackage(SHARED_KERNEL_PACKAGE)))
                        .as(
                                "ADR-006: api/ holds contracts only and must not reach into implementation classes");
        rule.check(DOMAIN_CLASSES);
    }

    @Test
    void components_interact_only_through_their_api() {
        ArchRule rule =
                classes()
                        .that()
                        .resideInAPackage("..component..")
                        .and()
                        .resideOutsideOfPackage("..api..")
                        .should(notDependOnAnotherComponentsInternals())
                        .as(
                                "ADR-006/ADR-007: components collaborate through api/ and events, not internals");
        rule.check(DOMAIN_CLASSES);
    }

    // ── ADR-002: Use case layer as application boundary ─────────────────────────────────

    @Test
    void use_cases_depend_only_on_component_api() {
        ArchRule rule =
                noClasses()
                        .that()
                        .resideInAPackage("..usecase..")
                        .should()
                        .dependOnClassesThat(
                                resideInAPackage("..component..")
                                        .and(resideOutsideOfPackage("..api.."))
                                        .and(resideOutsideOfPackage(SHARED_KERNEL_PACKAGE)))
                        .as(
                                "ADR-002: use cases depend on component api/ interfaces, never implementations");
        rule.check(DOMAIN_CLASSES);
    }

    @Test
    void each_use_case_exposes_a_single_public_method() {
        ArchRule rule =
                classes()
                        .that()
                        .haveSimpleNameEndingWith("UseCase")
                        .should(haveExactlyOnePublicMethod())
                        .as("ADR-002: a use case is a single operation with one public method");
        rule.check(DOMAIN_CLASSES);
    }

    // ── Domain entities: effectively immutable ──────────────────────────────────────────

    @Test
    void domain_state_is_final() {
        ArchRule rule =
                fields().that()
                        .areNotStatic()
                        .and()
                        .areDeclaredInClassesThat()
                        .resideInAPackage("..component..")
                        .and()
                        .areDeclaredInClassesThat()
                        .areNotAnnotatedWith("org.springframework.context.annotation.Configuration")
                        .should()
                        .beFinal()
                        .as(
                                "Domain state is immutable: non-static fields in domain classes are final");
        rule.check(DOMAIN_CLASSES);
    }

    // ── Custom conditions ────────────────────────────────────────────────────────────────

    private static ArchCondition<JavaClass> haveExactlyOnePublicMethod() {
        return new ArchCondition<>("have exactly one public method") {
            @Override
            public void check(JavaClass clazz, ConditionEvents events) {
                long publicMethods =
                        clazz.getMethods().stream()
                                .filter(
                                        method ->
                                                method.getModifiers().contains(JavaModifier.PUBLIC))
                                .count();
                if (publicMethods != 1) {
                    events.add(
                            SimpleConditionEvent.violated(
                                    clazz,
                                    clazz.getFullName()
                                            + " has "
                                            + publicMethods
                                            + " public methods (a use case must expose exactly one)"));
                }
            }
        };
    }

    private static ArchCondition<JavaClass> notDependOnAnotherComponentsInternals() {
        return new ArchCondition<>("not depend on another component's internal classes") {
            @Override
            public void check(JavaClass clazz, ConditionEvents events) {
                String owningComponent = componentOf(clazz);
                if (owningComponent == null) {
                    return;
                }
                clazz.getDirectDependenciesFromSelf().stream()
                        .map(dependency -> dependency.getTargetClass().getBaseComponentType())
                        .forEach(
                                target -> {
                                    String targetComponent = componentOf(target);
                                    boolean crossesInternalBoundary =
                                            targetComponent != null
                                                    && !targetComponent.equals(owningComponent)
                                                    && !targetComponent.equals(
                                                            SHARED_KERNEL_COMPONENT)
                                                    && !isApi(target);
                                    if (crossesInternalBoundary) {
                                        events.add(
                                                SimpleConditionEvent.violated(
                                                        clazz,
                                                        clazz.getFullName()
                                                                + " reaches into '"
                                                                + targetComponent
                                                                + "' internals via "
                                                                + target.getFullName()
                                                                + " (must go through its api/)"));
                                    }
                                });
            }
        };
    }

    private static String componentOf(JavaClass clazz) {
        String marker = ".component.";
        String packageName = clazz.getPackageName();
        int index = packageName.indexOf(marker);
        if (index < 0) {
            return null;
        }
        String remainder = packageName.substring(index + marker.length());
        int nextDot = remainder.indexOf('.');
        return nextDot < 0 ? remainder : remainder.substring(0, nextDot);
    }

    private static boolean isApi(JavaClass clazz) {
        return clazz.getPackageName().contains(".api");
    }
}
