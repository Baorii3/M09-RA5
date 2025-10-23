package iticbcn.xifratge;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador{

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        // Obternir els bytes de l'String
        byte[] msgByte = msg.getBytes();
        
        // Genera IvParameterSpec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Genera hash
    
        byte[] claveHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
            claveHash = md.digest(clau.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Encrypt
        byte[] xifrat = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(claveHash, ALGORISME_XIFRAT); 
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            xifrat = cipher.doFinal(msgByte);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        // Combinar IV i part xifrada
        byte[] xifratFinal = new byte[MIDA_IV + xifrat.length];
        System.arraycopy(iv, 0, xifratFinal, 0, MIDA_IV);
        System.arraycopy(xifrat, 0, xifratFinal, MIDA_IV, xifrat.length);
        
        // return iv-msgxifrat
        return new TextXifrat(xifratFinal);
        
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        // Extrau IV
        byte[] iv = new byte[MIDA_IV];
        System.arraycopy(xifrat, 0, iv, 0, MIDA_IV);

        // Extreue la part xifrada
        int midaMsgXifrat = xifrat.getBytes().length - MIDA_IV;
        byte[] msgXifratPart = new byte[midaMsgXifrat];
        System.arraycopy(xifrat, MIDA_IV, msgXifratPart, 0, midaMsgXifrat);

        // Fer hash de la clau
        byte[] claveHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
            claveHash = md.digest(clau.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Desxifrar
        SecretKeySpec secretKeySpec = new SecretKeySpec(claveHash, ALGORISME_XIFRAT); 
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        
        byte[] desxifrat = null;
        try {
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            desxifrat = cipher.doFinal(msgXifratPart);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        // return String desxifrat
        return new String(desxifrat);
    }
}
