package sopt.org.hmh.domain.dailychallenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import sopt.org.hmh.domain.app.dto.request.AppArrayUsageTimeRequest;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.common.response.BaseResponse;

@Tag(name = "일별챌린지 관련 API")
@SecurityRequirement(name = JwtConstants.AUTHORIZATION)
public interface DailyChallengeApi {

    @Operation(
            summary = "사용시간과 함께 일별챌린지 정보 업데이트 요청하는 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일별 챌린지 정보 업데이트에 성공했습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<?>> orderAddHistoryDailyChallenge(
            @UserId @Parameter(hidden = true) final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final AppArrayUsageTimeRequest request);

    @Operation(
            summary = "스크린타임 연장 시 챌린지 실패 처리하는 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "챌린지 실패 요청 성공했습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<?>> orderModifyDailyChallengeStatusFailure(
            @UserId @Parameter(hidden = true) final Long userId);
}

