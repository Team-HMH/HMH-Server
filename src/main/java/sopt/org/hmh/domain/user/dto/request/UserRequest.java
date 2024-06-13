package sopt.org.hmh.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRequest {

    public record LockDateRequest(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @NotNull(message = "잠금 날짜는 null일 수 없습니다.")
            LocalDate lockDate
    ) {
    }

}
