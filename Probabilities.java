public enum Probabilities {
    TO_INFECTED(0.2),
    INFECTED__TO_REPAIRED(0.3),
    INFECTED__TO_BROKEN(0.3),
    START_INFECTED(0.2),
    CHOOSE_BEST_DIRECTION(0.5);


    private final double prob;

    Probabilities(double v) {
        prob = v;
    }

    public double getProb() {
        return prob;
    }

}
