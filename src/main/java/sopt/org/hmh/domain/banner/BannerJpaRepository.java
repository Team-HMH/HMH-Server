package sopt.org.hmh.domain.banner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerJpaRepository extends JpaRepository<Banner, Long> {
    Optional<Banner> findTopByOrderByIdAsc(); // 디비에 배너 하나만 존재
}
