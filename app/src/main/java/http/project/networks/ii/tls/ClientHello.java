package http.project.networks.ii.tls;

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import org.apache.commons.codec.binary.Hex;

public class ClientHello {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private SecureRandom random;
    private Certificate certificate;
    private TlsShared tlsShared;
    private byte[] clientRandom;
    private byte[] serverRandom;
    public SecretKey symmetricKey;

    public ClientHello(String serverName, int port, TlsShared tlsShared) throws IOException {
        socket = new Socket(serverName, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        random = new SecureRandom();
        certificate = null;
        this.tlsShared = tlsShared;
        this.clientRandom = new byte[32];
        this.serverRandom = new byte[32];
    }

    public void sendClientHello() throws IOException {
        // TLS version (3.3 for TLSv1.3)
        out.writeByte(3);
        out.writeByte(3);
    
        //First random generated by the client
        random.nextBytes(this.clientRandom);
        out.write(this.clientRandom);

        // Cipher suite
        String cipherSuite = "TLS_AES_256_GCM_SHA384";
        out.writeUTF(cipherSuite);
        
        out.flush();
    }

    public void validateCertificateFromServer() {
        try {
            // Read TLS version
            byte major = in.readByte();
            byte minor = in.readByte();

            System.out.println("ServerHello: TLS version " + major + "." + minor);

            // Read server random
            in.readFully(this.serverRandom);

            // Read cipher suite
            String cipherSuite = in.readUTF();

            // Read certificate
            int certificateLength = in.readInt();
            byte[] certificateBytes = new byte[certificateLength];
            in.readFully(certificateBytes);

            certificate = CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(certificateBytes));
    
            //Not valid certificate will throw an exception
            PublicKey publicKey = certificate.getPublicKey();
            validateCertificate(publicKey);
            System.out.println("CLIENT VERIFICATION SUCCESSFUL!. Selected cipher suite: " + cipherSuite+ ",\n Certificate: \n" + certificate.toString());
        } catch (IOException | CertificateException | InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | SignatureException e) {
            e.printStackTrace();
        }
    }

    private void validateCertificate(PublicKey publicKey) throws CertificateException, 
                InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
                try {
                    X509Certificate x509Certificate = (X509Certificate) certificate;
                    x509Certificate.checkValidity();
                    x509Certificate.verify(publicKey);
                    System.out.println("Client Verification: Certificate is valid.");
                } catch (CertificateExpiredException e) {
                    System.out.println("Client Verification: Certificate is expired.");
                } catch (CertificateNotYetValidException e) {
                    System.out.println("Client Verification: Certificate is not yet valid.");
                }
    }

    private void sendPremasterSecret() throws Exception {
        // Generate pre-master secret
        byte[] preMasterSecret = new byte[48];
        random.nextBytes(preMasterSecret);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());
        byte[] encryptedPreMasterSecret = cipher.doFinal(preMasterSecret);

        // Send encrypted pre-master secret
        out.writeInt(encryptedPreMasterSecret.length);
        out.write(encryptedPreMasterSecret);
        out.flush();

        //Generate symmetric key
        tlsShared.setClientRandom(this.clientRandom);
        tlsShared.setServerRandom(this.serverRandom);
        tlsShared.generateSymmetricKey(preMasterSecret);
        this.symmetricKey = tlsShared.getSymmetricKey();
        System.out.println("Client: Sent pre-master secret and have the symmetric key");
        System.out.println("\nSymmetric key: " + Hex.encodeHexString(this.symmetricKey.getEncoded()));
    }
    
    public static void main(String[] args) { 
        try {
            TlsShared tlsShared = new TlsShared();
            ClientHello client = new ClientHello("localhost", 443, tlsShared);
            client.sendClientHello();
            client.validateCertificateFromServer();
            client.sendPremasterSecret();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}