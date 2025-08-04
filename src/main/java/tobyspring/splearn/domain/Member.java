package tobyspring.splearn.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Getter
@ToString
public class Member {
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    private Member(String email, String nickname, String passwordHash) {
        this.email = new Email(Objects.requireNonNull(email));
        this.nickname = Objects.requireNonNull(nickname);
        this.passwordHash = Objects.requireNonNull(passwordHash);
    }

    public void activate() {
        Assert.state(this.status == MemberStatus.PENDING, "가입대기 상태에서만 가입 완료 할 수 있다.");

        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        Assert.state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVED;
    }

    public static Member create(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
        return new Member(email, nickname, passwordEncoder.encode(password));
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(password);
    }
}
