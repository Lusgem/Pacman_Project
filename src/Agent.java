public class Agent {

    private Type type;
    private PositionAgent positionCourante;
    private Etat etat;
    private Strategie strategie = new StrategieRandom();


    public Agent(Type type, PositionAgent positionCourante) {
        this.type = type;
        this.positionCourante = positionCourante;
    }


    public PositionAgent getPositionCourante() {
        return positionCourante;
    }

    public void setPositionCourante(PositionAgent positionCourante) {
        this.positionCourante = positionCourante;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
}
