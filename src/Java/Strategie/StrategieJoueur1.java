package Java.Strategie;

import Java.Agent;
import Java.AgentAction;
import Java.Maze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StrategieJoueur1 implements Strategie, KeyListener {

    AgentAction action = new AgentAction(Maze.WEST);
    private static StrategieJoueur1 strategieJoueur1 = new StrategieJoueur1();

    private StrategieJoueur1(){
        super();
    }

    public static StrategieJoueur1 getInstance() {
        if (strategieJoueur1 == null) {
            // Le mot-clé synchronized sur ce bloc empêche toute instanciation
            // multiple même par différents "threads".
            // Il est TRES important.
            synchronized (StrategieJoueur1.class) {
                if (strategieJoueur1 == null) {
                    StrategieJoueur1 strategieJoueur1 = new StrategieJoueur1();
                }
            }
        }
        return strategieJoueur1;
    }

    @Override
    public AgentAction jouer(Agent a, Maze maze) {
        System.out.println(action.getDirection());
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
                System.out.println("left");
                action = new AgentAction(Maze.WEST);
                break;
            case KeyEvent.VK_UP:
                System.out.println("up");
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
