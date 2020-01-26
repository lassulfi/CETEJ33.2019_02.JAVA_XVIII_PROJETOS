package br.com.utfpr.cliente;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

	private static Socket socket;
	private static DataInputStream input;
	private static DataOutputStream output;
	
	private static final int PORT = 50000;
	private static final String HOST = "127.0.0.1";
	
	public static void main(String[] args) {
		try {
			System.out.printf("\nConectando com o servidor em %s:%d", HOST, PORT);
			socket = new Socket(HOST, PORT);
			
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("Digite uma mensagem: ");
			String message = reader.readLine();
			if(message.equalsIgnoreCase("sair")) {
				socket.close();
			} else {
				output.writeUTF(message);
			}
			
			//TODO: remove the printStackTrace commands.
		} catch (UnknownHostException e) {
			System.out.printf("\nErro ao conectar com o servidor %s", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.printf("Erro ao enviar mensagem ao servidor %s", e.getMessage());
			e.printStackTrace();
		}
	}	
}
