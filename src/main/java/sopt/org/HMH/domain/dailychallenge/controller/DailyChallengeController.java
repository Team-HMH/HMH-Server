package sopt.org.HMH.domain.dailychallenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.HMH.domain.challenge.domain.exception.ChallengeSuccess;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.domain.dailychallenge.domain.exception.DailyChallengeSuccess;
import sopt.org.HMH.domain.dailychallenge.dto.response.DailyChallengeResponse;
import sopt.org.HMH.domain.dailychallenge.repository.DailyChallengeRepository;
import sopt.org.HMH.domain.dailychallenge.service.DailyChallengeService;
import sopt.org.HMH.global.common.UserIdConvertor;
import sopt.org.HMH.global.common.response.ApiResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dayChallenge")
public class DailyChallengeController {

    private final DailyChallengeRepository dailyChallengeRepository;
    private final DailyChallengeService dailyChallengeService;

    @GetMapping
    public ResponseEntity<ApiResponse<DailyChallengeResponse>> orderDetailDailyChallenge(
            Principal principal,
            @RequestHeader("OS") final String os,
            @RequestBody final ChallengeRequest request
    ) {
        return ResponseEntity
                .status(DailyChallengeSuccess.GET_DAY_CHALLENGE_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(DailyChallengeSuccess.GET_DAY_CHALLENGE_SUCCESS,
                        dailyChallengeService.getChallenge(UserIdConvertor.getUserId(principal), request, os)));
    }
}
