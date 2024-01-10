package sopt.org.HMH.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record OnboardingRequest(
        String averageUseTime,

        @JsonProperty(value = "problem")
        List<String> problemList
) {
}
