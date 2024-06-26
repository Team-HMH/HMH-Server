package sopt.org.hmh.domain.point.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.challenge.dto.request.ChallengeDateRequest;
import sopt.org.hmh.domain.challenge.domain.ChallengeConstants;
import sopt.org.hmh.domain.point.dto.response.*;
import sopt.org.hmh.domain.point.exception.PointSuccess;
import sopt.org.hmh.domain.point.service.PointFacade;
import sopt.org.hmh.global.auth.UserId;
import sopt.org.hmh.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/point")
public class PointController implements PointApi {

    private final PointFacade pointFacade;

    @Override
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<ChallengePointStatusListResponse>> orderGetChallengePointStatusList(
            @UserId final Long userId
    ) {
        return ResponseEntity
                .status(PointSuccess.GET_CHALLENGE_POINT_STATUS_LIST_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(PointSuccess.GET_CHALLENGE_POINT_STATUS_LIST_SUCCESS,
                        pointFacade.getChallengePointStatusList(userId)));

    }

    @Override
    @PatchMapping("/use")
    public ResponseEntity<BaseResponse<UsePointResponse>> orderUsagePointAndChallengeFailed(
            @UserId final Long userId,
            @RequestBody @Valid final ChallengeDateRequest challengeDateRequest
    ) {
        return ResponseEntity
                .status(PointSuccess.POINT_USAGE_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(PointSuccess.POINT_USAGE_SUCCESS,
                        pointFacade.usePointAndChallengeFailed(userId, challengeDateRequest.challengeDate())));
    }

    @Override
    @PatchMapping("/earn")
    public ResponseEntity<BaseResponse<EarnPointResponse>> orderEarnPointAndChallengeEarned(
            @UserId final Long userId,
            @RequestBody final ChallengeDateRequest challengeDateRequest
    ) {
        return ResponseEntity
                .status(PointSuccess.POINT_EARN_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(PointSuccess.POINT_EARN_SUCCESS,
                        pointFacade.earnPointAndChallengeEarned(userId, challengeDateRequest.challengeDate())));
    }

    @Override
    @GetMapping("/earn")
    public ResponseEntity<BaseResponse<EarnedPointResponse>> orderGetEarnedPoint() {
        return ResponseEntity
                .status(PointSuccess.GET_EARNED_POINT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(PointSuccess.GET_EARNED_POINT_SUCCESS,
                        new EarnedPointResponse(ChallengeConstants.EARNED_POINT)));
    }

    @Override
    @GetMapping("/use")
    public ResponseEntity<BaseResponse<UsagePointResponse>> orderGetUsagePoint() {
        return ResponseEntity
                .status(PointSuccess.GET_USAGE_POINT_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(PointSuccess.GET_USAGE_POINT_SUCCESS,
                        new UsagePointResponse(ChallengeConstants.USAGE_POINT)));
    }
}
