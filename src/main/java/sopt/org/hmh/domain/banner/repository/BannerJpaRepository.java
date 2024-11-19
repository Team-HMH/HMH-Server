package sopt.org.hmh.domain.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.org.hmh.domain.banner.Banner;

import java.util.Optional;

public interface BannerJpaRepository extends JpaRepository<Banner, Long> {
    Optional<Banner> findTopByOrderByIdAsc(); // 디비에 배너 하나만 존재
}
