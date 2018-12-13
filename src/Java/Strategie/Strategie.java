package Java.Strategie;

import Java.Agent;
import Java.AgentAction;
import Java.Maze;

public interface Strategie {
    public AgentAction jouer(Agent a, Maze maze);
}
