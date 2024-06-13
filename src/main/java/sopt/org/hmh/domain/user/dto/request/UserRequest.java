package sopt.org.hmh.domain.user.dto.request;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRequest {

    public record LockDateRequest(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate lockDate
    ) {
    }

    public record LockCheckDateRequest(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate lockCheckDate
    ) {
    }
}
