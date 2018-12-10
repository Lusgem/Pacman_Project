import java.util.ArrayList;

public class StrategieRandom implements Strategie {

    @Override
    public AgentAction jouer(Agent a,Maze maze) {
        int nombreAleatoire = (int)(Math.random() * (4));
        switch (nombreAleatoire){
            case 0:
                return new AgentAction(Maze.NORTH);
            case 1:
                return new AgentAction(Maze.SOUTH);
            case 2:
                return new AgentAction(Maze.EAST);
            case 3:
                return new AgentAction(Maze.WEST);
            default:
                return new AgentAction(Maze.NORTH);
        }
    }
}
