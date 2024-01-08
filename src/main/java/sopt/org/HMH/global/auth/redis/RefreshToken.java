package sopt.org.HMH.global.auth.redis;

import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "refresh")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    private Long userId;

    private String refreshToken;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Integer expiration;
}