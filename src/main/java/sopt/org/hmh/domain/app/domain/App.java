package sopt.org.hmh.domain.app.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class App {

    protected String os;

    protected String appCode;
}