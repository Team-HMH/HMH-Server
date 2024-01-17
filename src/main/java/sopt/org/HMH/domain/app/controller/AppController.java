package sopt.org.HMH.domain.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.HMH.domain.app.domain.exception.AppSuccess;
import sopt.org.HMH.domain.app.dto.request.AppArrayGoalTimeRequest;
import sopt.org.HMH.domain.app.dto.request.AppDeleteRequest;
import sopt.org.HMH.domain.app.service.AppService;
import sopt.org.HMH.global.auth.UserId;
import sopt.org.HMH.global.common.response.ApiResponse;
import sopt.org.HMH.global.common.response.EmptyJsonResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class AppController {

    private final AppService appService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> orderAddApp(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppArrayGoalTimeRequest request
    ) {
        appService.addAppsAndUpdateRemainingDailyChallenge(userId, request.apps(), os);
        return ResponseEntity
                .status(AppSuccess.ADD_APP_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(AppSuccess.ADD_APP_SUCCESS, new EmptyJsonResponse()));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> orderRemoveApp(
            @UserId final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppDeleteRequest request
    ) {
        appService.removeAppAndUpdateRemainingDailyChallenge(userId, request, os);
        return ResponseEntity
                .status(AppSuccess.DELETE_APP_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(AppSuccess.DELETE_APP_SUCCESS, new EmptyJsonResponse()));
    }
}
