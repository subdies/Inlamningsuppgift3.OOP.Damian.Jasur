
import java.util.*;
import java.util.Random;

public class Metoder {

    private int STRL;
    private int[][] bräda;
    private int tomRad, tomKolumn;
    private Random r = new Random();

    public Metoder(int storlek) {
        this.STRL = storlek;
        this.bräda = new int[STRL][STRL];
        //BÖRJAR MED ATT SKAPA ETT LÖST SPEL SÅ ATT DET INTE ÄR OMÖJLIGT ATT LÖSA
        skapaLöstSpel();
        //BLANDAR DÄREFTER SPELET
        blandaspelet();
    }


    private void skapaLöstSpel(){
        int value = 1;
        for (int i = 0; i < STRL; i++) {
            for (int j = 0; j < STRL; j++) {
                if (i ==STRL - 1 && j == STRL - 1) {
                    //SISTA PLATSEN SKA VARA TOM
                    bräda[i][j] = 0;
                    tomRad = i;
                    tomKolumn = j;
                } else {
                    //FYLLER UT HELA BRÄDAN
                    bräda[i][j] = value++;
                }
            }
        }
    }

    public void blandaspelet(){
        int blandning = 30 + r.nextInt(71);
        //BLANDAR BRÄDAN ETT ANTAL GÅNGER
        for (int i = 0; i < blandning; i++) {
            List<int[]> lagligtRörelse = LagligaRörelser();
            //VÄLJER UT ETT RANDOM LAGLIGT RÖRELSE OCH BLANDAR
            int[] rörelse = lagligtRörelse.get(r.nextInt(lagligtRörelse.size()));
            int nyRad = rörelse[0];
            int nyKolumn = rörelse[1];
            bräda[tomRad][tomKolumn] = bräda[nyRad][nyKolumn];
            bräda[nyRad][nyKolumn] = 0;
            tomRad = nyRad;
            tomKolumn = nyKolumn;
        }
    }

//LISTA ÖVER LAGLIGA RÖRELSER
    private List<int[]> LagligaRörelser(){
        List<int[]> rörelser = new ArrayList<>();
        if (tomRad > 0 ) rörelser.add(new int[]{tomRad - 1, tomKolumn});
        if (tomRad < STRL - 1) rörelser.add(new int[]{tomRad + 1, tomKolumn});
        if (tomKolumn > 0) rörelser.add(new int[]{tomRad, tomKolumn - 1});
        if (tomKolumn < STRL -1) rörelser.add(new int[]{tomRad, tomKolumn + 1});

        return rörelser;
    }

// KOLLAR ATT RÖRELSEN ÄR MÖJLIG
    private boolean valideraRörelse(int rad, int kolumn) {
        return (Math.abs(tomRad - rad) + Math.abs(tomKolumn - kolumn) == 1);

    }

//UTRFÖR RÖRELSEN OM DRAGET ÄR GODKÄNT
    public boolean utförRörelse(int rad, int kolumn) {
        if (valideraRörelse(rad, kolumn)) {
            bräda[tomRad][tomKolumn] = bräda[rad][kolumn];
            bräda[rad][kolumn] = 0;
            tomRad = rad;
            tomKolumn = kolumn;
            return true;
        }
        return false;
    }

// SKICKAR AKTULLE BRÄDAN
        public int[][] getBräda () {
            return bräda;

        }

//KONTOLLERAR OM BRÄDAN ÄR KLAR
        public boolean kollaOmKlart() {
        int value = 1;
            for (int i = 0; i < STRL; i++) {
                for (int j = 0; j < STRL; j++) {
                    if (i == STRL - 1 && j == STRL - 1) {
                        return bräda[i][j] == 0;
                        // SISTA KNAPPEN SKA VARA TOM
                    }
                if (bräda[i][j] != value++) {
                    return false; //EJ KLART
                }
            }
        }
            return true; // KLART
    }
}
