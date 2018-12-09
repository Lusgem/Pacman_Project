import javax.swing.*;
import java.awt.*;

public class ViewGame extends JFrame implements Observateur{
    private Game game;
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

    public ViewGame(Game game){

        game.enregistrerObservateur(this);
        this.game = game;
        Maze maze = null;


        if(game instanceof SimpleGame){
            try {
                maze = new Maze("./src/layouts/bigCorners.lay");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (game instanceof PacmanGame){
            maze = ((PacmanGame) game).getMaze();
        }


        pacmanPanel = new PanelPacmanGame(maze);
        controleurGame = new ControleurSimpleGame(game);
        setTitle("Pacman");
        setSize(new Dimension(maze.getSizeX()*30,maze.getSizeY()*30));

        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width;
        int dy = centerPoint.y - windowSize.height;
        setLocation(dx, dy);

        panelGlobal = new JPanel(new GridLayout(2,1));
        labelTours.setText("Tour : 0");
        //labelTours.setPreferredSize(new Dimension(100,100));
        panelGlobal.add(labelTours,CENTER_ALIGNMENT);
        //panelGlobal.setSize(100,500);
        pacmanPanel= new PanelPacmanGame(maze);

        try {
            add(panelGlobal,BorderLayout.NORTH);
            add(pacmanPanel,BorderLayout.CENTER);
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
