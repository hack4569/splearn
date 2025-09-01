package tobyspring.splearn.application.provided;

import jakarta.validation.Valid;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberInfoUpdateRequest;
import tobyspring.splearn.domain.MemberRegisterRequest;

import java.util.Optional;

public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest registerRequest);
    Member activate(Long memberId);
    Member deactivate(Long memberId);
    Member updateInfo(Long memberId, @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);
}
