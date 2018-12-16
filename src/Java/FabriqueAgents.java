package Java;

import Java.Etat.EtatInvulnerable;
import Java.Etat.EtatVulnerable;
import Java.Strategie.StrategieBruteRapprochement;
import Java.Strategie.StrategieJoueur1;
import Java.Strategie.StrategieJoueur2;

/**
 * Fabrique pour créer différents types d'agents à partir d'une position
 * Fait appel au constructeur d'Agent en spécifiant le type d'agent, sa position et son état de départ
 * Dans certains cas, on lui assigne une stratégie, sinon la stratégie de base est Random
 */
public class FabriqueAgents {

    public static Agent fabriquePacman(PositionAgent positionAgent){
        Agent pacman = new Agent(TypeAgent.PACMAN,positionAgent, new EtatVulnerable());
        return pacman;
    }

    public static Agent fabriqueFantomeRandom(PositionAgent positionAgent){
        Agent fantome = new Agent(TypeAgent.FANTOME,positionAgent,new EtatInvulnerable());
        return fantome;
    }

    public static Agent fabriqueJoueur1(PositionAgent positionAgent){
        Agent pacman = new Agent(TypeAgent.PACMAN,positionAgent, new EtatVulnerable());
        pacman.setStrategie(StrategieJoueur1.getInstance());
        pacman.setControlable(true);
        return pacman;
    }

    public static Agent fabriqueFantomeBrute(PositionAgent positionAgent){
        Agent fantome = new Agent(TypeAgent.FANTOME,positionAgent,new EtatInvulnerable());
        fantome.setStrategie(new StrategieBruteRapprochement());
        fantome.setControlable(false);
        return fantome;
    }

    public static Agent fabriqueJoueur2(PositionAgent positionAgent){
        Agent fantome = new Agent(TypeAgent.FANTOME,positionAgent, new EtatInvulnerable());
        fantome.setStrategie(StrategieJoueur2.getInstance());
        fantome.setControlable(true);
        return fantome;
    }

}
