package with_yu.domain.security.oauth2.dto;

import with_yu.domain.user.entity.Role;

public record KakaoUserInfoDto(
    String name,
    String id,
    Role role,
    String email
) {
    public static KakaoUserInfoDto of(String name, String id, Role role, String email){
        return new KakaoUserInfoDto(name,id,role,email);
    }
}
