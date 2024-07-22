package cliente;

import server.Cadastro;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteAluno extends Cliente {

    public ClienteAluno(Socket socket, String nomeUsuario) {
        super(socket, nomeUsuario);
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Cadastro c = new Cadastro();

        System.out.println("Selecione uma opção:\n(1) - Aluno");
        int opcao = scanner.nextInt();

        if(opcao == 1 && !c.getListaAluno().isEmpty()) {
            Aluno aluno = new Aluno();
            System.out.println("Coloque seu login: ");
            String nomeUsuario = scanner.nextLine();
            aluno.setLogin(nomeUsuario);

            if(aluno.getLogin().equals(c.buscarAluno(nomeUsuario).getLogin())){
                System.out.println("Informe sua senha");
                String senha = scanner.nextLine();
                aluno.setSenha(senha);

                if(aluno.getSenha().equals(c.buscarAluno(nomeUsuario).getSenha())){
                    Socket socket = new Socket("localhost", 1234);
                    ClienteAluno clienteAluno = new ClienteAluno(socket, nomeUsuario);
                    clienteAluno.listenForMesssage();
                    clienteAluno.mandarMensagem();
                }
                else{
                    System.out.println("Senha incorreta!");
                }
            }
            else{
                System.out.println("Login incorreto!");
            }
        }

        else if(opcao == 1 && c.getListaAluno().isEmpty()){
            System.out.println("Não há alunos cadastrados!");
        }
    }

}
