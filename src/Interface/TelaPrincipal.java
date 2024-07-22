package Interface;

import cliente.Aluno;
import cliente.Cliente;
import cliente.ClienteAluno;
import server.Cadastro;
import server.Controle;
import server.MainServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TelaPrincipal {
    static Cadastro cadastro = new Cadastro();

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
        TelaPrincipal tela = new TelaPrincipal();

        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 1234);

        int opcao2 = -1;

        while (opcao2 != 0) {
            System.out.println("(1) - Técnico\n(2) - Aluno\n");
            opcao2 = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (opcao2 == 1) {
                int opcao = -1;

                while (opcao != 0) {
                    System.out.println("(1) - Inicie o servidor\n(2) - Cadastrar Aluno\n(3) - Listar Alunos");
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (opcao == 2) {
                        tela.cadastrarAluno();
                    } else if (opcao == 3) {
                        tela.listarAlunos();
                    } else {
                        System.out.println("Coloque um valor válido!!!");
                    }
                }
            } else if (opcao2 == 2) {
                int opcaoA = -1;

                while (opcaoA != 0) {
                    System.out.println("Selecione uma opção:\n(1) - Aluno");
                    opcaoA = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (opcaoA == 1 && !cadastro.getListaAluno().isEmpty()) {
                        System.out.println("Coloque seu login: ");
                        String nomeUsuario = scanner.nextLine();
                        Aluno alunoBuscado = cadastro.buscarAluno(nomeUsuario);

                        if (alunoBuscado != null && nomeUsuario.equals(alunoBuscado.getLogin())) {
                            System.out.println("Informe sua senha");
                            String senha = scanner.nextLine();

                            if (senha.equals(alunoBuscado.getSenha())) {
                                Cliente cliente = new Cliente(socket, nomeUsuario);
                                System.out.println("Login realizado com sucesso!");
                                System.out.println("Agora você entrou no chat!!");
                                cliente.listenForMesssage();
                                cliente.mandarMensagem();

                            } else {
                                System.out.println("Senha incorreta!");
                            }
                        } else {
                            System.out.println("Login incorreto!");
                        }
                    } else if (opcaoA == 1 && cadastro.getListaAluno().isEmpty()) {
                        System.out.println("Não há alunos cadastrados!!");
                    }
                }
            }
        }
    }
}
