package tobyspring.splearn.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
public class MemberRegisterRequest {
    private String email;
    private String nickname;
    private String password;
}
