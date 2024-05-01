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
        // Now the server can choose a cipher suite from the received list
        int chosenCipherSuite = -1;
        int cipherSuitesLength = (in.readUnsignedByte() << 8) | in.readUnsignedByte();
        for (int i = 0; i < cipherSuitesLength; i += 2) {
            int cipherSuite = in.readUnsignedShort();
            System.out.println("Received cipher suite: " + cipherSuite);
    
            // Check if the server supports this cipher suite and if it's the most secure one
            if (serverSupportsCipherSuite(cipherSuite) && (chosenCipherSuite == -1 || isMoreSecure(cipherSuite, chosenCipherSuite))) {
                chosenCipherSuite = cipherSuite;
            }
        }

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

    private boolean serverSupportsCipherSuite(int cipherSuite) {
        int[] supportedCipherSuites = {
            0x002F, // TLS_RSA_WITH_AES_128_CBC_SHA
            0x0035, // TLS_RSA_WITH_AES_256_CBC_SHA
            0x000A, // TLS_RSA_WITH_3DES_EDE_CBC_SHA
            // Añadir más suites de cifrado aquí
        };
    
        for (int supportedCipherSuite : supportedCipherSuites) {
            if (cipherSuite == supportedCipherSuite) {
                return true;
            }
        }
    
        return false;
    }

    // Check if cipherSuite1 is more secure than cipherSuite2
    private boolean isMoreSecure(int cipherSuite1, int cipherSuite2) {
        int[] cipherSuitesOrderedBySecurity = {
            0x002F, // TLS_RSA_WITH_AES_128_CBC_SHA
            0x0035, // TLS_RSA_WITH_AES_256_CBC_SHA
            0x000A, // TLS_RSA_WITH_3DES_EDE_CBC_SHA
            // Añadir más suites de cifrado aquí (de menos a más seguras)
        };
    
        int index1 = -1, index2 = -1;
        for (int i = 0; i < cipherSuitesOrderedBySecurity.length; i++) {
            if (cipherSuitesOrderedBySecurity[i] == cipherSuite1) {
                index1 = i;
            }
            if (cipherSuitesOrderedBySecurity[i] == cipherSuite2) {
                index2 = i;
            }
        }
    
        // If cipherSuite1 is not found in the list, consider cipherSuite1 less secure
        if (index1 == -1) {
            return false;
        }

        // If cipherSuite2 is not found in the list, consider cipherSuite1 more secure
        if (index2 == -1) {
            return true;
        }

        // If both are found, the one with the lower index is more secure
        return index1 < index2;
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


