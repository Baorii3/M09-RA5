package src;
public class rot13{
    public static void main(String[] args) {
        String fraseXifrada = xifraRot13("Holah, me llamo Ian. Tu?");
        System.out.println(fraseXifrada);
        String fraseDesXifrada = desxifraRot13(fraseXifrada);
        System.out.println(fraseDesXifrada);
    }

    public static String xifraRot13(String frase) {
        int indexCMinus;
        int indexCharMayus;
        char[] caracteres = "abcdefghijklmnopkrstuvxyzàáèéèíïùúüòóñç".toCharArray();
        char[] caracteresMayus = "abcdefghijklmnopkrstuvxyzàáèéèíïùúüòóñç".toUpperCase().toCharArray();
        String result = "";

        for (int i = 0; i<frase.length(); i++) {
            char c = frase.charAt(i);
            indexCMinus = indexChar(caracteres,c);
            if (indexCMinus > 0) {
                result += caracteres[(indexCMinus+13) % caracteres.length];
                continue;
            }
            indexCharMayus = indexChar(caracteresMayus,c);
            if (indexCharMayus > 0) {
                result += caracteresMayus[(indexCharMayus+13) % caracteresMayus.length];
                continue;
            }
            result += c;
        }
        return result;
    }

    public static String desxifraRot13 (String frase) {
        int indexCMinus;
        int indexCharMayus;
        char[] caracteres = "abcdefghijklmnopkrstuvxyzàáèéèíïùúüòóñç".toCharArray();
        char[] caracteresMayus = "abcdefghijklmnopkrstuvxyzàáèéèíïùúüòóñç".toUpperCase().toCharArray();
        String result = "";

        for (int i = 0; i<frase.length(); i++) {
            char c = frase.charAt(i);
            indexCMinus = indexChar(caracteres,c);
            if (indexCMinus > 0) {
                result += caracteres[(indexCMinus-13) % caracteres.length];
                continue;
            }
            indexCharMayus = indexChar(caracteresMayus,c);
            if (indexCharMayus > 0) {
                result += caracteresMayus[(indexCharMayus-13) % caracteresMayus.length];
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
}
