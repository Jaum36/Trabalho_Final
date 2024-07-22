package server;

import cliente.ClienteHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainServer {
    private ServerSocket serverSocket;

    public MainServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;

    }

    public void StartServer() {
        try {
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Um novo usuário se conectou!!");
                ClienteHandler clienteHandler = new ClienteHandler(socket);

                Thread thread = new Thread(clienteHandler);
                thread.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void CloseServer() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        ServerSocket serverSocket = new ServerSocket(1234);
        MainServer server = new MainServer(serverSocket);
        System.out.println("Server inciado!\nPorta: 1234");
        server.StartServer();

    }
}
