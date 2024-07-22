package Interface;

import cliente.Aluno;
import cliente.Cliente;
import server.Cadastro;
import server.FileUtil;


import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class TelaPrincipal {
    static Cadastro cadastro = new Cadastro();
    private Socket socket;


    public void cadastrarAluno(){
        Aluno aluno = new Aluno();
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o login do aluno: ");
        String valor = sc.nextLine();
        if (FileUtil.loginExists(valor)) {
            System.out.println("Erro ao cadastrar aluno! Login já existe.");
            return;
        }
        aluno.setLogin(valor);

        System.out.println("Digite a senha do aluno: ");
        valor = sc.nextLine();
        aluno.setSenha(valor);

        System.out.println("Digite o ano de ingresso do aluno: ");
        valor = sc.nextLine();
        aluno.setIngresso(valor);

        FileUtil.saveLogin(aluno.getLogin(), aluno.getSenha());

    }

    public void listarAlunos(){
        List<String> logins = FileUtil.readLogins();
        if (logins.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("Alunos cadastrados:");
            for (String login : logins) {
                System.out.println("Aluno e senha: "+login+"\n");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        TelaPrincipal tela = new TelaPrincipal();
        List<String> logins = FileUtil.readLogins();
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

                    if (opcaoA == 1) {
                        System.out.println("Coloque seu login: ");
                        String nomeUsuario = scanner.nextLine();
                        String senha = FileUtil.buscarSenhaPorLogin(nomeUsuario);

                        if (senha != null) {
                            System.out.println("Informe sua senha:");
                            String senhaInformada = scanner.nextLine();

                            if (senha.equals(senhaInformada)) {
                                Cliente cliente = new Cliente(socket, nomeUsuario);
                                System.out.println("Login realizado com sucesso!");
                                cliente.listenForMesssage();
                                cliente.mandarMensagem();
                            } else {
                                System.out.println("Senha incorreta!");
                            }
                        } else {
                            System.out.println("Login incorreto!");
                        }
                    }
                }
            }
        }
    }
}
