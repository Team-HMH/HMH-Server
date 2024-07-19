package sopt.org.hmh.domain.challenge.dto.request;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sopt.org.hmh.domain.app.domain.ChallengeApp;
import sopt.org.hmh.domain.app.dto.request.ChallengeAppRequest;
import sopt.org.hmh.domain.challenge.domain.Challenge;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewChallengeOrder {

    private final ChallengeRequest challengeRequest;
    private final List<ChallengeAppRequest> challengeAppRequests;
    private final Long userId;
    private final String os;
    private final ZoneId zoneId;
    private final boolean isFirstChallenge;

    public static NewChallengeOrder createFirstChallengeOrder(
            ChallengeRequest challengeRequest, List<ChallengeAppRequest> challengeAppRequests,
            Long userId, String os, String timeZone) {
        return new NewChallengeOrder(challengeRequest, challengeAppRequests,
                userId, os, ZoneId.of(timeZone), true);
    }

    public static NewChallengeOrder createNextChallengeOrder(
            ChallengeRequest challengeRequest, Long userId, String os, String timeZone
    ) {
        return new NewChallengeOrder(
                challengeRequest, List.of(), userId, os, ZoneId.of(timeZone), false);
    }

    public Challenge toChallengeEntity() {
        return Challenge.builder()
                .period(challengeRequest.period())
                .userId(userId)
                .goalTime(challengeRequest.goalTime())
                .startDate(LocalDate.now(zoneId))
                .build();
    }

    public List<ChallengeApp> toChallengeAppEntities(Challenge challenge) {
        return challengeAppRequests.stream()
                .map(challengeAppRequest -> challengeAppRequest.toEntity(challenge, os))
                .toList();
    }
}
