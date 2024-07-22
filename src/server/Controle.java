package server;

import cliente.Aluno;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Controle {
    Cadastro cadastro = new Cadastro();

    public void cadastrarAluno(){
        Aluno aluno = new Aluno();
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o login do aluno: ");
        String valor = sc.nextLine();
        aluno.setLogin(valor);

        System.out.println("Digite a senha do aluno: ");
        valor = sc.nextLine();
        aluno.setSenha(valor);

        System.out.println("Digite o ano de ingresso do aluno: ");
        valor = sc.nextLine();
        aluno.setIngresso(valor);

        if(cadastro.cadastrarAluno(aluno)){
            System.out.println("Cadastrado com sucesso!");
        }
        else{
            System.out.println("Erro ao cadastrar aluno!\nAluno já cadastrado");
        }
    }

    public void listarAlunos(){
        String concat = "";
        for(Aluno aluno : cadastro.getListaAluno()) {
            concat += "Nome: "+aluno.getLogin()+"\nIngresso: "+ aluno.getIngresso();
        }
        System.out.println(concat);
    }

    public static void main(String[] args) throws IOException {
        Controle control = new Controle();

        Scanner scanner = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(1234);
        MainServer server = new MainServer(serverSocket);

        int opcao = -1;

        while(opcao != -2) {
            System.out.println("(0) - Inicie o servidor\n(1) - Cadastrar Aluno\n(2) - Listar Alunos");
            opcao = scanner.nextInt();
            if (opcao == 0) {
                System.out.println("Servidor Inciado!\nPorta: 1234");
                server.StartServer();
            } else if (opcao == 1) {
                control.cadastrarAluno();
            }
            else if (opcao == 2) {
                control.listarAlunos();
            }
            else {
                System.out.println("Coloque um valor válido!!!");
            }
        }
    }
}
