package tobyspring.splearn.domain;

public class MemberFixture {
    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return new MemberRegisterRequest( "lsh@naver.com", "lsh", "secret");
    }
}
