package http.project.networks.ii.tls;

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.nio.file.Paths;
import java.nio.file.Files;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

public class ServerHello {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private SecureRandom random;
    private byte[] clientRandom;
    private byte[] serverRandom;
    private Certificate certificate;
    private TlsShared tlsShared;
    public SecretKey symmetricKey;

    /**
     * Constructor for the server TLS handshake
     * The socket is created with the server name and port from GreetClient
     * @param port
     * @param certificate
     * @param tlsShared
     * @param s
     * @throws IOException
     */
    public ServerHello(int port, Certificate certificate, TlsShared tlsShared, Socket s) throws IOException {
        this.socket = s;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        random = new SecureRandom();
        this.certificate = certificate;
        this.tlsShared = tlsShared;
        this.clientRandom = new byte[32];
        this.serverRandom = new byte[32];
    }

    /**
     * Process the client hello message and send the server hello message
     * The server hello message contains the TLS version, the server random, the cipher suite, and the certificate
     * The cipher suite is the symmetric encryption algorithm to be used
     * @throws IOException
     * @throws InvalidKeyException
     * @throws CertificateException
     */
    public void processClientAndServer() throws IOException, InvalidKeyException,
             CertificateException{
                //1. CLIENT HELLO
        // Read TLS version
        byte major = in.readByte();
        byte minor = in.readByte();

        //System.out.println("ClientHello: TLS version " + major + "." + minor);

        // Read client random
        in.readFully(this.clientRandom);

        // Read cipher suite
        String cipherSuite = in.readUTF();

        if (serverSupportsCipherSuite(cipherSuite)) {
            sendServerHello(cipherSuite); //2. SERVER HELLO with the cipher suite selected
        } else {
            //System.out.println("Server does not support ClientHello cipher suite " + cipherSuite);
        }
    }

    private void sendServerHello(String cipherSuite) throws IOException, InvalidKeyException,
             CertificateException {
        // TLS version (3.3 for TLSv1.3)
        out.writeByte(3);
        out.writeByte(3);

        // Random: 32-byte challenge
        random.nextBytes(this.serverRandom);
        out.write(this.serverRandom);

        // Cipher suite: Selected cipher suite from the server
        out.writeUTF(cipherSuite);

        // Send certificate
        byte[] encodedCertificate = certificate.getEncoded();
        out.writeInt(encodedCertificate.length);
        out.write(encodedCertificate);

        out.flush();
        
        //System.out.println("ServerHello: Sent ServerHello with cipher suite " + cipherSuite);
    }

    /**
     * Receive the pre-master secret from the client
     * The pre-master secret is encrypted with the server's public key
     * The pre-master secret is decrypted with the server's private key using RSA and the private_key.der file for the Kp
     * The symmetric key is generated from the pre-master secret
     * @throws Exception
     */
    public void receivePremasterSecret() throws Exception {
        int length = in.readInt();
        byte[] preMasterSecretCiphered = new byte[length];
        in.readFully(preMasterSecretCiphered);

        // Decrypt pre-master secret
        PrivateKey privateKey = getPrivateKey(Paths.get("app", "src", "main", "java", "http", "project", "networks", "ii", "tls", "private_key.der").toAbsolutePath().toString());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] preMasterSecret = cipher.doFinal(preMasterSecretCiphered);

        // Generate symmetric key
        tlsShared.setClientRandom(this.clientRandom);
        tlsShared.setServerRandom(this.serverRandom);
        tlsShared.generateSymmetricKey(preMasterSecret);
        this.symmetricKey = tlsShared.getSymmetricKey();
        //System.out.println("ServerHello: Received pre-master secret and have the symmetric key");
        //System.out.println("\nSymmetric key: " + Hex.encodeHexString(this.symmetricKey.getEncoded()));
    }

    private boolean serverSupportsCipherSuite(String cipherSuite) {
        String[] supportedCipherSuites = {
            "TLS_AES_128_GCM_SHA256",
            "TLS_AES_256_GCM_SHA384",
            "TLS_CHACHA20_POLY1305_SHA256",
            "TLS_AES_128_CCM_SHA256",
            "TLS_AES_256_GCM_SHA384",
            "TLS_RSA_WITH_AES_128_CBC_SHA"
        };

        for (String supportedCipherSuite : supportedCipherSuites) {
            if (cipherSuite.equals(supportedCipherSuite)) {
                return true;
            }
        }

        return false;
    }

    private PrivateKey getPrivateKey(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /* 
    public static void main(String[] args) {
        try {
            TlsShared tlsShared = new TlsShared();
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Path path = Paths.get("app", "src", "main", "java", "http", "project", "networks", "ii", "tls", "certif.crt");
            InputStream is = new FileInputStream(path.toFile());
            Certificate certificate = factory.generateCertificate(is);
            //ServerHello server = new ServerHello(443, certificate, tlsShared);
            //System.out.println("CETIFICADOOOOOOOOOOOOOOOOOOOOOO: "+certificate.toString());
            //server.processClientAndServer();
            is.close();
            //server.receivePremasterSecret();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    */
}
