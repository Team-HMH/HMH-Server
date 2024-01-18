package sopt.org.HMH.domain.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.HMH.global.common.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dummy")
@Deprecated
public class DummyAppController {
    @GetMapping("/app")
    public ResponseEntity<ApiResponse<?>> orderModifyDailyChallenge() {
        return ResponseEntity
                .status(DummyAppSuccess.GET_DUMMY_SUCCESS.getHttpStatus())
                .body(ApiResponse.success(DummyAppSuccess.GET_DUMMY_SUCCESS, DummyAppListResponse.of()));
    }
}
