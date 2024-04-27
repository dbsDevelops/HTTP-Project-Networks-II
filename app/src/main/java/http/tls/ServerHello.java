package http.tls;

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
        // Read the length of the cipher suites list
        int cipherSuitesLength = in.readUnsignedShort();

        // Read each cipher suite (each one is 2 bytes)
        for (int i = 0; i < cipherSuitesLength; i += 2) {
            int cipherSuite = in.readUnsignedShort();
            System.out.println("Received cipher suite: " + cipherSuite);
        }

        // Now the server can choose a cipher suite from the received list
        // Take the first one for example
        int chosenCipherSuite = in.readUnsignedShort();

        // TLS version (3.3 for TLSv1.2)
        out.writeByte(3);
        out.writeByte(3);

        // Random: 32-byte challenge
        byte[] challenge = new byte[32];
        random.nextBytes(challenge);
        out.write(challenge);

        // Cipher suite
        out.writeShort(chosenCipherSuite);

        // Send the certificate
        byte[] encodedCert = certificate.getEncoded();
        out.writeInt(encodedCert.length);
        out.write(encodedCert);

        out.flush();
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


