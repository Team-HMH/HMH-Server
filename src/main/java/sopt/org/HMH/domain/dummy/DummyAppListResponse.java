package sopt.org.HMH.domain.dummy;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public record DummyAppListResponse(
        List<DummyApp> apps
) {
        public static DummyAppListResponse of() {
                List<DummyApp> dummyAppList = new ArrayList<>();
                dummyAppList.add(new DummyApp("Netflix","https://github.com/Team-HMH/HMH-Server/assets/69035864/dd068b83-641a-4bff-b4f5-381ea6e04d44",2_400_000L,1_320_000L));
                dummyAppList.add(new DummyApp("Instagram","https://github.com/Team-HMH/HMH-Server/assets/69035864/bd572377-9dd9-47e7-a9f4-9efbbe66cd2e",5_400_000L,2_880_000L));
                dummyAppList.add(new DummyApp("YouTube","https://github.com/Team-HMH/HMH-Server/assets/69035864/8afa60c0-bf1d-4ff0-9b50-4d1558a71f0a",3_300_000L,900_000L));
                return new DummyAppListResponse(dummyAppList);
        }
}
