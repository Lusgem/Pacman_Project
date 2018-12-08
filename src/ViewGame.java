import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewGame extends JFrame {
    private SimpleGame game;
    private ControleurGame controleurGame;

    public ViewGame(SimpleGame game){
        this.game = game;
        controleurGame = new ControleurSimpleGame(game);
        setTitle("Pacman");
        try {
            add(new PanelPacmanGame(new Maze("./src/layouts/bigCorners.lay")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(500,500);
        setVisible(true);
    }
}
