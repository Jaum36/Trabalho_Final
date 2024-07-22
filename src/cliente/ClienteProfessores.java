package cliente;

import server.Cadastro;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteProfessores extends Cliente{

    public ClienteProfessores(Socket socket, String nomeUsuario) {
        super(socket, nomeUsuario);
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Cadastro c = new Cadastro();

        System.out.println("Selecione uma opção:\n(1) - Aluno\n(2) - Professor");
        int opcao = scanner.nextInt();

        if(opcao == 1) {
            Professores prof = new Professores();
            System.out.println("Coloque seu login: ");
            String nomeUsuario = scanner.nextLine();
            prof.setLogin(nomeUsuario);

            if(prof.getLogin().equals(c.buscarProfessor(nomeUsuario).getLogin())){
                System.out.println("Informe sua senha");
                String senha = scanner.nextLine();
                prof.setSenha(senha);

                if(prof.getSenha().equals(c.buscarProfessor(nomeUsuario).getSenha())){
                    Socket socket = new Socket("localhost", 1234);
                    Cliente cliente = new Cliente(socket, nomeUsuario);
                    cliente.listenForMesssage();
                    cliente.mandarMensagem();
                }
                else{
                    System.out.println("Senha incorreta!");
                }
            }
            else{
                System.out.println("Login incorreto!");
            }
        }
    }
}

