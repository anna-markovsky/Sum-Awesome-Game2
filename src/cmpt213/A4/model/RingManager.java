package cmpt213.A4.model;

import java.util.ArrayList;
import java.util.List;

public class RingManager {
    private static final int MAX_RINGS = 3;

    private final List<Ring> equippedRings = new ArrayList<>();
    private final List<Ring> allRings = new ArrayList<>();
    private List<String> activationMsgs = new ArrayList<>();

    public RingManager() {
        createDefaultRings();
    }

    public List<Ring> getAllRings(){
        return allRings;
    }

    private void createDefaultRings() {

        allRings.add(new Ring("", 1.0, new RingCondition() {
            @Override
            public boolean isActive(int strength) {
                return false;
            }
        }));

        allRings.add(new Ring(
                "The Big One", 1.5,
                new RingCondition() {
                    @Override
                    public boolean isActive(int strength) {
                        return strength >= 160;
                    }
                }
        ));

        allRings.add(new Ring(
                "The Little One", 1.5,
                new RingCondition() {
                    @Override
                    public boolean isActive(int strength) {
                        return strength <= 90;
                    }
                }
        ));

        allRings.add(new Ring(
                "Ring of Ten-acity", 1.5,
                new RingCondition() {
                    @Override
                    public boolean isActive(int strength) {
                        return strength % 10 == 0;
                    }
                }
        ));

        allRings.add(new Ring(
                "Ring of Meh", 1.1,
                new RingCondition() {
                    @Override
                    public boolean isActive(int strength) {
                        return strength % 5 == 0;
                    }
                }
        ));

        allRings.add(new Ring(
                "The Prime Directive", 2.0,
                new RingCondition() {
                    @Override
                    public boolean isActive(int strength) {
                        if (strength < 2) {
                            return false;
                        }
                        for (int i = 2; i * i <= strength; i++) {
                            if (strength % i == 0) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
        ));

        allRings.add(new Ring(
                "The Two Ring", 11.0,
                new RingCondition() {
                    @Override
                    public boolean isActive(int strength) {
                        if (strength <= 0) {
                            return false;
                        }
                        return (strength & (strength - 1)) == 0; // power of 2 check
                    }
                }
        ));
    }

    public void equipRing(Ring ring) {
        if (equippedRings.size() >= MAX_RINGS) {
            throw new IllegalStateException("Cannot equip more than " + MAX_RINGS + " rings");
        }
        equippedRings.add(ring);
    }

    public void unequipRing(int index) {
        equippedRings.remove(index);
    }

    public List<Ring> getEquippedRings() {
        return new ArrayList<>(equippedRings); // defensive copy
    }

    public int calculateTotalMultiplier(int strength) {
        double total = strength;
        for (Ring ring : equippedRings) {
            double m = ring.getMultiplierFor(strength);
            if(m > 1){
                activationMsgs.add(ring.getActivationMsg());
            }
            total *= m;
        }

        long rounded = Math.round(total);
        return (int) rounded;
    }

    public List<String> getActivationMsgs() {
        return activationMsgs;
    }
}
