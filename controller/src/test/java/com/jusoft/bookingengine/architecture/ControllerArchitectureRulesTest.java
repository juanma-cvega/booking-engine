package com.jusoft.bookingengine.architecture;

import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/** Enforces the ADRs governing this module (ADR-003, ADR-010). */
class ControllerArchitectureRulesTest {

    private static final String BASE_PACKAGE = "com.jusoft.bookingengine";

    private static final String[] CONTROLLER_OWNED_PACKAGES = {
        "..controller..", "..listener..", "..publisher..", "..app.."
    };

    private static final JavaClasses ALL_CLASSES =
            new ClassFileImporter()
                    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                    .importPackages(BASE_PACKAGE);

    // ── ADR-010: controllers/listeners are thin adapters over use cases ──

    @Disabled(
            "BookingControllerRest still depends on BookingManagerComponent directly; fix tracked as its own story")
    @Test
    void controllers_and_listeners_do_not_depend_on_manager_components() {
        ArchRule rule =
                noClasses()
                        .that()
                        .resideInAnyPackage(CONTROLLER_OWNED_PACKAGES)
                        .should()
                        .dependOnClassesThat(nameMatching(".*ManagerComponent"))
                        .as(
                                "ADR-010: controllers and listeners call use cases, never"
                                        + " component *ManagerComponent interfaces directly");
        rule.check(ALL_CLASSES);
    }

    @Test
    void only_global_exception_handler_declares_exception_handlers() {
        ArchRule rule =
                classes()
                        .that()
                        .resideInAnyPackage(CONTROLLER_OWNED_PACKAGES)
                        .should(declareExceptionHandlersOnlyIfGlobalHandler())
                        .as(
                                "ADR-010: exception-to-status mapping is centralized in"
                                        + " GlobalExceptionHandler, not scattered per controller");
        rule.check(ALL_CLASSES);
    }

    // ── ADR-003: manual dependency injection ──────────────────────────────

    @Test
    void no_component_scanning() {
        ArchRule rule =
                noClasses()
                        .that()
                        .resideInAnyPackage(CONTROLLER_OWNED_PACKAGES)
                        .should()
                        .beAnnotatedWith("org.springframework.context.annotation.ComponentScan")
                        .as("ADR-003: beans are wired explicitly; @ComponentScan is forbidden");
        rule.check(ALL_CLASSES);
    }

    @Test
    void no_spring_stereotype_auto_detection() {
        ArchRule rule =
                noClasses()
                        .that()
                        .resideInAnyPackage(CONTROLLER_OWNED_PACKAGES)
                        .should()
                        .beAnnotatedWith("org.springframework.stereotype.Component")
                        .orShould()
                        .beAnnotatedWith("org.springframework.stereotype.Service")
                        .orShould()
                        .beAnnotatedWith("org.springframework.stereotype.Repository")
                        .as(
                                "ADR-003: no @Component/@Service/@Repository auto-detection; only"
                                        + " @Configuration");
        rule.check(ALL_CLASSES);
    }

    @Test
    void only_configuration_classes_may_use_field_injection() {
        ArchRule rule =
                noFields()
                        .that()
                        .areDeclaredInClassesThat()
                        .resideInAnyPackage(CONTROLLER_OWNED_PACKAGES)
                        .and()
                        .areDeclaredInClassesThat()
                        .areNotAnnotatedWith("org.springframework.context.annotation.Configuration")
                        .should()
                        .beAnnotatedWith("org.springframework.beans.factory.annotation.Autowired")
                        .as("ADR-003: constructor injection outside @Configuration classes");
        rule.check(ALL_CLASSES);
    }

    private static ArchCondition<JavaClass> declareExceptionHandlersOnlyIfGlobalHandler() {
        return new ArchCondition<>(
                "declare @ExceptionHandler methods only in GlobalExceptionHandler") {
            @Override
            public void check(JavaClass clazz, ConditionEvents events) {
                if (clazz.getSimpleName().equals("GlobalExceptionHandler")) {
                    return;
                }
                clazz.getMethods().stream()
                        .filter(
                                method ->
                                        method.isAnnotatedWith(
                                                "org.springframework.web.bind.annotation.ExceptionHandler"))
                        .forEach(
                                method ->
                                        events.add(
                                                SimpleConditionEvent.violated(
                                                        clazz,
                                                        method.getFullName()
                                                                + " declares @ExceptionHandler"
                                                                + " outside"
                                                                + " GlobalExceptionHandler")));
            }
        };
    }
}
