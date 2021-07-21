package com.octopusneko.neko.miner.model;

public enum MatchState {
    NOT_STARTED(0),
    FIRST_HALF(1),
    HALF_TIME(2),
    SECOND_HALF(3),
    ADDITION_TIME(4),
    PENALTY_KICK(5),

    FINISHED(-1),
    CANCELED(-10),
    TBD(-11),
    CUT_OFF(-12),
    INTERRUPTED(-13),
    DEFERRED(-14),
    ;

    private final int state;

    MatchState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public static MatchState from(int state) {
        switch (state) {
            case 0:
                return NOT_STARTED;
            case 1:
                return FIRST_HALF;
            case 2:
                return HALF_TIME;
            case 3:
                return SECOND_HALF;
            case 4:
                return ADDITION_TIME;
            case 5:
                return PENALTY_KICK;
            case -1:
                return FINISHED;
            case -10:
                return CANCELED;
            case -11:
                return TBD;
            case -12:
                return CUT_OFF;
            case -13:
                return INTERRUPTED;
            case -14:
                return DEFERRED;
            default:
                throw new IllegalArgumentException(String.format("Unknown state %d", state));
        }
    }
}
