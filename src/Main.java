import cmpt213.A4.model.Game;
import cmpt213.A4.model.StatsTracker;
import cmpt213.A4.model.WeaponManager;
import cmpt213.A4.userinterface.TextUI;

public class Main {//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

    // click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Game game = new Game();
        TextUI ui = new TextUI(game);
        WeaponManager weaponManager = new WeaponManager(game);
        StatsTracker statsTracker = new StatsTracker(game);
        ui.playGame();
    }
}