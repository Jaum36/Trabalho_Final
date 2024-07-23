package Interface;

import cliente.Aluno;
import cliente.Cliente;
import cliente.Professores;
import cliente.Tecnico;
import server.Cadastro;
import server.FileUtil;
import server.FileUtilProf;
import server.FileUtilTecnicos;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class TelaPrincipal {
    static Cadastro cadastro = new Cadastro();
    private Socket socket;
    private PrintWriter out;
    private Scanner in;




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

        FileUtil.saveLogin(aluno.getLogin(), aluno.getSenha(), aluno.getIngresso());

    }

    //public void killClient(String login) {
        //out.println("/kill " + login);
    //}

    public void cadastrarProf(){
        Professores professores = new Professores();
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o login do professor: ");
        String valor = sc.nextLine();
        if (FileUtilProf.loginExists(valor)) {
            System.out.println("Erro ao cadastrar professor! Login já existe.");
            return;
        }
        professores.setLogin(valor);

        System.out.println("Digite a senha do professor: ");
        valor = sc.nextLine();
        professores.setSenha(valor);

        System.out.println("Digite a titulação do professor: ");
        valor = sc.nextLine();
        professores.setTitulacao(valor);

        FileUtilProf.saveLogin(professores.getLogin(), professores.getSenha(), professores.getTitulacao());
    }
    public void listarProfessores(){
        List<String> logins = FileUtilProf.readLogins();
        if (logins.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
        } else {
            System.out.println("Professores cadastrados:");
            for (String login : logins) {
                System.out.println("Professor, senha e titulação: "+login+"\n");
            }
        }
    }

    public void listarAlunos(){
        List<String> logins = FileUtil.readLogins();
        if (logins.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("Alunos cadastrados:");
            for (String login : logins) {
                System.out.println("Aluno, senha e ano de ingresso: "+login+"\n");
            }
        }
    }

    public void cadastrarTecnicos(){
        Tecnico tecnico = new Tecnico();
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o login do professor: ");
        String valor = sc.nextLine();
        if (FileUtilTecnicos.loginExists(valor)) {
            System.out.println("Erro ao cadastrar professor! Login já existe.");
            return;
        }
        tecnico.setLogin(valor);

        System.out.println("Digite a senha do professor: ");
        valor = sc.nextLine();
        tecnico.setSenha(valor);


        FileUtilTecnicos.saveLogin(tecnico.getLogin(), tecnico.getSenha());
    }

    public void listarTecnicos(){
        List<String> logins = FileUtilTecnicos.readLogins();
        if (logins.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        }else {
            System.out.println("Alunos cadastrados:");
            for (String login : logins) {
                System.out.println("Aluno, senha e ano de ingresso: "+login+"\n");
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
            System.out.println("(1) - Técnico\n(2) - Usuarios\n");
            System.out.println("Digite uma opção: ");
            opcao2 = scanner.nextInt();
            scanner.nextLine();

            if (opcao2 == 1) {
                int opcao = -1;
                System.out.println("Coloque seu login: ");
                String nomeUsuario = scanner.nextLine();
                String senha = FileUtilTecnicos.buscarSenhaPorLogin(nomeUsuario);

                if (senha != null) {
                    System.out.println("Informe sua senha:");
                    String senhaInformada = scanner.nextLine();

                    if (senha.equals(senhaInformada)) {
                        while (opcao != 0) {


                            System.out.println("(1) - Inicie o servidor\n(2) - Cadastrar Aluno\n(3) - Listar Alunos\n(4) - Cadastrar Professores" +
                                    "\n(5) - Listar Professores\n(6) - Cadastrar Técnicos\n(7) - Listar Tecnicos");
                            opcao = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (opcao == 1) {
                                System.out.println(" ");
                            } else if (opcao == 2) {
                                tela.cadastrarAluno();
                            } else if (opcao == 3) {
                                tela.listarAlunos();
                            } else if (opcao == 4) {
                                tela.cadastrarProf();
                            } else if (opcao == 5) {
                                tela.listarProfessores();
                            } else if (opcao == 6) {
                                tela.cadastrarTecnicos();
                            } else if (opcao == 7) {
                                tela.listarTecnicos();
                            } else if (opcao == 0) {
                                System.out.println(" ");
                            } else {
                                System.out.println("Coloque um valor válido!!!");
                            }
                    }
                        }else {
                        System.out.println("Senha incorreta!");
                    }
                } else {
                    System.out.println("Login incorreto!");
                }

            } else if (opcao2 == 2) {
                int opcaoA = -1;

                while (opcaoA != 0) {
                    System.out.println("Selecione uma opção:\n(1) - Aluno\n(2) - Professor");
                    opcaoA = scanner.nextInt();
                    scanner.nextLine();

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

                    if (opcaoA == 2) {
                        System.out.println("Coloque seu login: ");
                        String nomeUsuario = scanner.nextLine();
                        String senha = FileUtilProf.buscarSenhaPorLogin(nomeUsuario);

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