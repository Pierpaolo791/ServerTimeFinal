/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertimemtsdb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Pierpaolo
 * if (client.getInetAddress().getHostAddress().equals(IPController.lastIP)) {
            
        }
        else IPController.lastIP =  client.getInetAddress().getHostAddress();
 */
public class ServerTimeMTSDB {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3233);
        
        while(true) {
            Socket s = server.accept();
            new ClientHandler(s).start();
        }
    }
    
}
