public class RotX {

    private static final char[] CARACTERES_MINUS ="abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toCharArray();
    private static final char[] CARACTERES_MAYUS ="abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toUpperCase().toCharArray();
    public static void main(String[] args) {
        String[] frases = {"ABC", "XYZ","Hola, Mr.calçot","Perdó, per tu què és?"};
        System.out.printf("%nXifrat%n-----%n");
        String[] frasesXifrades = printRotX(frases, false);
        
        System.out.printf("%nDesxifrat%n-----%n");
        printRotX(frasesXifrades, true);

        System.out.printf("%nMissatge Xifrat: %s%n",frasesXifrades[3]);
        String[] frasesForcaBruta = forcaBrutaRotX(frasesXifrades[3]);
        for (int i = 0; i<frasesForcaBruta.length;i++){
            System.out.printf("(%d)-%s%n",i,frasesForcaBruta[i]);
        }
          
    }

    // XifraRotX i DesxifraRotX fan el mateix, podriem eliminar 1 dels dos.
    public static String xifraRotX(String cadena, int desplaçament) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<cadena.length(); i++){
            char c = cadena.charAt(i);
            if (indexChar(CARACTERES_MAYUS, c) != -1) { // comprovem si esta en la llista
                result.append(xifraCaracter(CARACTERES_MAYUS,c,desplaçament));
                continue;
            } else if (indexChar(CARACTERES_MINUS, c) != -1){
                result.append(xifraCaracter(CARACTERES_MINUS,c,desplaçament));
                continue;
            }
            result.append(c);
        } 
        return result.toString();
    }

    public static String desxifraRotX(String cadena, int desplaçament) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<cadena.length(); i++){
            char c = cadena.charAt(i);
            if (indexChar(CARACTERES_MAYUS, c) != -1) { // comprovem si esta en la llista
                result.append(xifraCaracter(CARACTERES_MAYUS,c,desplaçament));
                continue;
            } else if (indexChar(CARACTERES_MINUS, c) != -1){
                result.append(xifraCaracter(CARACTERES_MINUS,c,desplaçament));
                continue;
            }
            result.append(c);
        } 
        return result.toString();
    }

    public static String[] forcaBrutaRotX(String cadenaXifrada) {
        String[] frasesForcaBruta = new String[CARACTERES_MAYUS.length];
        for(int i = 0; i<frasesForcaBruta.length; i++){
            frasesForcaBruta[i] = xifraRotX(cadenaXifrada, i);
        }
        return frasesForcaBruta;
        
    }

    public static int indexChar(char[] caracteres, char c) {
        for (int i = 0; i< caracteres.length; i++) {
            if ( c == caracteres[i]) {
                return i;
            }
        }
        return -1;
    }

    public static char xifraCaracter(char[] caracteres, char c, int desplaçament) {
        int index = indexChar(caracteres, c);
        if (index > -1) return caracteres[(((index+desplaçament) % caracteres.length) + caracteres.length) % caracteres.length]; // calcul que dona igual si el desplaçament es llarg o negatiu
        return c;
    }

    public static String[] printRotX(String[] frases, boolean negatiu){
        String[] frasesRotX = new String[frases.length];
        int desplaçament = 0;
        for (int i = 0; i < frases.length; i++) {
            String frase = frases[i];
            String fraseXifrada = xifraRotX(frase, (!negatiu)?desplaçament:desplaçament*-1);
            frasesRotX[i] = fraseXifrada;
            System.out.printf("(%d)-%s  => %s%n", desplaçament, frase, fraseXifrada);
            desplaçament += 2;
        }  
        return frasesRotX;
    }
}
