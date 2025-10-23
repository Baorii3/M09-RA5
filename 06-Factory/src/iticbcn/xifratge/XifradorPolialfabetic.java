package iticbcn.xifratge;

import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {

    private static final char[] CARACTERS = 
        "abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toUpperCase().toCharArray();
    private char[] CARACTERS_PERMUTATS = new char[CARACTERS.length];
    private Random random;

    private void initRandom(String clau) throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
        try {
            long contra = Long.parseLong(clau);
            random = new Random(contra);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
    }

    private void permutaAlfabet(char[] alfabet) {
        int num = random.nextInt(CARACTERS.length);
        for (int i = 0; i < CARACTERS.length; i++) {
            CARACTERS_PERMUTATS[i] = alfabet[(num + i) % alfabet.length];
        }
    }

    private int indexChar(char[] caracteres, char c) {
        for (int i = 0; i < caracteres.length; i++) {
            if (Character.toUpperCase(c) == caracteres[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        initRandom(clau);

        StringBuilder resuBuilder = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            permutaAlfabet(CARACTERS);
            int index = indexChar(CARACTERS, c);
            if (index > -1) {
                resuBuilder.append(Character.isUpperCase(c) ? CARACTERS_PERMUTATS[index] : Character.toLowerCase(CARACTERS_PERMUTATS[index]));
            } else {
                resuBuilder.append(c);
            }
        }

        return new TextXifrat(resuBuilder.toString().getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        initRandom(clau);

        StringBuilder resuBuilder = new StringBuilder();
        String texto = new String(xifrat.getBytes());
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            permutaAlfabet(CARACTERS);
            int index = indexChar(CARACTERS_PERMUTATS, c);
            if (index > -1) {
                resuBuilder.append(Character.isUpperCase(c) ? CARACTERS[index] : Character.toLowerCase(CARACTERS[index]));
            } else {
                resuBuilder.append(c);
            }
        }

        return resuBuilder.toString();
    }
}
