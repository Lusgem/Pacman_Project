package Java.Strategie;

import Java.Agent;
import Java.AgentAction;
import Java.Maze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class StrategieJoueur2 implements Strategie, KeyListener {
    AgentAction action = new AgentAction(Maze.WEST);
    private static StrategieJoueur2 strategieJoueur2 = new StrategieJoueur2();

    private StrategieJoueur2(){
        super();
    }

    public static StrategieJoueur2 getInstance() {
        if (strategieJoueur2 == null) {
            synchronized (StrategieJoueur2.class) {
                if (strategieJoueur2 == null) {
                    strategieJoueur2 = new StrategieJoueur2();
                }
            }
        }
        return strategieJoueur2;
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
            case KeyEvent.VK_Q:
                action = new AgentAction(Maze.WEST);
                break;
            case KeyEvent.VK_Z:
                action = new AgentAction(Maze.NORTH);
                break;
            case KeyEvent.VK_S:
                action = new AgentAction(Maze.SOUTH);
                break;
            case KeyEvent.VK_D:
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
