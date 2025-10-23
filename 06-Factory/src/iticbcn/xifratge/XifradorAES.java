package iticbcn.xifratge;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("La clau no pot ser null");
        }

        byte[] msgByte = msg.getBytes();

        // Genera IV aleatorio
        byte[] iv = new byte[MIDA_IV];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Genera hash de la clau
        byte[] claveHash;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
            claveHash = md.digest(clau.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new ClauNoSuportada("Error generando hash de la clau");
        }

        // Cifrar
        byte[] xifrat;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(claveHash, ALGORISME_XIFRAT);
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            xifrat = cipher.doFinal(msgByte);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new ClauNoSuportada("Error xifrant el missatge");
        }

        // Combinar IV + texto cifrado
        byte[] xifratFinal = new byte[MIDA_IV + xifrat.length];
        System.arraycopy(iv, 0, xifratFinal, 0, MIDA_IV);
        System.arraycopy(xifrat, 0, xifratFinal, MIDA_IV, xifrat.length);

        return new TextXifrat(xifratFinal);
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("La clau no pot ser null");
        }

        byte[] bytesXifrat = xifrat.getBytes();

        // Extraer IV
        byte[] iv = new byte[MIDA_IV];
        System.arraycopy(bytesXifrat, 0, iv, 0, MIDA_IV);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extraer mensaje cifrado
        int midaMsgXifrat = bytesXifrat.length - MIDA_IV;
        byte[] msgXifratPart = new byte[midaMsgXifrat];
        System.arraycopy(bytesXifrat, MIDA_IV, msgXifratPart, 0, midaMsgXifrat);

        // Genera hash de la clau
        byte[] claveHash;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
            claveHash = md.digest(clau.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new ClauNoSuportada("Error generando hash de la clau");
        }

        // Descifrar
        byte[] desxifrat;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(claveHash, ALGORISME_XIFRAT);
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            desxifrat = cipher.doFinal(msgXifratPart);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new ClauNoSuportada("Error desxifrant el missatge");
        }

        return new String(desxifrat);
    }
}
