package sopt.org.hmh.domain.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "os는 null일 수 없습니다.")
    protected String os;

    @NotNull(message = "appCode는 null일 수 없습니다.")
    protected String appCode;
}