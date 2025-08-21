package tobyspring.splearn.application.provided;

import tobyspring.splearn.domain.Member;

public interface MemberFinder {
    /**
     * 회원조회
     * @param memberId
     * @return
     */
    Member find(Long memberId);
}
