package sopt.org.hmh.domain.banner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.hmh.domain.banner.BannerResponse;
import sopt.org.hmh.domain.banner.BannerService;
import sopt.org.hmh.domain.banner.exception.BannerSuccess;
import sopt.org.hmh.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/banner")
public class BannerController implements BannerApi {

    private final BannerService bannerService;

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<BannerResponse>> orderGetBanner() {
        return ResponseEntity
                .status(BannerSuccess.GET_BANNER_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(BannerSuccess.GET_BANNER_SUCCESS, bannerService.getBanner()));
    }
}
