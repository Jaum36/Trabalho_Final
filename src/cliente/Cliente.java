package cliente;

import server.Cadastro;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String nomeUsuario;

    public Cliente(Socket socket, String nomeUsuario) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nomeUsuario = nomeUsuario;
        } catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void mandarMensagem() {
        try {
            bufferedWriter.write(nomeUsuario);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected()) {
                String mensagemEnviar = scanner.nextLine();
                bufferedWriter.write(mensagemEnviar);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } catch(IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMesssage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mensagemChat;

                while(socket.isConnected()) {
                    try {
                        mensagemChat = bufferedReader.readLine();
                        System.out.println(mensagemChat);
                    } catch(IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Cadastro c = new Cadastro();

        System.out.println("Selecione uma opção:\n(1) - Aluno\n(2) - Professor");
        int opcao = scanner.nextInt();

        if(opcao == 1) {
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



        //System.out.println("Coloque seu login: ");
       // String nomeUsuario = scanner.nextLine();



    }
}
