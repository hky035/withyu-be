package with_yu.domain.security.oauth2.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final KakaoUserInfoDto kakaoUserInfo;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(kakaoUserInfo.role()));
        return authorities;
    }

    @Override
    public String getName() {
        return kakaoUserInfo.name();
    }

    public String getEmail() {
        return kakaoUserInfo.email();
    }

    public String getId(){
        return kakaoUserInfo.id();
    }


}
