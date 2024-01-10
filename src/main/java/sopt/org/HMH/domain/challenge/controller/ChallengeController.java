package sopt.org.HMH.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.HMH.domain.challenge.domain.exception.ChallengeSuccess;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.domain.challenge.dto.response.CreatedChallengeResponse;
import sopt.org.HMH.domain.challenge.service.ChallengeService;
import sopt.org.HMH.global.common.Util;
import sopt.org.HMH.global.common.response.ApiResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreatedChallengeResponse>> orderAddChallenge(
            Principal principal,
            @RequestHeader("OS") final String os,
            @RequestBody final ChallengeRequest request
    ) {
        return ResponseEntity
                .status(ChallengeSuccess.SUCCESS_CREATE_CHALLENGE.getHttpStatus())
                .body(ApiResponse.success(ChallengeSuccess.SUCCESS_CREATE_CHALLENGE, challengeService.addChallenge(Util.getUserId(principal), request, os)));
    }
}