package cmpt213.A4.model;

import java.util.*;
/**
 * A class for managing, creating and storing weapons. This class creates the weapon objects and assigns an
 * interface for checking if a weapon is activated depending on the fill condition of the current match.
 */
interface WeaponCondition {
    boolean isActive(FillConditions fillConditions);
}
public class WeaponManager {
    private Game game;
    public List<Weapon> allWeapons = new ArrayList<>();
    public WeaponManager() {
        createAllWeapons(game.getFillConditions());
    }
    public WeaponManager(Game game) {
        this.game = game;
        this.allWeapons.addAll(createAllWeapons(game.getFillConditions()));
        registerAsObserver();
    }
    public List<Weapon> getAllWeapons() {
        return allWeapons;
    }
    private void registerAsObserver() {
        game.addObserver(new PlayerAttackObserver() {
            @Override
            public void attackStateChanged() {
            }
        });
    }
    private int assignRandomOpponentDamage() {
        Random random = new Random();
        int index = random.nextInt(game.getOpponents().size());
        return index;
    }
    private void equipRandomWeapon() {
        FillConditions fillConditions = game.getFillConditions();
        Player player = game.getPlayer();
        Random random = new Random();
        int index = random.nextInt(allWeapons.size());
        player.equipWeapon(allWeapons.get(index));
    }
    private List<Weapon> createAllWeapons(FillConditions fillConditions) {
        List<Weapon> qualifingWeapons = new ArrayList<>();
        int randIndex = assignRandomOpponentDamage();
        int colIndex = game.getFillConditions().getLastSelectedColIndex();
        Weapon frostBow = new Weapon();
        frostBow.assignWeapon(
                "frost bow",
                new double[]{1.0, 1.0, 1.0},
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkAddedCellsAscending();
                    }
                }
        );
        qualifingWeapons.add(frostBow);
        Weapon diamondSword = new Weapon();
        diamondSword.assignWeapon(
                "diamond sword",
                diamondSword.assignDamagePercentages(colIndex, 1.0, 0.75),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkAddedCellsDescending();
                    }
                }
        );
        qualifingWeapons.add(diamondSword);
        Weapon fireStaff = new Weapon();
        fireStaff.assignWeapon(
                "fire staff",
                fireStaff.assignDamagePercentages(colIndex, 1.0, 0.75),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkNumFills(15);
                    }
                }
        );
        qualifingWeapons.add(fireStaff);
        Weapon stoneHammer = new Weapon();
        stoneHammer.assignWeapon(
                "stone hammer",
                stoneHammer.assignDamagePercentages(colIndex, 0.8, 0.8),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkNumFills(10);
                    }
                }
        );
        qualifingWeapons.add(stoneHammer);
        Weapon lightningWand = new Weapon();
        lightningWand.assignWeapon(
                "lightning wand",
                lightningWand.assignDamagePercentages(randIndex, 1.0, 0.0),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkTime(10);
                    }
                }
        );
        qualifingWeapons.add(lightningWand);
        Weapon sparkleDagger = new Weapon();
        sparkleDagger.assignWeapon(
                "sparkle dagger",
                sparkleDagger.assignDamagePercentages(randIndex, 0.5, 0.0),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkTime(20);
                    }
                }
        );
        qualifingWeapons.add(sparkleDagger);
        if (allWeapons.isEmpty()) {
            NullWeapon nullWeapon = new NullWeapon();
            nullWeapon.assignWeapon(
                    "",
                    nullWeapon.assignDamagePercentages(randIndex, 0.0, 0.0),
                    new WeaponCondition() {
                        @Override
                        public boolean isActive(FillConditions fillConditions) {
                            return false;
                        }
                    }
            );
        }
        return qualifingWeapons;
    }
}