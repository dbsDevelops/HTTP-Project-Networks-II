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
        // TLS version (3.3 for TLSv1.2)
        out.writeByte(3);
        out.writeByte(3);
    
        // Random: 32-byte challenge
        byte[] challenge = new byte[32];
        random.nextBytes(challenge);
        out.write(challenge);
    
        // List of supported cipher suites
        byte[][] cipherSuites = {
            {0x00, 0x2F}, // TLS_RSA_WITH_AES_128_CBC_SHA
            {0x00, 0x35}, // TLS_RSA_WITH_AES_256_CBC_SHA
            {0x00, 0x0A}, // TLS_RSA_WITH_3DES_EDE_CBC_SHA
            {0x00, 0x05}, // TLS_RSA_WITH_RC4_128_SHA
            {0x00, 0x04}, // TLS_RSA_WITH_RC4_128_MD5
            {0x00, 0x2B}, // TLS_RSA_WITH_AES_128_CBC_SHA256
            {0x00, 0x3C}, // TLS_RSA_WITH_AES_256_CBC_SHA256
            {(byte) 0xC0, 0x14}, // TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA
            {(byte) 0xC0, 0x0A}, // TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA
            {(byte) 0xC0, 0x13}, // TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA
            {(byte) 0xC0, 0x09}, // TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA
            {(byte) 0xC0, 0x23}, // TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256
            {(byte) 0xC0, 0x27}, // TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256
        };
        
        //CIPHER SUITES LENGTH:: 2 bytes
        /*
         * out.writeByte(0); // Cipher suites length MSB
         * out.writeByte(cipherSuites.length * 2); // Cipher suites length LSB
         */
        int cipherSuitesLength = cipherSuites.length * 2;
        out.writeByte(cipherSuitesLength >>> 8); // Cipher suites length MSB
        out.writeByte(cipherSuitesLength); // Cipher suites length LSB


    
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

