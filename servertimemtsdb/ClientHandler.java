package servertimemtsdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ClientHandler extends Thread {

    private final Socket client;
    private BufferedReader input;
    private PrintWriter output;

    public ClientHandler(Socket client) {
        this.client = client;
        try {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {

        String request = null;
        String response;
        System.out.println("Client connectted  " + client.getInetAddress().getHostName());
        while (true) {

            try {
                request = input.readLine();
                if (IPController.banned.contains(client.getInetAddress().getHostAddress())) {
                    output.println("BANNED");
                    break;
                }
                else if (client.getInetAddress().getHostAddress().equals(IPController.lastIP)) {
                    IPController.banned.add(client.getInetAddress().getHostAddress());
                    output.println("BANNED");
                    break;
                }
                else IPController.lastIP=client.getInetAddress().getHostAddress();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (request == null) {
                return;
            }
            request = request.toLowerCase();
            System.out.println("Request from " + client.getInetAddress().getHostName() + ": " + request);
            if (request.equals("time")) {
                output.println("" + new Date().toString());
            } else if (request.equals("exit")) {
                System.out.println("Client disconnected " + client.getInetAddress().getHostName());
                output.println("Disconnected");
                break;
            } else {
                output.println("N/A");
            }
        }
        
        try {
            this.client.close();
            this.input.close();
            this.output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
