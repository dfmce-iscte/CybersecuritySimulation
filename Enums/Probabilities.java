package Enums;

public enum Probabilities {

    TO_INFECTED(0.5),
    INFECTED__TO_REPAIRED(0.05),
    INFECTED__TO_BROKEN(0.05),
    START_INFECTED(0.4),
    CHOOSE_BEST_DIRECTION(0.35);

    private final double prob;

    Probabilities(double v) {
        prob = v;
    }

    public double getProb() {
        return prob;
    }
}
