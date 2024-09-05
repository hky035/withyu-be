package with_yu.domain.security.oauth2.dto;

import java.util.HashMap;
import java.util.Map;

public class KakaoResponse implements OAuth2Resposne{

    private final Map<String, Object> attributes;
    private final Map<String, Object> kakaoAcountAttributes;
    private final Map<String, Object> profileAttributes;
    private final Map<String, Object> properties;

    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties = (Map<String, Object>) attributes.get("properties");
        this.kakaoAcountAttributes = (Map<String, Object>) attributes.get("kakao_account");
        this.profileAttributes = (Map<String, Object>) kakaoAcountAttributes.get("profile");
    }


    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getIdInProvider() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return profileAttributes.get("email").toString();
    }

    @Override
    public String getName() {
        return profileAttributes.get("nickname").toString();
    }

    public Map<String, String> getImageUrl(){
        Map<String, String> imgAttr = new HashMap<String, String>();
        imgAttr.put("profile", properties.get("profile_image").toString());
        imgAttr.put("thumnail", properties.get("thumnail_image").toString());
        return imgAttr;
    }
}
