import javax.swing.*;
import java.awt.*;

public class ViewGame extends JFrame implements Observateur{
    private SimpleGame game;
    private ControleurGame controleurGame;
    private JPanel panelGlobal;
    private JLabel labelTours=new JLabel();
    private PanelPacmanGame pacmanPanel;

    {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ViewGame(SimpleGame game){
        game.enregistrerObservateur(this);
        this.game = game;
        Maze maze = null;
        try {
            maze = new Maze("./src/layouts/bigCorners.lay");
        } catch (Exception e) {
            e.printStackTrace();
        }
        pacmanPanel = new PanelPacmanGame(maze);
        controleurGame = new ControleurSimpleGame(game);
        setTitle("Pacman");
        setSize(maze.getSizeX()*15,maze.getSizeY()*15);
        panelGlobal = new JPanel(new GridLayout(2,1));
        labelTours.setText("Tour : 0");
        //labelTours.setPreferredSize(new Dimension(100,100));
        panelGlobal.add(labelTours,CENTER_ALIGNMENT);
        //panelGlobal.setSize(100,500);
        pacmanPanel.setPreferredSize(new Dimension(500,500));

        try {
            add(panelGlobal,BorderLayout.NORTH);
            add(pacmanPanel,BorderLayout.SOUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    public void actualiser() {
        labelTours.setText("Tour : "+game.getCompteur());

    }
}
