package tobyspring.splearn.application.required;

import org.springframework.data.repository.Repository;
import tobyspring.splearn.application.provided.MemberRegister;
import tobyspring.splearn.domain.Email;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberRegisterRequest;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> register(MemberRegisterRequest registerRequest);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);
}
