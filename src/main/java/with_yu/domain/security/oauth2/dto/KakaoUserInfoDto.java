package with_yu.domain.security.oauth2.dto;

public record KakaoUserInfoDto(
    String name,
    String id,
    String role,
    String email
) {
    public KakaoUserInfoDto of(String name, String id, String role, String email){
        return new KakaoUserInfoDto(name,id,role,email);
    }
}
