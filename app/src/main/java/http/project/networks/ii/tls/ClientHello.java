package http.project.networks.ii.tls;

import java.io.*;
import java.net.*;
import java.security.SecureRandom;

public class ClientHello {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private SecureRandom random;

    public ClientHello(String serverName, int port) throws IOException {
        socket = new Socket(serverName, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        random = new SecureRandom();
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
        // List of supported cipher suites
        byte[] cipherSuites = "TLS_AES_128_GCM_SHA256".getBytes();
    
        out.writeBytes(cipherSuites.toString());
        out.flush();
    }
    

    public static void main(String[] args) {
        try {
            ClientHello client = new ClientHello("localhost", 443);
            client.sendClientHello();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

