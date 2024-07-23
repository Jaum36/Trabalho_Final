package server;

import cliente.Cliente;
import cliente.ClienteHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MainServer {
    private ServerSocket serverSocket;

    private static List<Cliente> clientes = Collections.synchronizedList(new ArrayList<>());
    private static Set<ClienteHandler> clientesConectados = new HashSet<>();


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



    public void CloseServer() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void killClient(String login) {
        synchronized (clientesConectados) {
            for (ClienteHandler cliente : clientesConectados) {
                if (cliente.getNomeUsuario().equals(login)) {
                    cliente.removeClienteHandler();
                    clientesConectados.remove(cliente);
                    System.out.println("Cliente " + login + " desconectado.");
                    break;
                }
            }
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
