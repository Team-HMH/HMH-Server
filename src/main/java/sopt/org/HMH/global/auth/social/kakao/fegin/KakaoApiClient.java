package sopt.org.HMH.global.auth.social.kakao.fegin;

import org.springframework.http.HttpHeaders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import sopt.org.HMH.global.auth.social.kakao.response.KakaoUserResponse;

@FeignClient(name = "kakaoApiClient", url = "${oauth2.kakao.base-url}")
public interface KakaoApiClient {

    @GetMapping(value = "/v2/user/me")
    KakaoUserResponse getUserInformation(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}