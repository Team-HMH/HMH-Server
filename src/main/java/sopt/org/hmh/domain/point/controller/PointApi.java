package sopt.org.hmh.domain.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import sopt.org.hmh.domain.point.dto.request.ChallengeDateRequest;
import sopt.org.hmh.domain.point.dto.response.ChallengePointStatusListResponse;
import sopt.org.hmh.domain.point.dto.response.EarnPointResponse;
import sopt.org.hmh.domain.point.dto.response.EarnedPointResponse;
import sopt.org.hmh.domain.point.dto.response.UsagePointResponse;
import sopt.org.hmh.domain.point.dto.response.UsePointResponse;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.common.response.BaseResponse;

@Tag(name = "포인트 관련 API")
@SecurityRequirement(name = JwtConstants.AUTHORIZATION)
public interface PointApi {

    @Operation(
            summary = "포인트 수령 여부 리스트 조회 API",
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
    ResponseEntity<BaseResponse<ChallengePointStatusListResponse>> orderGetChallengePointStatusList(
            @Parameter(hidden = true) Long userId);

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
    ResponseEntity<BaseResponse<UsePointResponse>> orderUsagePointAndChallengeFailed(
            @Parameter(hidden = true) Long userId, ChallengeDateRequest challengeDateRequest);

    @Operation(
            summary = "포인트 받기 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "포인트 받기에 성공하였습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<EarnPointResponse>> orderEarnPointAndChallengeEarned(
            @Parameter(hidden = true) Long userId, ChallengeDateRequest challengeDateRequest);

    @Operation(
            summary = "사용할 포인트 받기 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "사용할 포인트 반환에 성공하였습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<UsagePointResponse>> orderGetUsagePoint();

    @Operation(
            summary = "받을 포인트 받기 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "사용할 포인트 반환에 성공하였습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<EarnedPointResponse>> orderGetEarnedPoint();
}
