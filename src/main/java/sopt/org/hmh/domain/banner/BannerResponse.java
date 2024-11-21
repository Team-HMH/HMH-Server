package sopt.org.hmh.domain.banner;

import lombok.Builder;

@Builder
public record BannerResponse(
        String title,
        String subTitle,
        String imageUrl,
        String linkUrl
) {
    public static BannerResponse of(Banner banner) {
        return BannerResponse.builder()
                .title(banner.getTitle())
                .subTitle(banner.getSubTitle())
                .imageUrl(banner.getImageUrl())
                .linkUrl(banner.getLinkUrl())
                .build();
    }
}
