import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewCommande extends JFrame {

    SimpleGame game;
    ControleurGame controleurGame;

    GridLayout grid = new GridLayout(1,4);
    JPanel panelbuttons = new JPanel(grid);
    JPanel panelslide = new JPanel((new GridLayout(1,2)));
    JSlider slider = new JSlider(1,10,1);
    JLabel turnInfo = new JLabel("Turn : ");
    Icon icon_restart  = new ImageIcon("icon_restart.png");
    Icon icon_pause  = new ImageIcon("icon_pause.png");
    Icon icon_run  = new ImageIcon("icon_run.png");
    Icon icon_step  = new ImageIcon("icon_step.png");
    JButton choixInit = new JButton(icon_restart) ;
    JButton choixRun = new JButton(icon_run);
    JButton choixStep = new JButton(icon_step);
    JButton choixPause = new JButton(icon_pause);

    public ViewCommande(SimpleGame game) {
        this.game = game;
        controleurGame = new ControleurSimpleGame(game);
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
        panelbuttons.add(choixInit);
        panelbuttons.add(choixRun);
        panelbuttons.add(choixStep);
        panelbuttons.add(choixPause);
        setVisible(true);
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

    }
    }

