package com.jusoft.bookingengine.component.authorization;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.jusoft.bookingengine.util.LockingTemplate.withLock;

@AllArgsConstructor
public class MemberRepositoryInMemory implements MemberRepository {

  private final Map<Long, Member> store;
  private final Lock lock;

  @Override
  public Optional<Member> find(long memberId) {
    Optional<Member> memberFound = Optional.ofNullable(store.get(memberId));
    return memberFound.map(this::copyOf);
  }

  private Member copyOf(Member member) {
    return Member.of(member.getId(), member.getBuildings());
  }

  @Override
  public void save(Member member) {
    store.put(member.getId(), member);
  }

  @Override
  public void execute(long memberId, UnaryOperator<Member> function, Supplier<RuntimeException> notFoundExceptionSupplier) {
    withLock(lock, () -> {
      Member member = find(memberId).orElseThrow(notFoundExceptionSupplier);
      store.put(memberId, function.apply(member));
    });
  }
}
