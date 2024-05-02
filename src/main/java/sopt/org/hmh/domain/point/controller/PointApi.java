package sopt.org.hmh.domain.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.common.response.BaseResponse;

@Tag(name = "포인트 관련 API")
@SecurityRequirement(name = JwtConstants.AUTHORIZATION)
public interface PointApi {
    @PostMapping("/failure")
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
    ResponseEntity<BaseResponse<?>> orderChallengeFailureByUsagePoint(
            @UserId @Parameter(hidden = true) Long userId);
}
