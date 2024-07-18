package sopt.org.hmh.domain.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.app.domain.exception.AppSuccess;
import sopt.org.hmh.domain.app.dto.request.AppRemoveRequest;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppArrayRequest;
import sopt.org.hmh.domain.challenge.service.ChallengeFacade;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenge/app")
public class ChallengeAppController implements ChallengeAppApi {

    private final ChallengeFacade challengeFacade;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderAddApps(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody @Valid final ChallengeAppArrayRequest requests) {
        challengeFacade.addAppsToCurrentChallenge(userId, requests.apps(), os);

        return ResponseEntity
                .status(AppSuccess.ADD_APP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AppSuccess.ADD_APP_SUCCESS, new EmptyJsonResponse()));

    }

    @Override
    @DeleteMapping
    public ResponseEntity<BaseResponse<EmptyJsonResponse>> orderRemoveApp(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody @Valid final AppRemoveRequest request) {
        challengeFacade.removeAppFromCurrentChallenge(userId, request.appCode(), os);

        return ResponseEntity
                .status(AppSuccess.REMOVE_APP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AppSuccess.REMOVE_APP_SUCCESS, new EmptyJsonResponse()));
    }
}