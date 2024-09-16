package with_yu.domain.user.dto;

import lombok.RequiredArgsConstructor;

public record SignupReq(
        String name,
        String studentNumber,
        String department,
        String phoneNumber
) {

}
