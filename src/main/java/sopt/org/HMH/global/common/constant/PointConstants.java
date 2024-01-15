package sopt.org.HMH.global.common.constant;

public enum PointConstants {

    INITIAL_POINT(0),
    ;

    private final int point;

    PointConstants(int point) {
        this.point = point;
    }

    public Integer getPoint() {
        return point;
    }
}