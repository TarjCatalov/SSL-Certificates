    import java.io.ObjectInputStream;
    import java.io.ObjectOutputStream;
    import java.net.Socket;
    import java.util.HashMap;
    import java.net.ServerSocket;

    /**
     * A password manager server.
     * You will need to give it the ability to correctly store passwords and to send them back to the
     * client when requested.
     */
    public class Server {
        private static HashMap<String, byte[]> passwordStore = new HashMap<>();

        public static boolean handleMessage(Socket conn, byte[] data) {
            try (
                ObjectOutputStream out = new ObjectOutputStream(conn.getOutputStream());
            ) {
                String message = new String(data);

                if (message.trim().equals("end")) {
                    out.writeObject("bye.".getBytes());
                    return true;
                }

                int separatorIndex = -1;
                for (int i = 0; i < data.length; i++) {
                    if (data[i] == ' ') {
                        separatorIndex = i;
                        break;
                    }
                }

                if (separatorIndex != -1) {
                    // Store message part
                    String website = new String(data, 0, separatorIndex);
                    byte[] encryptedPassword = new byte[data.length - separatorIndex - 1];
                    System.arraycopy(data, separatorIndex + 1, encryptedPassword, 0, encryptedPassword.length);

                    passwordStore.put(website, encryptedPassword);
                    out.writeObject("Password successfully stored.".getBytes());
                } else {
                    // Get message part
                    String website = message.trim();
                    if (passwordStore.containsKey(website)) {
                        byte[] encryptedPassword = passwordStore.get(website);
                        out.writeObject(encryptedPassword);
                        System.out.println("Sent password for website: " + website);
                    } else {
                        out.writeObject(new byte[0]); 
                        System.out.println("No password found for website: " + website);
                    }
                }

                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        public static void main(String[] args) {
            int port = 22500;  // Arbitrary non-privileged port
            boolean end = false;
            System.out.println("Listening for a client on port " + port);

            try (ServerSocket serverSocket = new ServerSocket(port)) {
                do {
                    Socket socket = serverSocket.accept();
                    System.out.println("Connected by " + socket.getInetAddress() + ":" + socket.getPort());

                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    byte[] data = (byte[]) in.readObject();
                    end = handleMessage(socket, data);

                    in.close();
                    socket.close();
                } while (!end);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }