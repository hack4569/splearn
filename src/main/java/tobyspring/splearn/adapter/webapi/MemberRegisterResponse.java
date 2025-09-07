package tobyspring.splearn.adapter.webapi;


import tobyspring.splearn.domain.Member;

public record MemberRegisterResponse(Long memberId, String email) {
    public static MemberRegisterResponse of(Member member) {
        return new MemberRegisterResponse(member.getId(), member.getEmail().address());
    }
}
