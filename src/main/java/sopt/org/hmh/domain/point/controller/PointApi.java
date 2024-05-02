package sopt.org.hmh.domain.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import sopt.org.hmh.domain.point.dto.response.PointUsageResponse;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.common.response.BaseResponse;

@Tag(name = "포인트 관련 API")
@SecurityRequirement(name = JwtConstants.AUTHORIZATION)
public interface PointApi {

    @Operation(
            summary = "포인트 사용 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "포인트 사용에 성공하였습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<PointUsageResponse>> orderChallengeFailureByUsagePoint(
            @Parameter(hidden = true) Long userId, LocalDate challengeDate);
}
