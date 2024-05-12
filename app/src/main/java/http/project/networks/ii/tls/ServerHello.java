package http.project.networks.ii.tls;

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.nio.file.Paths;
import java.nio.file.Path;

public class ServerHello {
    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private SecureRandom random;
    private byte[] clientRandom;
    private byte[] serverRandom;
    private Certificate certificate;
    private TlsShared tlsShared;

    public ServerHello(int port, Certificate certificate, TlsShared tlsShared) throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        random = new SecureRandom();
        this.certificate = certificate;
        this.tlsShared = tlsShared;
        this.clientRandom = new byte[32];
        this.serverRandom = new byte[32];
    }

    public void processClientAndServer() throws IOException, InvalidKeyException,
             CertificateException{
                //1. CLIENT HELLO
        // Read TLS version
        byte major = in.readByte();
        byte minor = in.readByte();

        System.out.println("ClientHello: TLS version " + major + "." + minor);

        // Read client random
        in.readFully(this.clientRandom);

        // Read cipher suite
        String cipherSuite = in.readUTF();

        if (serverSupportsCipherSuite(cipherSuite)) {
            sendServerHello(cipherSuite); //2. SERVER HELLO with the cipher suite selected
        } else {
            System.out.println("Server does not support ClientHello cipher suite " + cipherSuite);
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
        
        System.out.println("ServerHello: Sent ServerHello with cipher suite " + cipherSuite);
    }

    private void receivePremasterSecret() throws Exception {
        byte[] preMasterSecret = new byte[48];
        in.readFully(preMasterSecret);
        tlsShared.setClientRandom(this.clientRandom);
        tlsShared.setServerRandom(this.serverRandom);
        tlsShared.generateSymmetricKey(preMasterSecret);
        System.out.println("ServerHello: Received pre-master secret and have the symmetric key");
    }

    private boolean serverSupportsCipherSuite(String cipherSuite) {
        String[] supportedCipherSuites = {
            "TLS_AES_128_GCM_SHA256",
            "TLS_AES_256_GCM_SHA384",
            "TLS_CHACHA20_POLY1305_SHA256",
            "TLS_AES_128_CCM_SHA256",
            "TLS_AES_256_GCM_SHA384"
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
            TlsShared tlsShared = new TlsShared();
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Path path = Paths.get("app", "src", "main", "java", "http", "project", "networks", "ii", "tls", "certificate.crt");
            InputStream is = new FileInputStream(path.toFile());
            Certificate certificate = factory.generateCertificate(is);
            ServerHello server = new ServerHello(443, certificate, tlsShared);
            //System.out.println("CETIFICADOOOOOOOOOOOOOOOOOOOOOO: "+certificate.toString());
            server.processClientAndServer();
            is.close();
            server.receivePremasterSecret();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
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
}
