package tobyspring.splearn.application.required;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register("lsh@naver.com", "lsh", "secret", MemberFixture.createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        //flush되어야 저장됨
        entityManager.flush();
        entityManager.clear();
        var found = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(found.getDetail().getRegisteredAt()).isNotNull();

    }

    @Test
    void duplicateEmailFail() {
        Member member = Member.register("lsh@naver.com", "lsh", "secret", MemberFixture.createPasswordEncoder());
        memberRepository.save(member);

        Member member2 = Member.register("lsh@naver.com", "lsh", "secret", MemberFixture.createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}