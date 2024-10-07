package with_yu.domain.auth.dto;

public class LoginReq {
    public record General (
            String email,
            String password
    ) { }
}
