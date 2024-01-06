package sopt.org.HMH.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.HMH.domain.challenge.dto.request.ChallengeRequestDTO;
import sopt.org.HMH.domain.challenge.service.ChallengeService;
import sopt.org.HMH.global.common.response.ApiResponse;

import java.security.Principal;

import static sopt.org.HMH.global.common.exception.GlobalSuccess.SUCCESS_CREATE_CHALLENGE;
import static sopt.org.HMH.global.common.response.ApiResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ApiResponse> orderAdd(Principal principal, @RequestBody ChallengeRequestDTO request) {
        Long response = challengeService.add(1L, request);
        return ResponseEntity
                .status(SUCCESS_CREATE_CHALLENGE.getHttpStatus())
                .body(success(SUCCESS_CREATE_CHALLENGE, response));
    }
}
