package cliente;

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
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Coloque seu login: ");
        String nomeUsuario = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        Cliente cliente = new Cliente(socket, nomeUsuario);
        cliente.listenForMesssage();
        cliente.mandarMensagem();

    }
}
