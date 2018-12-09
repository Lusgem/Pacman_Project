public class Agent {

    private Type type;
    private PositionAgent positionCourante;


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
}
