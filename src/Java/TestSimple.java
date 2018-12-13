package Java;

import Java.Controleur.ControleurSimpleGame;
import Java.Model.SimpleGame;
import Java.View.ViewCommande;
import Java.View.ViewGame;

public class TestSimple {

    /**
     * @param args
     */
    public static void main(String[] args) {

        SimpleGame g = new SimpleGame(20);
        ControleurSimpleGame controleurSimpleGame = new ControleurSimpleGame(g);
        ViewCommande view = new ViewCommande(g,controleurSimpleGame);
        ViewGame viewGame = new ViewGame(g);


    }

}
