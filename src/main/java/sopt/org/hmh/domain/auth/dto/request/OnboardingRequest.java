package sopt.org.hmh.domain.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record OnboardingRequest(
        String averageUseTime,

        @JsonProperty(value = "problem")
        List<String> problemList
) {
}
