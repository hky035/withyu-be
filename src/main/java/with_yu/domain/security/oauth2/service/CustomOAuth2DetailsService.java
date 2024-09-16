package with_yu.domain.security.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import with_yu.domain.security.oauth2.dto.CustomOAuth2User;
import with_yu.domain.security.oauth2.dto.KakaoResponse;
import with_yu.domain.security.oauth2.dto.KakaoUserInfoDto;
import with_yu.domain.security.oauth2.dto.OAuth2Resposne;
import with_yu.domain.user.entity.Role;
import with_yu.domain.user.entity.User;
import with_yu.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomOAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String accessToken = userRequest.getAccessToken().toString();
        System.out.println("token by OAuth2UserRequest: " + accessToken);
        // 액세스 토큰 및 리프레시 토큰 저장 필요 => 리프레시토큰은 자동갱신 된다는 이야기가 있는데 그러면 어디 저장하는 거지?

        String oAuth2Provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Resposne oAuth2Resposne = null;

        if(oAuth2Provider.equals("kakao")) {
            oAuth2Resposne = new KakaoResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String userIdInProvider = oAuth2Resposne.getIdInProvider();
        String provider = oAuth2Resposne.getProvider();

        User existUser = userRepository.findByProviderId(provider + " " + userIdInProvider);

        if(existUser == null) { // 한 번도 로그인 X => role만 GUEST로 부여하여서 로그인 시 signup페이지로 리다이렉트 되도록
            // 카카오 프로필 이미지 적용 등 새로운 User를 생성해야함
            User user = User.builder()
                    .email(oAuth2Resposne.getEmail())
                    .nickname(oAuth2Resposne.getName())
                    .providerId(oAuth2Resposne.getProvider() + " " + oAuth2Resposne.getIdInProvider())
                    .profileImageUrl(((KakaoResponse)oAuth2Resposne).getImageUrl().get("profile")) // check
                    .role(Role.GUEST)
                    .build();

            userRepository.save(user);
            existUser = user;

        } else { // 이미 존재하는 유저 => 이메일만 업데이트
            existUser.updateEmail(oAuth2Resposne.getEmail());

            userRepository.save(existUser);
        }


        return new CustomOAuth2User(KakaoUserInfoDto.of(existUser.getNickname(), existUser.getProviderId(), existUser.getRole(), existUser.getEmail()));
    }
}
