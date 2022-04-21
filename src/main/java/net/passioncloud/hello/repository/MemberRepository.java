package net.passioncloud.hello.repository;

import net.passioncloud.hello.model.Member;
import org.springframework.data.repository.CrudRepository;

// Will be auto implemented by Spring into a Bean called memberRepository
public interface MemberRepository extends CrudRepository<Member, Integer> {
}
