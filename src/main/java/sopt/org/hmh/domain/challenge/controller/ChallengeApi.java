package sopt.org.hmh.domain.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.dto.response.DailyChallengeResponse;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.common.response.BaseResponse;
import sopt.org.hmh.global.common.response.EmptyJsonResponse;

@Tag(name = "챌린지 관련 API")
@SecurityRequirement(name = JwtConstants.AUTHORIZATION)
public interface ChallengeApi {

    @Operation(
            summary = "챌린지가 끝난 후 새 챌린지 생성하는 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "챌린지가 성공적으로 추가되었습니다."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<EmptyJsonResponse>> orderAddChallenge(
            @Parameter(hidden = true) final Long userId,
            final String os,
            final String timeZone,
            final ChallengeRequest request);

    @Operation(
            summary = "달성현황뷰 챌린지 정보를 불러오는 API",
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
    ResponseEntity<BaseResponse<ChallengeResponse>> orderGetChallenge(
            @Parameter(hidden = true) final Long userId,
            @RequestHeader final String timeZone);

    @Operation(
            summary = "이용시간 통계 정보를 불러오는 API",
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
    ResponseEntity<BaseResponse<DailyChallengeResponse>> orderGetDailyChallenge(
            @Parameter(hidden = true) final Long userId,
            @RequestHeader final String timeZone);
}

