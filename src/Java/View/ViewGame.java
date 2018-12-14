package Java.View;

import Java.*;
import Java.Controleur.ControleurGame;
import Java.Controleur.ControleurSimpleGame;
import Java.Etat.EtatVulnerable;
import Java.Model.Game;
import Java.Model.PacmanGame;
import Java.Model.SimpleGame;
import Java.Strategie.Strategie;
import Java.Strategie.StrategieJoueur1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewGame extends JFrame implements Observateur {
    private Game game;
    private ControleurGame controleurGame;
    private JPanel panelGlobal;
    private JLabel labelTours=new JLabel();
    private PanelPacmanGame pacmanPanel;
    Maze maze;
    ArrayList<PositionAgent> posFantomes= new ArrayList<>();
    ArrayList<PositionAgent> posPacman= new ArrayList<>();
    public StrategieJoueur1 strategieJoueur1 = StrategieJoueur1.getInstance();

    {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ViewGame(Game game){

        game.enregistrerObservateur(this);
        this.game = game;
        maze = null;

        addKeyListener(strategieJoueur1);
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
        labelTours.setText("Temps restant : "+(game.getTours_max()-game.getCompteur()));
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
        labelTours.setText("Temps restant: "+(game.getTours_max()-game.getCompteur()));
        posFantomes.clear();
        posPacman.clear();
        if (game instanceof PacmanGame){
            for(Agent a : ((PacmanGame)game).getFantomesAgents()){
                posFantomes.add(a.getPositionCourante());
            }
            pacmanPanel.setGhosts_pos(posFantomes);
            for (Agent a: ((PacmanGame) game).getPacmanAgents()){
                posPacman.add(a.getPositionCourante());
            }
            pacmanPanel.setPacmans_pos(posPacman);
            if(!((PacmanGame) game).getFantomesAgents().isEmpty() && ((PacmanGame) game).getFantomesAgents().get(0).getEtat() instanceof EtatVulnerable){
                pacmanPanel.setGhostsScarred(true);
            }
            else
                pacmanPanel.setGhostsScarred(false);

            pacmanPanel.repaint();

        }

    }
}
