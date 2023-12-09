package Enums;

public enum Variables {

    N_COLS(8),
    N_ROWS(8),
    N_VEHICLES((int) (N_COLS.getValue() * N_ROWS.getValue() * 0.20)),
    N_ATTRACTORS((int) (N_COLS.getValue() * N_ROWS.getValue() * 0.05)),
    ;
    private final int value;

    Variables(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
