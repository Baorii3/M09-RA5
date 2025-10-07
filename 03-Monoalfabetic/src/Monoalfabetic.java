import java.util.Random;

public class Monoalfabetic {
    private static final char[] CARACTERS ="abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toUpperCase().toCharArray();
    private static final char[] CARACTERS_PERMUTATS = permutaAlfabet(CARACTERS);

    public static void main(String[] args) {
        String cadena = "Perdó, per tu què és?";
        System.out.println("Sense xifrar: " +cadena);
        String xifrat = xifraMonoAlfa(cadena);
        System.out.println("Xifrada: " + xifrat);
        String desxifrat = desxifraMonoAlfa(xifrat);
        System.out.println("Desxifrat: " + desxifrat);
    }

    public static char[] permutaAlfabet(char[] alfabet) {
        Random random = new Random();
        int num = random.nextInt(alfabet.length);

        char[] caracters = new char[alfabet.length];

        for (int i = 0; i < caracters.length; i++) {
            caracters[i] = alfabet[(num + i) % alfabet.length];
        }
        return caracters;
    }

    public static String xifraMonoAlfa(String cadena) {
        StringBuilder resuBuilder = new StringBuilder();
        for (int i = 0; i < cadena.length();  i++) {
            char c = cadena.charAt(i);
            int index = indexChar(CARACTERS, c);
            if (index > -1) {
                resuBuilder.append(Character.isUpperCase(c) ? CARACTERS_PERMUTATS[index] : Character.toLowerCase(CARACTERS_PERMUTATS[index]));
                continue;
            }
            resuBuilder.append(c);
        }
        return resuBuilder.toString();
    }

    public static String desxifraMonoAlfa(String cadena) {
        StringBuilder resuBuilder = new StringBuilder();
        for (int i = 0; i < cadena.length();  i++) {
            char c = cadena.charAt(i);
            int index = indexChar(CARACTERS_PERMUTATS, c);
            if (index > -1) {
                resuBuilder.append(Character.isUpperCase(c) ? CARACTERS[index] : Character.toLowerCase(CARACTERS[index]));
                continue;
            }
            resuBuilder.append(c);
        }
        return resuBuilder.toString();
    }

    public static int indexChar(char[] caracteres, char c) {
        for (int i = 0; i< caracteres.length; i++) {
            if ( Character.toUpperCase(c) == caracteres[i]) {
                return i;
            }
        }
        return -1;
    }
}
