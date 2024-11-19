package sopt.org.hmh.domain.banner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.hmh.domain.banner.exception.BannerError;
import sopt.org.hmh.domain.banner.exception.BannerException;
import sopt.org.hmh.domain.banner.repository.BannerRepository;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerResponse getBanner() {
        return bannerRepository.findTopByOrderByIdAsc()
                .map(BannerResponse::of)
                .orElseThrow(() -> new BannerException(BannerError.BANNER_NOT_FOUND));
    }
}
