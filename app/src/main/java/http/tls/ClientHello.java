package http.tls;

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
        // TLS version (3.3 for TLSv1.2)
        out.writeByte(3);
        out.writeByte(3);
    
        // Random: 32-byte challenge
        byte[] challenge = new byte[32];
        random.nextBytes(challenge);
        out.write(challenge);
    
        // List of supported cipher suites
        // TLS_RSA_WITH_AES_128_CBC_SHA is {0x00,0x2F}
        // TLS_RSA_WITH_AES_256_CBC_SHA is {0x00,0x35}
        byte[][] cipherSuites = {
            {0x00, 0x2F},
            {0x00, 0x35}
        };
    
        out.writeByte(0); // Cipher suites length MSB
        out.writeByte(cipherSuites.length * 2); // Cipher suites length LSB
    
        for (byte[] cipherSuite : cipherSuites) {
            out.write(cipherSuite);
        }
    
        out.flush();
    }
    

    public static void main(String[] args) {
        try {
            ClientHello client = new ClientHello("localhost", 4433);
            client.sendClientHello();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

