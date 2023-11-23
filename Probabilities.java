public enum Probabilities {
    NON_INFECTED__TO_INFECTED(0.2),
    INFECTED__TO_REPAIRED(0.3),
    INFECTED__TO_BROKEN(0.3),
    START_INFECTED(0.2);


    private final double prob;

    Probabilities(double v) {
        prob=v;
    }

    public double getProb() {
        return prob;
    }

}
