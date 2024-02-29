package sopt.org.hmh.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sopt.org.hmh.global.common.exception.base.ErrorBase;
import sopt.org.hmh.global.common.exception.base.SuccessBase;


@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse<T> {

    private final int status;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> BaseResponse<T> success(SuccessBase success, T data) {
        return new BaseResponse<>(success.getHttpStatusCode(), success.getSuccessMessage(), data);
    }

    public static <T> BaseResponse<T> error(ErrorBase error) {
        return new BaseResponse<>(error.getHttpStatusCode(), error.getErrorMessage());
    }

    public static <T> BaseResponse<T> error(ErrorBase error, T data) {
        return new BaseResponse<>(error.getHttpStatusCode(), error.getErrorMessage(), data);
    }

    public static BaseResponse<Exception> error(ErrorBase error, Exception exception) {
        return new BaseResponse<>(error.getHttpStatusCode(), error.getErrorMessage(), exception);
    }
}