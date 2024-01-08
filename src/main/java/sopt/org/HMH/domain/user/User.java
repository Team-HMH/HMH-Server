package sopt.org.HMH.domain.user;

import jakarta.persistence.*;
import sopt.org.HMH.global.common.domain.BaseTimeEntity;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Integer Id;
}
