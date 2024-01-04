package sopt.org.HMH.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt.org.HMH.global.common.exception.base.ErrorBase;
import sopt.org.HMH.global.common.exception.base.SuccessBase;


@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static ApiResponse<?> success(SuccessBase success) {
        return new ApiResponse<>(success.getHttpStatusCode(), success.getSuccessMessage());
    }

    public static <T> ApiResponse<T> success(SuccessBase success, T data) {
        return new ApiResponse<>(success.getHttpStatusCode(), success.getSuccessMessage(), data);
    }

    public static <T> ApiResponse<T> error(ErrorBase error) {
        return new ApiResponse<>(error.getHttpStatusCode(), error.getErrorMessage());
    }

    public static <T> ApiResponse<T> error(ErrorBase error, T data) {
        return new ApiResponse<>(error.getHttpStatusCode(), error.getErrorMessage(), data);
    }

    public static ApiResponse<Exception> error(ErrorBase error, Exception exception) {
        return new ApiResponse<>(error.getHttpStatusCode(), error.getErrorMessage(), exception);
    }
}