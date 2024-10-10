package with_yu.domain.mail.dto;

public record CodeVerificationReq(
        String email,
        String code
) {
}
