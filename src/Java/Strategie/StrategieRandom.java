package Java.Strategie;

import Java.Agent;
import Java.AgentAction;
import Java.Maze;
import Java.Model.PacmanGame;

import java.util.ArrayList;

public class StrategieRandom implements Strategie {

    int nombreAleatoire;

    @Override
    public AgentAction jouer(Agent a, Maze maze, ArrayList<Agent> ennemies, ArrayList<Agent> allies) {
        nombreAleatoire = (int)(Math.random() * (4));
        while(PacmanGame.isLegalMove(a,new AgentAction(nombreAleatoire))) {
            switch (nombreAleatoire) {
                case 0:
                    if(PacmanGame.isLegalMove(a,new AgentAction(nombreAleatoire)))
                    return new AgentAction(Maze.NORTH);
                case 1:
                    if(PacmanGame.isLegalMove(a,new AgentAction(nombreAleatoire)))
                    return new AgentAction(Maze.SOUTH);
                case 2:
                    if(PacmanGame.isLegalMove(a,new AgentAction(nombreAleatoire)))
                    return new AgentAction(Maze.EAST);
                case 3:
                    if(PacmanGame.isLegalMove(a,new AgentAction(nombreAleatoire)))
                    return new AgentAction(Maze.WEST);
                default:
                    return new AgentAction(Maze.NORTH);
            }
        }
        return new AgentAction(Maze.NORTH);
    }
}
