package Sources.Strategie;

import Sources.Agent;
import Sources.AgentAction;
import Sources.Maze;

public interface Strategie {
    public AgentAction jouer(Agent a, Maze maze);
}
