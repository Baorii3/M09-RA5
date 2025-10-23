package iticbcn.xifratge;

import java.util.Random;

public class XifradorMonoalfabetic implements Xifrador {
    private static final char[] CARACTERS ="abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toUpperCase().toCharArray();
    private final char[] CARACTERS_PERMUTATS = permutaAlfabet(CARACTERS);

    public char[] permutaAlfabet(char[] alfabet) {
        Random random = new Random();
        int num = random.nextInt(alfabet.length);

        char[] caracters = new char[alfabet.length];

        for (int i = 0; i < caracters.length; i++) {
            caracters[i] = alfabet[(num + i) % alfabet.length];
        }
        return caracters;
    }

    public int indexChar(char[] caracteres, char c) {
        for (int i = 0; i< caracteres.length; i++) {
            if ( Character.toUpperCase(c) == caracteres[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        StringBuilder resuBuilder = new StringBuilder();
        for (int i = 0; i < msg.length();  i++) {
            char c = msg.charAt(i);
            int index = indexChar(CARACTERS, c);
            if (index > -1) {
                resuBuilder.append(Character.isUpperCase(c) ? CARACTERS_PERMUTATS[index] : Character.toLowerCase(CARACTERS_PERMUTATS[index]));
                continue;
            }
            resuBuilder.append(c);
        }
        return new TextXifrat(resuBuilder.toString().getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        String text = new String(xifrat.getBytes());
        StringBuilder resuBuilder = new StringBuilder();
        for (int i = 0; i < text.toString().length();  i++) {
            char c = text.toString().charAt(i);
            int index = indexChar(CARACTERS_PERMUTATS, c);
            if (index > -1) {
                resuBuilder.append(Character.isUpperCase(c) ? CARACTERS[index] : Character.toLowerCase(CARACTERS[index]));
                continue;
            }
            resuBuilder.append(c);
        }
        return resuBuilder.toString();
    }
}
