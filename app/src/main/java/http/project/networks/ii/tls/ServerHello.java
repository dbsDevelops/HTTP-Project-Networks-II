package http.project.networks.ii.tls;

import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

public class ServerHello {
    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private SecureRandom random;
    private Certificate certificate;

    public ServerHello(int port, Certificate certificate) throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        random = new SecureRandom();
        this.certificate = certificate;
    }

    public void sendServerHello() throws IOException, CertificateEncodingException {
        // TLS version (3.3 for TLSv1.3)
        out.writeByte(3);
        out.writeByte(3);
    
        // Random: 32-byte challenge
        byte[] challenge = new byte[32];
        random.nextBytes(challenge);
        out.write(challenge);

        byte[] cipherSuites = "TLS_AES_128_GCM_SHA256".getBytes();
        if(serverSupportsCipherSuite("TLS_AES_128_GCM_SHA256")) {
            out.write(cipherSuites);
        } else {
            out.writeBytes("No cipher suites supported");
        }

        // Certificate
        byte[] cert = certificate.getEncoded();
        out.write(cert);

        // ServerHelloDone
        out.writeByte(0);
        out.writeByte(0);
        out.flush();
    }

    private boolean serverSupportsCipherSuite(String cipherSuite) {
        String[] supportedCipherSuites = {
            "TLS_AES_128_GCM_SHA256",
            "TLS_AES_256_GCM_SHA384",
            "TLS_CHACHA20_POLY1305_SHA256",
            "TLS_AES_128_CCM_SHA256"
        };
    
        for (String supportedCipherSuite : supportedCipherSuites) {
            if (cipherSuite.equals(supportedCipherSuite)) {
                return true;
            }
        }
    
        return false;
    } 

    public static void main(String[] args) {
        try {
            
            Certificate certificate = null; //Put the certificate
            ServerHello server = new ServerHello(4433, certificate);
            server.sendServerHello();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


