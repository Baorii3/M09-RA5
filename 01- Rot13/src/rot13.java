package src;
public class rot13{

    private static final char[] CARACTERES_MINUS ="abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toCharArray();
    private static final char[] CARACTERES_MAYUS ="abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toUpperCase().toCharArray();

    public static void main(String[] args) {
        String fraseXifrada = xifraRot13("Hola, me uLlamo Iaan, tu?? eEncaAntado.");
        System.out.println(fraseXifrada);
        String fraseDesXifrada = desxifraRot13(fraseXifrada);
        System.out.println(fraseDesXifrada);
    }

    public static String xifraRot13(String frase) {
        String result = "";

        for (int i = 0; i<frase.length(); i++) {
            char c = frase.charAt(i);
            if (Character.isUpperCase(c)) {
                result += xifraCaracter(CARACTERES_MAYUS, c);
                continue;
            }
            if (Character.isLowerCase(c)) {
                result += xifraCaracter(CARACTERES_MINUS, c);
                continue;
            }
            result += c;
        }
        return result;
    }

    public static String desxifraRot13 (String frase) {
        String result = "";

        for (int i = 0; i<frase.length(); i++) {
            char c = frase.charAt(i);
            if (Character.isUpperCase(c)) {
                result += desxifraCaracter(CARACTERES_MAYUS, c);
                continue;
            }
            if (Character.isLowerCase(c)) {
                result += desxifraCaracter(CARACTERES_MINUS, c);
                continue;
            }
            result += c;
        }
        return result;
    }

    public static int indexChar(char[] caracteres, char c) {
        for (int i = 0; i< caracteres.length; i++) {
            if ( c == caracteres[i]) {
                return i;
            }
        }
        return -1;
    }

    public static char xifraCaracter(char[] caracteres, char c) {
        int index = indexChar(caracteres, c);
        if (index > -1) return caracteres[(index+13) % caracteres.length];
        return c;
    }

    public static char desxifraCaracter(char[] caracteres, char c) {
        int index = indexChar(caracteres, c);
        if (index > -1) return caracteres[((index-13) + caracteres.length) % caracteres.length];
        return c;
    }
}
