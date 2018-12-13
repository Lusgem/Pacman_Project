package Sources;

public class FabriqueAgents {

    public static Agent fabriquePacman(PositionAgent positionAgent){
        Agent pacman = new Agent(TypeAgent.PACMAN,positionAgent);
        return pacman;
    }

    public static Agent fabriqueFantome(PositionAgent positionAgent){
        Agent fantome = new Agent(TypeAgent.FANTOME,positionAgent);
        return fantome;
    }
}
