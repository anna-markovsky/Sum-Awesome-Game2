import cmpt213.A4.model.Game;
import cmpt213.A4.userinterface.TextUI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Game game = new Game();
    TextUI ui = new TextUI(game);
    ui.playGame();
}
