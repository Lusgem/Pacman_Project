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
import Java.Strategie.StrategieJoueur2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Vue principale du jeu, contient la labirynthe, le score, le temps restant (en fait ce sont les périodes restantes) et le nb de vies restants au pacman
 */
public class ViewGame extends JFrame implements Observateur {
    private Game game;
    private ControleurGame controleurGame;
    private JPanel panelScore;
    private JLabel labelTours = new JLabel();
    private JLabel labelScore = new JLabel();
    private JLabel labelVies = new JLabel();
    private PanelPacmanGame pacmanPanel;
    Maze maze;
    ArrayList<PositionAgent> posFantomes= new ArrayList<>();
    ArrayList<PositionAgent> posPacman= new ArrayList<>();
    private StrategieJoueur1 strategieJoueur1 = StrategieJoueur1.getInstance();
    private StrategieJoueur2 strategieJoueur2 = StrategieJoueur2.getInstance();


    public ViewGame(Game game){

        game.enregistrerObservateur(this);
        this.game = game;
        maze = null;

        addKeyListener(strategieJoueur1);
        addKeyListener(strategieJoueur2);
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


        panelScore = new JPanel(new GridLayout(1,3));
        labelTours.setText("Temps restant : "+(game.getTours_max()-game.getCompteur()));
        panelScore.add(labelTours);
        if(game instanceof PacmanGame){
            labelScore.setText("Score : "+((PacmanGame) game).getScore());
            labelVies.setText("Vies restantes : "+((PacmanGame) game).getVies());
            panelScore.add(labelScore);
            panelScore.add(labelVies);
        }

        pacmanPanel= new PanelPacmanGame(maze);

        try {
            add(panelScore,BorderLayout.NORTH);
            add(pacmanPanel,BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    /**
     * Mets à jour la vue (score, temps, vies restantes et positions des différents agents
     */
    @Override
    public void actualiser() {
        labelTours.setText("Temps restant: "+(game.getTours_max()-game.getCompteur()));
        posFantomes.clear();
        posPacman.clear();
        if (game instanceof PacmanGame){
            labelScore.setText("Score : "+((PacmanGame) game).getScore());
            labelVies.setText("Vies restantes : "+((PacmanGame) game).getVies());
            for(Agent a : ((PacmanGame)game).getFantomesAgents()){
                posFantomes.add(a.getPositionCourante());
            }
            pacmanPanel.setGhosts_pos(posFantomes);
            for (Agent a: ((PacmanGame) game).getPacmanAgents()){
                posPacman.add(a.getPositionCourante());
            }
            pacmanPanel.setPacmans_pos(posPacman);
            if(!((PacmanGame) game).getFantomesAgents().isEmpty() && ((PacmanGame) game).resteFantomesVulnerables()){
                pacmanPanel.setGhostsScarred(true);
            }
            else
                pacmanPanel.setGhostsScarred(false);

            pacmanPanel.repaint();

        }

    }
}
