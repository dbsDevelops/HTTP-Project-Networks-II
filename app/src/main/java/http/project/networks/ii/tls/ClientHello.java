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

public class ClientHello {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private SecureRandom random;
    private Certificate certificate;
    public ClientHello(String serverName, int port) throws IOException {
        socket = new Socket(serverName, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        random = new SecureRandom();
        certificate = null;
    }

    public void sendClientHello() throws IOException {
        // TLS version (3.3 for TLSv1.3)
        out.writeByte(3);
        out.writeByte(3);
    
        // Random: 32-byte challenge
        byte[] challenge = new byte[32];
        random.nextBytes(challenge);
        out.write(challenge);

        //USAR SIEMPRE : TLS_AES_128_GCM_SHA256
        byte[] cipherSuites = "TLS_AES_128_GCM_SHA256".getBytes();
    
        out.writeBytes(cipherSuites.toString());
        out.flush();
    }

    public void handleResponse() {
        try {
            // Read TLS version
            byte major = in.readByte();
            byte minor = in.readByte();
    
            System.out.println("ServerHello: TLS version " + major + "." + minor);
    
            // Read server random
            byte[] serverRandom = new byte[32];
            in.readFully(serverRandom);
    
            // Read cipher suite
            byte[] cipherSuiteBytes = new byte[in.available()];
            in.readFully(cipherSuiteBytes);
            String cipherSuite = new String(cipherSuiteBytes);
    
            System.out.println("ServerHello: Cipher suite " + cipherSuite);
    
            // Read certificate
            int certificateLength = in.readInt();
            byte[] certificateBytes = new byte[certificateLength];
            in.readFully(certificateBytes);
    
            certificate = CertificateFactory.getInstance("X.509")
                    .generateCertificate(new ByteArrayInputStream(certificateBytes));
    
            //Not valid certificate will throw an exception
            PublicKey publicKey = certificate.getPublicKey();
            validateCertificate(publicKey);
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
                    System.out.println("Certificate is valid.");
                } catch (CertificateExpiredException e) {
                    System.out.println("Certificate is expired.");
                } catch (CertificateNotYetValidException e) {
                    System.out.println("Certificate is not yet valid.");
                }
    }
    

    public static void main(String[] args) {
        try {
            ClientHello client = new ClientHello("localhost", 443);
            client.sendClientHello();
            client.handleResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

