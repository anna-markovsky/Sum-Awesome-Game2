import cmpt213.A4.model.Game;
import cmpt213.A4.userinterface.StatsTracker;
import cmpt213.A4.model.WeaponManager;
import cmpt213.A4.userinterface.TextUI;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        TextUI ui = new TextUI(game);
        WeaponManager weaponManager = new WeaponManager(game);
        StatsTracker statsTracker = new StatsTracker(game);
        ui.playGame();
    }
}