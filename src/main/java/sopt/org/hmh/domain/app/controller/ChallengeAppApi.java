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
import sopt.org.hmh.domain.app.dto.request.AppRemoveRequest;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppArrayRequest;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@Tag(name = "챌린지 관련 API")
@SecurityRequirement(name = JwtConstants.AUTHORIZATION)
public interface ChallengeAppApi {

    @Operation(
            summary = "스크린타임 설정할 앱을 추가하는 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "챌린지 정보 조회에 성공했습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<EmptyJsonResponse>> orderAddApps(
            @Parameter(hidden = true) Long userId,
            @RequestHeader("OS") String os,
            @RequestBody ChallengeAppArrayRequest requests);

    @Operation(
            summary = "스크린타임 설정한 앱을 삭제하는 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "챌린지 정보 조회에 성공했습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<EmptyJsonResponse>> orderRemoveApp(
            @Parameter(hidden = true) Long userId,
            @RequestHeader("OS") String os,
            @RequestBody AppRemoveRequest request);
}