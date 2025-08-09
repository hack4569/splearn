package tobyspring.splearn.domain;

import lombok.Getter;

@Getter
public class MemberRegisterRequest {
    private String email;
    private String nickname;
    private String password;
}
