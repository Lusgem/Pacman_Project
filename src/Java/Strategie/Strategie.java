package Java.Strategie;

import Java.Agent;
import Java.AgentAction;
import Java.Maze;

import java.util.ArrayList;

public interface Strategie {
    public AgentAction jouer(Agent a, Maze maze, ArrayList<Agent> ennemies, ArrayList<Agent> allies);
}
