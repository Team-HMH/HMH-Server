package sopt.org.hmh.domain.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.app.domain.exception.AppSuccess;
import sopt.org.hmh.domain.app.dto.request.AppArrayGoalTimeRequest;
import sopt.org.hmh.domain.app.dto.request.AppDeleteRequest;
import sopt.org.hmh.domain.app.service.AppService;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class AppController implements AppApi {

    private final AppService appService;

    @PostMapping
    public ResponseEntity<BaseResponse<?>> orderAddApp(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppArrayGoalTimeRequest request
    ) {
        appService.addAppsAndUpdateRemainingDailyChallenge(userId, request.apps(), os);
        return ResponseEntity
                .status(AppSuccess.ADD_APP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AppSuccess.ADD_APP_SUCCESS, new EmptyJsonResponse()));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<?>> orderRemoveApp(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppDeleteRequest request
    ) {
        appService.removeAppAndUpdateRemainingDailyChallenge(userId, request, os);
        return ResponseEntity
                .status(AppSuccess.DELETE_APP_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(AppSuccess.DELETE_APP_SUCCESS, new EmptyJsonResponse()));
    }
}
