import java.util.ArrayList;

public interface Strategie {
    public AgentAction jouer(Agent a, ArrayList<PositionAgent> positionPacman, ArrayList<PositionAgent> positionFantomes);
}
