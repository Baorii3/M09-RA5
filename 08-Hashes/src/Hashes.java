import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    public int npass = 0;
    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] hash = md.digest(pw.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

    public String getPBKDF2AmbSalt(String pw, String salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), 65536, 128);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }
    
    public String forcaBruta(String alg, String hash, String salt){
    String caracters = "abcdefABCDEF1234567890!";
    int maxLength = 6;
    npass = 0;

    String prefix = "";
    if (prefix.length() > maxLength) return null;

    if (matches(prefix, alg, hash, salt)) return prefix;

    int maxAdd = Math.min(6, maxLength - prefix.length());
    int L = caracters.length();
    int[] indices = new int[maxAdd];
    boolean done = false;

    while (!done) {
        StringBuilder intento = new StringBuilder(prefix);
        for (int i = 0; i < maxAdd; i++) {
            intento.append(caracters.charAt(indices[i]));
        }
        npass++;
        if (matches(intento.toString(), alg, hash, salt)) return intento.toString();

        for (int pos = maxAdd - 1; pos >= 0; pos--) {
            if (indices[pos] + 1 < L) {
                indices[pos]++;
                for (int reset = pos + 1; reset < maxAdd; reset++) indices[reset] = 0;
                break;
            } else if (pos == 0) {
                done = true;
            }
        }
    }
    return null;
}

private boolean matches(String text, String alg, String hash, String salt) {
    String h = null;
    if (alg.equals("SHA-512")) h = getSHA512AmbSalt(text, salt);
    else if (alg.equals("PBKDF2")) h = getPBKDF2AmbSalt(text, salt);
    return h != null && h.equals(hash);
}

    public String getInterval(long t1, long t2) {
        long diff = t2 - t1;
        long totalSeconds = diff / 1000;
        long days = totalSeconds / 86400;
        long hours = (totalSeconds % 86400) / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        long milliseconds = diff % 1000;
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis",
                days, hours, minutes, seconds, milliseconds);
    }

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";

        Hashes h = new Hashes();
        String[] aHashes = {
            h.getSHA512AmbSalt(pw, salt),
            h.getPBKDF2AmbSalt(pw, salt)
        };

        String pwTrobat = null;
        String[] algorismes = { "SHA-512", "PBKDF2" };

        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("================================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("--------------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("--------------------------------\n\n");
        }
    }

}