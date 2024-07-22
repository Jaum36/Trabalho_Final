package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Controle {

    public void cadastrarCliente(){

    }

    public static void main(String[] args) throws IOException {


        Scanner scanner = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(1234);
        MainServer server = new MainServer(serverSocket);
        String opcao = scanner.nextLine();
        System.out.println("(0) - Inicie o servidor");
        if(opcao.equals("0")) {
            System.out.println("Servidor Inciado!\nPorta: 1234");
            server.StartServer();
        }
        else{
            System.out.println("Coloque um valor válido!!!");
        }
    }
}
