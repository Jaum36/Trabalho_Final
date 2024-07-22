package server;

import cliente.Cliente;
import cliente.ClienteHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainServer {
    private ServerSocket serverSocket;

    private static List<Cliente> clientes = Collections.synchronizedList(new ArrayList<>());


    public MainServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;

    }

    public void StartServer() {
        try {
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Um novo usuário foi cadastrado!!");
                ClienteHandler clienteHandler = new ClienteHandler(socket);

                Thread thread = new Thread(clienteHandler);
                thread.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void listClients(PrintWriter out) {
        StringBuilder clientList = new StringBuilder("Clientes conectados:\n");
        synchronized (clientes) {
            for (Cliente cliente : clientes) {
                clientList.append(cliente.getNomeUsuario()).append("\n");
            }
        }
        out.println(clientList);
    }

    public static List<String> getConnectedClients() {
        List<String> logins = new ArrayList<>();
        synchronized (clientes) {
            for (Cliente cliente : clientes) {
                logins.add(cliente.getNomeUsuario());
            }
        }
        return logins;
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
