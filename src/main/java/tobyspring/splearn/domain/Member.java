package tobyspring.splearn.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.util.Assert;
import tobyspring.splearn.domain.Email;
import tobyspring.splearn.domain.MemberRegisterRequest;
import tobyspring.splearn.domain.MemberStatus;
import tobyspring.splearn.domain.PasswordEncoder;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Entity
@Getter
@ToString(callSuper = true, exclude = "detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private Email email;

    private String nickname;

    private String passwordHash;

    @Enumerated
    private MemberStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private MemberDetail detail;

    private Member(String email, String nickname, String passwordHash) {
        this.email = new Email(requireNonNull(email));
        this.nickname = requireNonNull(nickname);
        this.passwordHash = requireNonNull(passwordHash);
    }

    public void activate() {
        Assert.state(this.status == MemberStatus.PENDING, "가입대기 상태에서만 가입 완료 할 수 있다.");

        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        Assert.state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVED;
    }

    public static Member register(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
        return new Member(email, nickname, passwordEncoder.encode(password));
    }

    public static Member register(MemberRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(registerRequest.email());
        member.nickname = requireNonNull(registerRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(registerRequest.password()));
        member.status = MemberStatus.PENDING;
        member.detail = MemberDetail.create();
        return member;
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.nickname = Objects.requireNonNull(updateRequest.nickname());
        this.detail.updateInfo(updateRequest);
    }
    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(password);
    }
}
