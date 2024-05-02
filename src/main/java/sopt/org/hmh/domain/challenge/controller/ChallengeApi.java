package sopt.org.hmh.domain.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.hmh.domain.app.dto.request.AppArrayGoalTimeRequest;
import sopt.org.hmh.domain.app.dto.request.AppRemoveRequest;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeRequest;
import sopt.org.hmh.domain.challenge.dto.response.ChallengeResponse;
import sopt.org.hmh.domain.challenge.dto.response.DailyChallengeResponse;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.auth.jwt.JwtConstants;
import sopt.org.hmh.global.common.response.BaseResponse;

@Tag(name = "챌린지 관련 API")
@SecurityRequirement(name = JwtConstants.AUTHORIZATION)
public interface ChallengeApi {

    @PostMapping
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
    ResponseEntity<BaseResponse<?>> orderAddChallenge(
            @UserId @Parameter(hidden = true) final Long userId,
            @RequestHeader("OS") final String os,
            @RequestBody final ChallengeRequest request);

    @GetMapping
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
            @UserId @Parameter(hidden = true) final Long userId,
            @RequestHeader("OS") final String os);

    @GetMapping("/home")
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
            @UserId @Parameter(hidden = true) final Long userId,
            @RequestHeader("OS") final String os);

    @PostMapping("/app")
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
    ResponseEntity<BaseResponse<?>> orderAddApps(@UserId @Parameter(hidden = true) Long userId,
                                                 @RequestHeader("OS") String os,
                                                 @RequestBody AppArrayGoalTimeRequest requests);

    @GetMapping("/app")
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
    ResponseEntity<BaseResponse<?>> orderRemoveApp(@UserId Long userId,
                                                   @RequestHeader("OS") String os,
                                                   @RequestBody AppRemoveRequest request);
}

