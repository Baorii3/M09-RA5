import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "suuuuu";


    public static void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet", "Hola Andrés cómo está tu cuñado", "Àgora illa Òtto"};
        
        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            }
            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("DEC: " + desxifrat);
        }
        
    }

    public static byte[] xifraAES (String msg, String clau) throws Exception {
        // Obternir els bytes de l'String
        byte[] msgByte = msg.getBytes();
        
        // Genera IvParameterSpec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Genera hash
        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] claveHash = md.digest(clau.getBytes("UTF-8"));

        // Encrypt
        SecretKeySpec secretKeySpec = new SecretKeySpec(claveHash, ALGORISME_XIFRAT);
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] xifrat = cipher.doFinal(msgByte);

        // Combinar IV i part xifrada
        byte[] xifratFinal = new byte[iv.length + xifrat.length];
        System.arraycopy(iv, 0, xifratFinal, 0, iv.length);
        System.arraycopy(xifrat, 0, xifratFinal, iv.length, xifrat.length);
        
        // return iv-msgxifrat
        return xifratFinal;

    }
}
