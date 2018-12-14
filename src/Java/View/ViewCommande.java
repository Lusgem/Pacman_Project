package Java.View;

import Java.Controleur.ControleurGame;
import Java.Model.Game;
import Java.Strategie.StrategieJoueur1;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewCommande extends JFrame implements Observateur {

    Game game;
    ControleurGame controleurGame;

    GridLayout grid = new GridLayout(1,4);
    JPanel panelbuttons = new JPanel(grid);
    JPanel panelslide = new JPanel((new GridLayout(1,2)));
    JSlider slider = new JSlider(1,10,1);
    JLabel turnInfo = new JLabel("Tour : 0");
    Icon icon_restart  = new ImageIcon("icon_restart.png");
    Icon icon_pause  = new ImageIcon("icon_pause.png");
    Icon icon_run  = new ImageIcon("icon_run.png");
    Icon icon_step  = new ImageIcon("icon_step.png");
    JButton choixRestart = new JButton(icon_restart) ;
    JButton choixRun = new JButton(icon_run);
    JButton choixStep = new JButton(icon_step);
    JButton choixPause = new JButton(icon_pause);






    public ViewCommande(Game game, ControleurGame controleurGame) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x  ;
        int dy = centerPoint.y ;
        setLocation(dx, dy);

        game.enregistrerObservateur(this);
        this.game = game;
        this.controleurGame = controleurGame;
        setLayout(new GridLayout(2, 1));
        setTitle("Commandes du jeu");
        add(panelbuttons);
        add(panelslide);
        panelslide.add(slider);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                controleurGame.setTime(1000 / slider.getValue());
            }
        });


        panelslide.add(turnInfo);
        setSize(500, 200);
        setLayout(new GridLayout(2, 1));
        panelbuttons.add(choixRestart);
        panelbuttons.add(choixRun);
        panelbuttons.add(choixStep);
        panelbuttons.add(choixPause);
        setVisible(true);

        choixRun.setEnabled(true);
        choixRestart.setEnabled(false);
        choixStep.setEnabled(false);
        choixPause.setEnabled(false);


        choixRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controleurGame.restart();
                choixRestart.setEnabled(false);
                choixRun.setEnabled(true);
                choixPause.setEnabled(false);
                choixStep.setEnabled(false);
            }
        });

        choixPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controleurGame.pause();
                choixStep.setEnabled(true);
                choixRun.setEnabled(true);
                choixRestart.setEnabled(true);
                choixPause.setEnabled(false);
            }
        });

        choixRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controleurGame.run();
                choixRun.setEnabled(false);
                choixPause.setEnabled(true);
                choixStep.setEnabled(false);
                choixRestart.setEnabled(false);
            }
        });
        choixStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controleurGame.step();
                choixRestart.setEnabled(true);
            }
        });

    }

    @Override
    public void actualiser() {
        turnInfo.setText("Temps restant : "+(game.getTours_max()-game.getCompteur()));
        if(game.isOver()){
            choixRun.setEnabled(false);
            choixRestart.setEnabled(true);
            choixStep.setEnabled(false);
            choixPause.setEnabled(false);
        }
    }





}



