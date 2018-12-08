

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View{
    ControleurGame controleurGame = new SimpleGame(20,1000);
    JFrame fenetre = new JFrame();
    JFrame fenetre2 = new JFrame();

    GridLayout grid = new GridLayout(1,4);
    JPanel panelbuttons = new JPanel(grid);
    JPanel panelslide = new JPanel((new GridLayout(1,2)));
    JSlider slider = new JSlider(1,10);
    JLabel turnInfo = new JLabel("Turn : ");
    Icon icon_restart  = new ImageIcon("icon_restart.png");
    Icon icon_pause  = new ImageIcon("icon_pause.png");
    Icon icon_run  = new ImageIcon("icon_run.png");
    Icon icon_step  = new ImageIcon("icon_step.png");
    JButton choixInit = new JButton(icon_restart) ;
    JButton choixRun = new JButton(icon_run);
    JButton choixStep = new JButton(icon_step);
    JButton choixPause = new JButton(icon_pause);
    public View(){
        fenetre.setLayout(new GridLayout(2,1));
        fenetre.setTitle("Commande");
        fenetre.add(panelbuttons);
        fenetre.add(panelslide);
        panelslide.add(slider);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        panelslide.add(turnInfo);
        fenetre.setSize(500,200);
        fenetre.setLayout(new GridLayout(2,1));
        panelbuttons.add(choixInit);
        panelbuttons.add(choixRun);
        panelbuttons.add(choixStep);
        panelbuttons.add(choixPause);
        fenetre.setVisible(true);
        choixInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controleurGame.init();
            }
        });

        choixPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controleurGame.pause();
            }
        });

        choixRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controleurGame.launch();
            }
        });
        choixStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controleurGame.step();
            }
        });

        try {
            fenetre2.add(new PanelPacmanGame(new Maze("./src/layouts/bigCorners.lay")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        fenetre2.setSize(500,500);
        fenetre2.setVisible(true);
    }

}
