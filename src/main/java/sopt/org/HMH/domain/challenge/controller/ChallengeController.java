package sopt.org.HMH.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.HMH.domain.challenge.domain.exception.ChallengeSuccess;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.domain.challenge.dto.response.AddChallengeResponse;
import sopt.org.HMH.domain.challenge.service.ChallengeService;
import sopt.org.HMH.global.common.UserIdConvertor;
import sopt.org.HMH.global.common.response.ApiResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ApiResponse<AddChallengeResponse>> orderAddChallenge(
            Principal principal,
            @RequestHeader("OS") final String os,
            @RequestBody final ChallengeRequest request
    ) {
        return ResponseEntity
                .status(ChallengeSuccess.ADD_CHALLENGE_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(ChallengeSuccess.ADD_CHALLENGE_SUCCESS,
                        challengeService.addChallenge(UserIdConvertor.getUserId(principal), request, os)));
    }
}