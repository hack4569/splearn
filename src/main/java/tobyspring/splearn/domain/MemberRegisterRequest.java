package tobyspring.splearn.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
@Getter
public record MemberRegisterRequest (
        @Email String email,
        @Size(min=5, max = 20) String nickname,
        @Size(min=8, max = 100) String password){

}
