package sopt.org.hmh.domain.banner;

import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public record BannerResponse(
        String title,
        String subTitle,
        String imageUrl,
        String linkUrl,
        List<String> backgroundColors

) {
    public static BannerResponse of(Banner banner) {
        return BannerResponse.builder()
                .title(banner.getTitle())
                .subTitle(banner.getSubTitle())
                .imageUrl(banner.getImageUrl())
                .linkUrl(banner.getLinkUrl())
                .backgroundColors(convertToColorArray(banner.getBackgroundColors()))
                .build();
    }

    private static List<String> convertToColorArray(String input) {
        String[] colorArray = input.split(",\\s*");
        return Arrays.asList(colorArray);
    }
}
