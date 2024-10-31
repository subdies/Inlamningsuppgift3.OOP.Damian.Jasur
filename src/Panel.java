import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private int STRL;
    private JButton[][] spelknappar;

    public void skapaPanel(){
        frame = new JFrame("Shuffle-Spel");
        panel = new JPanel(new BorderLayout());

        frame.setSize(500, 500);
        JButton nyttSpelButton = new JButton("Nytt spel");

        nyttSpelButton.addActionListener(this);
        panel.add(nyttSpelButton, BorderLayout.NORTH);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Nytt spel")) {
            valAvStorlek();
        }
    }
    ButtonGroup bg = new ButtonGroup();

    public void valAvStorlek(){
        JDialog dialog = new JDialog(frame, "Välj storlek på spelet", true);
        dialog.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        frame.setLocationRelativeTo(null);


        JRadioButton liten  = new JRadioButton("Liten (3X3");
        JRadioButton mellan  = new JRadioButton("Mellan (4X4)");
        JRadioButton stor  = new JRadioButton("Stor (5X5)");

        liten.setActionCommand("3");
        mellan.setActionCommand("4");
        stor.setActionCommand("5");

        bg.add(liten); bg.add(mellan); bg.add(stor);
        panel.add(liten);
        panel.add(mellan);
        panel.add(stor);

        JButton välj = new JButton("Välj");
        välj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String storleksVal = bg.getSelection().getActionCommand();
                skapaSpel(Integer.parseInt(storleksVal));
                dialog.dispose();
            }
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(välj, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }


    public void skapaSpel(int storlek){
        STRL = storlek;
        spelknappar = new JButton[STRL][STRL];
        panel.removeAll();

        panel.setLayout(new GridLayout(STRL, STRL));

        for (int i = 0; i < STRL; i++) {
            for (int j = 0; j < STRL; j++) {
                spelknappar[i][j] = new JButton();
                panel.add(spelknappar[i][j]);
            }
        }
        panel.revalidate();
        panel.repaint();
    }
}
