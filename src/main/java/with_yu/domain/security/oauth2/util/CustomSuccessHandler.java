package with_yu.domain.security.oauth2.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import with_yu.domain.security.oauth2.dto.CustomOAuth2User;
import with_yu.domain.user.entity.Role;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String role = customOAuth2User.getAuthorities().iterator().next().getAuthority();
        System.out.println("role : " + role);
        System.out.println("role equals ROLE.USER : " + role.equals(Role.USER));
        System.out.println("role equals ROLE.GUEST : " + role.equals(Role.GUEST));

        if(role.equals(Role.GUEST)){
             response.sendRedirect("http://localhost:5173/signup"); // 회원가입 페이지
        } else if ( role.equals(Role.USER)){

            response.sendRedirect("http://localhost:5173");         // 메인 페이지

            // 토큰 발급 로직 추가 필요
        }
    }
}
