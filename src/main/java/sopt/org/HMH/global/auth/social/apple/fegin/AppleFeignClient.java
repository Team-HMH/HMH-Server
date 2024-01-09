package sopt.org.HMH.global.auth.social.apple.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleFeignClient", url = "${oauth2.apple.base-url}")
public interface AppleFeignClient {
    @GetMapping("/auth/keys")
    ApplePublicKeys getApplePublicKeys();
}
