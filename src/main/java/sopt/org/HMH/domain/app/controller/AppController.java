package sopt.org.HMH.domain.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.HMH.domain.app.domain.exception.AppSuccess;
import sopt.org.HMH.domain.app.dto.request.AppArrayGoalTimeRequest;
import sopt.org.HMH.domain.app.dto.request.AppDeleteRequest;
import sopt.org.HMH.domain.app.service.AppService;
import sopt.org.HMH.global.auth.jwt.JwtProvider;
import sopt.org.HMH.global.common.response.ApiResponse;
import sopt.org.HMH.global.common.response.EmptyJsonResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class AppController {

    private final AppService appService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> orderAddApp(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestHeader("OS") final String os,
            @RequestBody final AppArrayGoalTimeRequest request
    ) {
        appService.addAppsByUserId(socialAccessToken, request.apps());
        return ResponseEntity
                .status(AppSuccess.SUCCESS_ADD_APP.getHttpStatus())
                .body(ApiResponse.success(AppSuccess.SUCCESS_ADD_APP, new EmptyJsonResponse()));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> orderRemoveApp(
            @RequestHeader("Authorization") final String socialAccessToken,
            @RequestHeader("OS") final String os,
            @RequestBody final AppDeleteRequest request
    ) {
        appService.removeApp(socialAccessToken, request);
        return ResponseEntity
                .status(AppSuccess.SUCCESS_DELETE_APP.getHttpStatus())
                .body(ApiResponse.success(AppSuccess.SUCCESS_DELETE_APP, new EmptyJsonResponse()));
    }
}
