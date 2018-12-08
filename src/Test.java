public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {

        SimpleGame g = new SimpleGame(20);
        ViewCommande view = new ViewCommande(g);
        ViewGame viewGame = new ViewGame(g);

        g.launch();

    }

}
