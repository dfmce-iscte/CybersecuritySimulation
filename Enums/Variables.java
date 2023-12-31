package Enums;

public enum Variables {

    N_COLS(10),
    N_ROWS(10),
    N_VEHICLES((int) (N_COLS.getValue() * N_ROWS.getValue() * 0.2) + 1),

    N_ATTRACTORS((int) (N_COLS.getValue() * N_ROWS.getValue() * 0.05) + 1),
    ;
    private final int value;

    Variables(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
