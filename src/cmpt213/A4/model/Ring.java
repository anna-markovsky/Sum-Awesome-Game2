package cmpt213.A4.model;

interface RingCondition {
    boolean isActive(int strength);
}

public class Ring {
    private final String name;
    private final double bonusMultiplier;
    private final RingCondition condition;
    private final String activationMsg;

    public Ring(String name, double bonusMultiplier, RingCondition condition) {
        this.name = name;
        this.bonusMultiplier = bonusMultiplier;
        this.condition = condition;
        activationMsg = name + " adds " + convertMultiplierToPercentage() + "% bonus damage.";
    }

    public String getActivationMsg() {
        return activationMsg;
    }

    private int convertMultiplierToPercentage(){
        return (int) Math.round((bonusMultiplier - 1) * 100);
    }

    public String getName() {
        return name;
    }

    public double getMultiplierFor(int strength) {
        return condition.isActive(strength) ? bonusMultiplier : 1.0;
    }
}