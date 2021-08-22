package app;

import java.util.LinkedList;

public class KonNajkratsiaCesta {

    private static class Policko {
        private int x;
        private int y;
        private int vzdialenost;
        /**
         * Policko reprezentuje jedno policko na sachovnici
         * @param x suradnica x policka
         * @param y suradnica y policka
         * @param vzdialenost pocet tahov, ktory je potrebny na dosiahnutie policka zo zaciatocnej pozicie
         */
        Policko(int x, int y, int vzdialenost) {
            this.x = x;
            this.y = y;
            this.vzdialenost = vzdialenost;
        }
        public int getX() {return this.x;};
        public int getY() {return this.y;};
        public int getVzdialenost() {return this.vzdialenost;};
    }
    
    /**
     * skontroluje ci je tah na zadane x a y platny, teda ci sa policko nachadza na sachovnici
     * @param x suradnica x ktoru treba skontrolovat
     * @param y suradnica x ktoru treba skontrolovat
     * @return true ak je tah platny, false ak nie je
     */
    public static boolean jeTahPlatny(int x, int y) {
        if(x >= 0 && x <=7 && y >= 0 && y <=7) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param xZac suradnica x zaciatocnej pozicie
     * @param yZac suradnica y zaciatocnej pozicie
     * @param xKon suradnica x konecnej pozicie
     * @param yKon suradnica y konecnej pozicie
     * @return minimalny pocet tahov, ktory je potrebny na dosiahnutie konecnej pozicie zo zaciatocnej
     * alebo -1, ak sa cesta nenajde
     */
    public static int vratMinPocetTahov(int xZac,int yZac,int xKon,int yKon){
        boolean[][] sachovnica = new boolean[8][8]; //indikator ci policka boli navstivene
        //polia moznych tahov konom po riadkoch a stlpcoch
        int[] riadky = new int[]{-2,-2,-1,1,2,2,-1,1}; 
        int[] stlpce = new int[]{-1,1,-2,-2,-1,1,2,2};
        //na zaciatku sa vsetky policka na sachovnici nastavia na nenavstivene
        for (int i = 0; i < sachovnica.length; i++) {
            for (int j = 0; j < sachovnica[i].length; j++) {
                sachovnica[i][j] = false;
            }
        }
        
        LinkedList<Policko> queue = new LinkedList<>();
        queue.add(new Policko(xZac,yZac,0)); //prida sa zaciatocna pozicia kona do queue
        sachovnica[xZac][yZac] = true; //zaciatocnu poziciu kona nastavime ako navstivenu
        //prehladavanie Breadth first search na najdenie cesty
        //kym v queue nie je 1 prvok
        while(!queue.isEmpty()) {
            Policko aktualne = queue.removeFirst();
            int aktX = aktualne.getX();
            int aktY = aktualne.getY();
            //ak dojdeme na cielove policko, vrati sa pocet tahov, ktorymi sme sa tam dostali
            if (aktX == xKon && aktY == yKon) {
                return aktualne.getVzdialenost();
            }
            
            //prejdu sa vsetky dosazitelne pozicie a ak su platne a neboli navstivene,
            //oznacia sa ako navstivene a pridaju sa do queue so vzdialenostou o 1 vacsou
            int x;
            int y;
            for (int i = 0; i < riadky.length; i++) {
                x = aktX + stlpce[i];
                y = aktY + riadky[i];
                if (jeTahPlatny(x,y) && !sachovnica[x][y]) {
                    sachovnica[x][y] = true;
                    queue.add(new Policko(x,y,aktualne.getVzdialenost() + 1));
                }
            }
        }
        return -1;     
    }
    
    public static void main(String[] args) {
        System.out.println("Najmensi pocet tahov: " + KonNajkratsiaCesta.vratMinPocetTahov(0, 0, 7, 7));
    }
    
}
