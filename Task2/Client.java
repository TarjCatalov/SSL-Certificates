import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * A password manager client.
 * You will need to give it an interface with the options to send the server a password to store, to
 * get a stored password from the server, and to end the programs. You will also have to implement RSA
 * and use it correctly so that the server and any possible interceptor can never read the passwords.
 */
public class Client {

    /**
     * Send and receive a message in bytes to the specified host at the specified port.
     * 
     * @return a byte array containing the message that is received back from the server
     */
    public static byte[] sendReceive(String hostname, int port, byte[] message) {
        try (
            Socket s = new Socket(hostname, port);
        ) {
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(message);  // Send the message to the server
            out.flush();

            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            byte buffer[] = (byte[]) in.readObject(); // Receive a message from the server
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        RSA rsa = new RSA(); 
        rsa.primeReader();
        rsa.keyGen();
        
        String hostname = "localhost";
        int port = 22500;

        Scanner console = new Scanner(System.in);

        System.out.println("Welcome to the SENG2250 password manager client, you have the following options:");
        boolean state = true;
        while (state == true) {
            System.out.println("- store <website> <password>");
            System.out.println("- get <website>");
            System.out.println("- end");
            System.out.print(">>> ");
            String answer = console.nextLine();

            if (answer.startsWith("store")) {
                String[] parts = answer.split(" ");
                if (parts.length != 3) {
                    System.out.println("Invalid Input!: 3 words only when using 'store <website> <password>!'");
                    continue;
                } else {
                    String website = parts[1];
                    String password = parts[2];

                    BigInteger passwordBigInt = new BigInteger(password.getBytes());
                    BigInteger encryptedPassword = rsa.encrypt(passwordBigInt);
                    byte[] encryptedBytes = encryptedPassword.toByteArray();

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    outputStream.write(website.getBytes());    
                    outputStream.write(' ');               
                    outputStream.write(encryptedBytes);    
                    byte[] combinedByte = outputStream.toByteArray();

                    byte[] response = sendReceive(hostname, port, combinedByte);
                    System.out.println(new String(response));
                }
            } else if (answer.startsWith("get")) {
                String[] parts = answer.split(" ");
                if (parts.length != 2) {
                    System.out.println("Invalid Input!: 2 words only when using 'get <website>!'");
                    continue;
                } else {
                    String website = parts[1];

                    byte[] response = sendReceive(hostname, port, website.getBytes());
        
                    if (response == null || response.length == 0) {
                        System.out.println("No password found for " + website);
                        continue;
                    }
        
                    BigInteger encryptedPassword = new BigInteger(response);
                    String decryptedPassword = rsa.decrypt(encryptedPassword);
        
                    System.out.println(decryptedPassword);
                }
                
            } else if (answer.equalsIgnoreCase("end")) {
                byte[] response = sendReceive(hostname, port, "end".getBytes());
                System.out.println(new String(response));
                break;
            } else {
                System.out.println("Invalid Input!: Please follow the options outline");
            }
        }
        console.close();
        
    }
}