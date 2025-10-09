import java.util.Random;

public class Polialfabetic {

    private static final char[] CARACTERS ="abcdefghijklmnñopqrstuvwxyzáéíóúüàèìòùç".toUpperCase().toCharArray();
    private static char[] CARACTERS_PERMUTATS = new char[CARACTERS.length];
    private static final long CONTRASEÑA = 12341;
    private static Random random;

    public static void main(String[] args) {
        String msgs[] = {"Test 01 àrbritre, coixí, Perímetre", "Test 02 Taüll, DÍA, año", "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];
        System.out.println("Xifratge:\n----"); 
        for (int i = 0; i < msgs.length; i++){
            initRandom(CONTRASEÑA);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]); System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
            System.out.println("Desxifratge:\n-----------");
        }
        for (int i = 0; i < msgs.length; i++) {
            initRandom(CONTRASEÑA);
            String msg = desxifraPoliAlfa(msgsXifrats[i]); System.out.printf("%-34s -> %s %n", msgsXifrats[i], msg);
        }
    }

    public static void initRandom(long contra) {
        random = new Random(contra);
    }

    public static void permutaAlfabet(char[] alfabet) {

        int num = random.nextInt(CARACTERS.length);

        for (int i = 0; i < CARACTERS.length; i++) {
            CARACTERS_PERMUTATS[i] = alfabet[(num + i) % alfabet.length];
        }
    }

    public static String xifraPoliAlfa(String msg){
        StringBuilder resuBuilder = new StringBuilder();
        for (int i = 0; i < msg.length();  i++) {
            char c = msg.charAt(i);
            permutaAlfabet(CARACTERS);
            int index = indexChar(CARACTERS, c);
            if (index > -1) {
                resuBuilder.append(Character.isUpperCase(c) ? CARACTERS_PERMUTATS[index] : Character.toLowerCase(CARACTERS_PERMUTATS[index]));
                continue;
            }
            resuBuilder.append(c);
        }
        return resuBuilder.toString();
    }

    public static String desxifraPoliAlfa(String msgXifrat){
        StringBuilder resuBuilder = new StringBuilder();
        for (int i = 0; i < msgXifrat.length();  i++) {
            char c = msgXifrat.charAt(i);
            permutaAlfabet(CARACTERS);
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
            if (Character.toUpperCase(c) == caracteres[i]) {
                return i;
            }
        }
        return -1;
    }
}