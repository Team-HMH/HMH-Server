package sopt.org.HMH.domain.challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import sopt.org.HMH.domain.app.dto.request.AppGoalTimeRequest;
import java.util.List;

public record ChallengeRequest(

        Integer period,
        Long goalTime,

        @JsonProperty(value = "apps")
        List<AppGoalTimeRequest> apps
) {
}