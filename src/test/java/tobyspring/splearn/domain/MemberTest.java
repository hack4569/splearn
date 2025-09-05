package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
         member = Member.register("hack4569@naver.com", "lsh", "secret", passwordEncoder);
    }
    @Test
    void active() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        member.activate();
        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changePassword() {
        member.changePassword("verysecret2", passwordEncoder);

        assertThat(member.verifyPassword("verysecret2", passwordEncoder)).isTrue();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() ->
                Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void updateInfo() {
        member.activate();

        var request = new MemberInfoUpdateRequest("Leo", "toby100", "자기소개");
        member.updateInfo(request);

        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(request.introduction());
    }

    @Test
    void updateInfoFail() {
        assertThatThrownBy(() -> {
            var request = new MemberInfoUpdateRequest("Leo", "toby100", "자기소개");
            member.updateInfo(request);
        }).isInstanceOf(IllegalStateException.class);
    }
}