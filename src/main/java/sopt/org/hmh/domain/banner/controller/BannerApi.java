package sopt.org.hmh.domain.banner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import sopt.org.hmh.domain.banner.BannerResponse;
import sopt.org.hmh.global.common.response.BaseResponse;

@Tag(name = "배너 관련 API")
public interface BannerApi {

    @Operation(
            summary = "배너 정보를 불러오는 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "배너 정보 불러오기에 성공하였습니다."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "배너를 찾을 수 없습니다.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content)})
    ResponseEntity<BaseResponse<BannerResponse>> orderGetBanner();
}
