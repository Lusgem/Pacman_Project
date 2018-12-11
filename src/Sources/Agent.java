package Sources;

import Sources.Etat.Etat;
import Sources.Strategie.Strategie;
import Sources.Strategie.StrategieRandom;

public class Agent {

    private TypeAgent typeAgent;
    private PositionAgent positionCourante;
    private PositionAgent positionInitiale;
    private Etat etat;
    private Strategie strategie = new StrategieRandom();


    public Agent(TypeAgent typeAgent, PositionAgent positionCourante) {
        this.typeAgent = typeAgent;
        this.positionInitiale = positionCourante;
        this.positionCourante = positionCourante;
    }


    public PositionAgent getPositionCourante() {
        return positionCourante;
    }

    public void setPositionCourante(PositionAgent positionCourante) {
        this.positionCourante = positionCourante;
    }

    public TypeAgent getTypeAgent() {
        return typeAgent;
    }

    public void setTypeAgent(TypeAgent typeAgent) {
        this.typeAgent = typeAgent;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Strategie getStrategie() {
        return strategie;
    }

    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    public PositionAgent getPositionInitiale() {
        return positionInitiale;
    }
}
