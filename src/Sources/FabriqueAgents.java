package Sources;

public class FabriqueAgents {

    public static Agent fabriquePacman(PositionAgent positionAgent){
        Agent pacman = new Agent(Type.PACMAN,positionAgent);
        return pacman;
    }

    public static Agent fabriqueFantome(PositionAgent positionAgent){
        Agent fantome = new Agent(Type.FANTOME,positionAgent);
        return fantome;
    }
}
