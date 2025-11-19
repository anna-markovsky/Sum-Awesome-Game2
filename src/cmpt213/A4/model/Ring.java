package cmpt213.A4.model;

interface RingCondition {
    boolean isActive(int strength);
}

public class Ring {
    private final String name;
    private final double bonusMultiplier; // e.g. 1.5 = +50%, 1.1 = +10%
    private final RingCondition condition;

    public Ring(String name, double bonusMultiplier, RingCondition condition) {
        this.name = name;
        this.bonusMultiplier = bonusMultiplier;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public double getMultiplierFor(int strength) {
        return condition.isActive(strength) ? bonusMultiplier : 1.0;
    }
}
