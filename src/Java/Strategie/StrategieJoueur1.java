package Java.Strategie;

import Java.Agent;
import Java.AgentAction;
import Java.Maze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Cette classe représente la stratégie du joueur1
 * Elle est aussi une classe Singleton car elle ne doit pas être dupliquée (à cause du Key Listener)
 * Elle permet de gérer les actions du joueur au clavier
 */
public class StrategieJoueur1 implements Strategie, KeyListener {

    AgentAction action = new AgentAction(Maze.WEST);
    private static StrategieJoueur1 strategieJoueur1 = new StrategieJoueur1();

    private StrategieJoueur1(){
        super();
    }

    public static StrategieJoueur1 getInstance() {
        if (strategieJoueur1 == null) {
            synchronized (StrategieJoueur1.class) {
                if (strategieJoueur1 == null) {
                    strategieJoueur1 = new StrategieJoueur1();
                }
            }
        }
        return strategieJoueur1;
    }

    @Override
    public AgentAction jouer(Agent a, Maze maze, ArrayList<Agent> ennemies, ArrayList<Agent> allies) {
        return action;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        switch(code) {
            case KeyEvent.VK_LEFT:
                action = new AgentAction(Maze.WEST);
                break;
            case KeyEvent.VK_UP:
                action = new AgentAction(Maze.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                action = new AgentAction(Maze.SOUTH);
                break;
            case KeyEvent.VK_RIGHT:
                action = new AgentAction(Maze.EAST);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
