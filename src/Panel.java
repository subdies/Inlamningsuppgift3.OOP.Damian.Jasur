import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private int STRL;
    private JButton[][] spelknappar;
    private Metoder metoder;
    private int antalRörelser;
//SKAPAR EN PANEL / MENY
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
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Nytt spel")) {
            valAvStorlek();
        }
    }
    ButtonGroup bg = new ButtonGroup();

    //LÅTER ANVÄNDAREN VÄLJA STORLEK PÅ SPELET X3, X4, X5
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
        //LÄGGER ALL KNAPPAR I EN GRUPP
        bg.add(liten); bg.add(mellan); bg.add(stor);
        //LÄGGER ALL KNAPPAR PÅ PANELEN
        panel.add(liten);
        panel.add(mellan);
        panel.add(stor);

        JButton välj = new JButton("Välj");
        välj.addActionListener(e -> {
            //SKAPAR ET SPEL BASERAT PÅ VALET
            if (bg.getSelection() != null) {
                String storleksVal = bg.getSelection().getActionCommand();
                skapaSpel(Integer.parseInt(storleksVal));
                dialog.dispose();
            }else {
                JOptionPane.showMessageDialog(dialog, "Välj en storlek.");
            }
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(välj, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

//SKAPAR ETT SPEL BASERAT PÅ STORLEKEN SOM VALDES
    public void skapaSpel(int storlek){
        STRL = storlek;
        spelknappar = new JButton[STRL][STRL];
        //LÄGGER TILL ANTAL RÖRELSER/DRAG
        antalRörelser = 0;
        panel.removeAll();

        panel.setLayout(new GridLayout(STRL, STRL));
        metoder = new Metoder(STRL);

        for (int i = 0; i < STRL; i++) {
            for (int j = 0; j < STRL; j++) {
                spelknappar[i][j] = new JButton();
                int rad = i;
                int kolumn = j;
                spelknappar[i][j].addActionListener(e ->{
                    if (metoder.utförRörelse(rad,kolumn)){
                        antalRörelser++;
                        uppdatera();
                        if(metoder.kollaOmKlart()){
                            frågaOmSpelaIgen();
                        }
                            }
                        });
                panel.add(spelknappar[i][j]);
            }
        }
        uppdatera();
        panel.revalidate();
        panel.repaint();
    }
//SPELAREN KAN VÄLJA SPELA IGEN ELLER AVSLUTA I SLUTET
    private void frågaOmSpelaIgen(){
        JOptionPane.showMessageDialog(frame, "Grattis du har löst spelet på " + antalRörelser + " drag.");
        int val = JOptionPane.showConfirmDialog(frame, "Vill du spela igen?","Spela igen?",JOptionPane.YES_NO_OPTION);
        if (val == JOptionPane.YES_OPTION) {
            valAvStorlek();
        }else {
            frame.dispose();
        }
    }

// UPPDATERAR AKTUELLA BRÄDAN
    private void uppdatera(){
        int[][] bräda = metoder.getBräda();
        for (int i = 0; i < STRL; i++) {
            for (int j = 0; j < STRL; j++) {
                spelknappar[i][j].setText(bräda[i][j] == 0 ? "" : String.valueOf(bräda[i][j]));
            }
        }
    }
}
