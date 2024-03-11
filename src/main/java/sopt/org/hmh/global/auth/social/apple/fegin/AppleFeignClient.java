package sopt.org.hmh.global.auth.social.apple.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import sopt.org.hmh.global.auth.social.apple.response.ApplePublicKeysResponse;

@FeignClient(name = "appleFeignClient", url = "${oauth2.apple.base-url}")
public interface AppleFeignClient {
    
    @GetMapping("/auth/keys")
    ApplePublicKeysResponse getApplePublicKeys();
}
