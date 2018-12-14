package Java;

import Java.Etat.EtatInvulnerable;
import Java.Etat.EtatVulnerable;
import Java.Strategie.StrategieJoueur1;

/**
 * Fabrique pour créer différents types d'agents à partir d'une position
 * Fait appel au constructeur d'Agent en spécifiant le type d'agent, sa position et son état de départ
 */
public class FabriqueAgents {

    public static Agent fabriquePacman(PositionAgent positionAgent){
        Agent pacman = new Agent(TypeAgent.PACMAN,positionAgent, new EtatVulnerable());
        return pacman;
    }

    public static Agent fabriqueFantome(PositionAgent positionAgent){
        Agent fantome = new Agent(TypeAgent.FANTOME,positionAgent,new EtatInvulnerable());
        return fantome;
    }


}
