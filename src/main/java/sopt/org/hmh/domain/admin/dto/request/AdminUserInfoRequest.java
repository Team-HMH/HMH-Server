package sopt.org.hmh.domain.admin.dto.request;

public record AdminUserInfoRequest(
        Long userId,
        String name,
        Integer point
) {

}
