package sopt.org.hmh.domain.banner.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sopt.org.hmh.domain.banner.Banner;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BannerRepositoryImpl implements BannerRepository {

    private final BannerJpaRepository bannerJpaRepository;

    @Override
    public Optional<Banner> findTopByOrderByIdAsc() {
        return bannerJpaRepository.findTopByOrderByIdAsc();
    }
}
