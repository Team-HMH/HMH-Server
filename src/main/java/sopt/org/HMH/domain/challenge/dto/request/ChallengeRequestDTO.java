package sopt.org.HMH.domain.challenge.dto.request;

import sopt.org.HMH.domain.app.dto.response.AppResponse;

import java.util.List;

public record ChallengeRequestDTO(
        Integer period,
        Long goalTime,
        List<AppResponse> apps
) {
}
