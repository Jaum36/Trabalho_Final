package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteHandler implements Runnable {
    public static ArrayList<ClienteHandler> clienteHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String nomeUsuario;

    public String getNomeUsuario(){
        return nomeUsuario;
    }

    public ClienteHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nomeUsuario = bufferedReader.readLine();
            clienteHandlers.add(this);
            broadcastMessage("SERVIDOR: "+nomeUsuario+" entrou no chat");
        } catch(Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }



    public void run() {
        String mensagemUsuario;

        while (socket.isConnected()) {
            try {
                mensagemUsuario = bufferedReader.readLine();
                broadcastMessage(nomeUsuario+": "+mensagemUsuario);
            } catch(Exception e) {
                closeEverything(socket,bufferedReader, bufferedWriter);
                break;
            }
        }

    }



    public void broadcastMessage(String mensagemEnviada) {
        for(ClienteHandler clienteHandler : clienteHandlers) {
            try {
                if(!clienteHandler.nomeUsuario.equals(nomeUsuario)) {
                    clienteHandler.bufferedWriter.write(mensagemEnviada);
                    clienteHandler.bufferedWriter.newLine();
                    clienteHandler.bufferedWriter.flush();
                }
            } catch(Exception e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClienteHandler() {
        clienteHandlers.remove(this);
        broadcastMessage("Server: "+ nomeUsuario+ " está offline" );
    }

    public void listarClienteHandler(){
        for(ClienteHandler clienteHandler : clienteHandlers) {
            broadcastMessage(clienteHandler.nomeUsuario);
        }
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClienteHandler();
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
}
