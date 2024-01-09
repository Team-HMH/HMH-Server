package sopt.org.HMH.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.HMH.domain.challenge.domain.exception.ChallengeSuccess;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.HMH.domain.challenge.service.ChallengeService;
import sopt.org.HMH.global.common.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ApiResponse> orderAdd(@RequestBody ChallengeRequest request) {
        // TODO: - 토큰으로 유저 아이디 찾는 함수 연결
        return ResponseEntity
                .status(ChallengeSuccess.SUCCESS_CREATE_CHALLENGE.getHttpStatus())
                .body(ApiResponse.success(ChallengeSuccess.SUCCESS_CREATE_CHALLENGE, challengeService.addChallenge(1L, request)));
    }
}