package with_yu.domain.security.oauth2.dto;

public interface OAuth2Resposne {
    String getProvider();
    String getIdInProvider();
    String getEmail();
    String getName();
}
