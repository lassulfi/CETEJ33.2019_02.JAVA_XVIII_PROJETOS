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
			
			while(true) {
				System.out.print("\nDigite uma mensagem para converter para uppercase ou "
						+ "digite sair para encerrar a aplicação: ");
				String message = reader.readLine();
				output.writeUTF(message);
				if(message.equalsIgnoreCase("sair")) {
					System.out.printf("\nFechando conexão com servidor em %s:%d", HOST, PORT);
					socket.close();
					break;
				}
				String convertedMessage = input.readUTF();
				System.out.printf("Mensagem convertida para uppercase: %s\n", convertedMessage);
			}
			
			socket.close();
			output.close();	
			
		} catch (UnknownHostException e) {
			System.out.printf("\nErro ao conectar com o servidor %s", e.getMessage());
		} catch (IOException e) {
			System.out.printf("Erro ao enviar mensagem ao servidor %s", e.getMessage());
		}
	}	
}
