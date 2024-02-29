package sopt.org.hmh.domain.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import sopt.org.hmh.domain.app.dto.request.AppArrayGoalTimeRequest;
import sopt.org.hmh.domain.app.dto.request.AppDeleteRequest;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.common.response.BaseResponse;

@Tag(name = "스크린타임 앱 관련 API")
@SecurityRequirement(name = JwtConstants.AUTHORIZATION)
public interface AppApi {
    @Operation(
            summary = "스크린타임 제한 앱 추가 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앱이 성공적으로 추가되었습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<?>> orderAddApp(
            @UserId @Parameter(hidden = true) final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppArrayGoalTimeRequest request);

    @Operation(
            summary = "스크린타임 제한 앱 삭제 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앱이 성공적으로 삭제되었습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<?>> orderRemoveApp(
            @UserId @Parameter(hidden = true) final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppDeleteRequest request);
}