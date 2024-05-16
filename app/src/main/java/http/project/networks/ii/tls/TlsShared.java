package http.project.networks.ii.tls;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.SecretKey;
import java.nio.file.Files;

public class TlsShared {
    private byte[] clientRandom;
    private byte[] serverRandom;
    private SecretKey symmetricKey;

    void generateSymmetricKey(byte[] preMasterSecret) throws Exception {
        byte[] seed = concatenate(serverRandom, clientRandom);
        //pbkdf2(preMasterSecret, seed, 2048, 256);
        symmetricKey = pbkdf2(preMasterSecret, seed, 2048, 256);
    }

    // PBKDF2 with HMAC-SHA256 as the PRF
    private SecretKey pbkdf2(byte[] password, byte[] salt, int iterations, int keyLength) throws Exception {
        KeySpec spec = new PBEKeySpec(toChars(password), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private char[] toChars(byte[] bytes) {
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char) bytes[i];
        }
        return chars;
    }

    private byte[] concatenate(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public void setClientRandom(byte[] clientRandom) {
        this.clientRandom = clientRandom;
    }

    public void setServerRandom(byte[] serverRandom) {
        this.serverRandom = serverRandom;
    }
    
    public SecretKey getSymmetricKey() {
        return symmetricKey;
    }
}
