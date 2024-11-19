package sopt.org.hmh.domain.banner.repository;

import sopt.org.hmh.domain.banner.Banner;

import java.util.Optional;

public interface BannerRepository {
    Optional<Banner> findTopByOrderByIdAsc();
}
