package iticbcn.xifratge;

public class XifradorRotX implements Xifrador{

    private static final char[] CARACTERES_MINUS ="abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toCharArray();
    private static final char[] CARACTERES_MAYUS ="abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toUpperCase().toCharArray();

    // XifraRotX i DesxifraRotX fan el mateix, podriem eliminar 1 dels dos.
    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<msg.length(); i++){
            char c = msg.charAt(i);
            int desplaçament = Integer.parseInt(msg);
            if (indexChar(CARACTERES_MAYUS, c) != -1) { // comprovem si esta en la llista
                result.append(xifraCaracter(CARACTERES_MAYUS,c,desplaçament));
                continue;
            } else if (indexChar(CARACTERES_MINUS, c) != -1){
                result.append(xifraCaracter(CARACTERES_MINUS,c,desplaçament));
                continue;
            }
            result.append(c);
        } 
        return new TextXifrat(result.toString().getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<xifrat.toString().length(); i++){
            char c = xifrat.toString().charAt(i);
            int desplaçament = Integer.parseInt(xifrat.toString());
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

    public String[] forcaBrutaRotX(String cadenaXifrada) {
        String[] frasesForcaBruta = new String[CARACTERES_MAYUS.length];
        for(int i = 0; i<frasesForcaBruta.length; i++){
            try {
                frasesForcaBruta[i] = xifra(cadenaXifrada, String.format("%d", i)).toString();
            } catch (ClauNoSuportada e) {
                e.printStackTrace();
            }
        }
        return frasesForcaBruta;
        
    }

    public int indexChar(char[] caracteres, char c) {
        for (int i = 0; i< caracteres.length; i++) {
            if ( c == caracteres[i]) {
                return i;
            }
        }
        return -1;
    }

    public char xifraCaracter(char[] caracteres, char c, int desplaçament) {
        int index = indexChar(caracteres, c);
        if (index > -1) return caracteres[(((index+desplaçament) % caracteres.length) + caracteres.length) % caracteres.length]; // calcul que dona igual si el desplaçament es llarg o negatiu
        return c;
    }

    public String[] printRotX(String[] frases, boolean negatiu){
        String[] frasesRotX = new String[frases.length];
        int desplaçament = 0;
        for (int i = 0; i < frases.length; i++) {
            String frase = frases[i];
            String fraseXifrada = null;
            try {
                fraseXifrada = xifra(frase, String.format("%d", (!negatiu)?desplaçament:desplaçament*-1)).toString();
            } catch (ClauNoSuportada e) {
                e.printStackTrace();
            }
            frasesRotX[i] = fraseXifrada;
            System.out.printf("(%d)-%s  => %s%n", desplaçament, frase, fraseXifrada);
            desplaçament += 2;
        }  
        return frasesRotX;
    }

}
