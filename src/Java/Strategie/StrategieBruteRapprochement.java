package Java.Strategie;

import Java.Agent;
import Java.AgentAction;
import Java.Maze;
import Java.Model.Game;
import Java.Model.PacmanGame;
import Java.PositionAgent;

import java.util.ArrayList;

public class StrategieBruteRapprochement implements Strategie {
    @Override
    public AgentAction jouer(Agent a, Maze maze, ArrayList<Agent> ennemies, ArrayList<Agent> allies) {
        PositionAgent pos = a.getPositionCourante();
        PositionAgent posEnnemie = ennemies.get(0).getPositionCourante();
        if((posEnnemie.getX() > pos.getX()) && (PacmanGame.isLegalMove(a,new AgentAction(Maze.EAST)))) {
            return new AgentAction(Maze.EAST);
        }
        else if ((posEnnemie.getY() < pos.getY()) && (PacmanGame.isLegalMove(a,new AgentAction(Maze.NORTH)))) {
            return new AgentAction(Maze.NORTH);
        }
        else if ((posEnnemie.getY() > pos.getY()) && (PacmanGame.isLegalMove(a,new AgentAction(Maze.SOUTH)))) {
            return new AgentAction(Maze.SOUTH);
        }
        else if ((posEnnemie.getX() < pos.getX()) && (PacmanGame.isLegalMove(a,new AgentAction(Maze.WEST)))) {
            return new AgentAction(Maze.WEST);
        }
        else if(PacmanGame.isLegalMove(a,new AgentAction(Maze.EAST))){
            return new AgentAction(Maze.EAST);
        }
        else if(PacmanGame.isLegalMove(a,new AgentAction(Maze.NORTH))){
            return new AgentAction(Maze.NORTH);
        }
        else if(PacmanGame.isLegalMove(a,new AgentAction(Maze.WEST))){
            return new AgentAction(Maze.WEST);
        }
        else if(PacmanGame.isLegalMove(a,new AgentAction(Maze.SOUTH))){
            return new AgentAction(Maze.SOUTH);
        }



    return new AgentAction(Maze.STOP);
    }
}
