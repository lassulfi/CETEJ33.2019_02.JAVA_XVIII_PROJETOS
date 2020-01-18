package br.com.utfpr.cliente;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

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
			
			System.out.print("\nInforme um CPF "
					+ "(incluir ponto(.) e traço separador de digito (-) ex.: XXX.XXX.XXX-XX): ");
			String cpf = reader.readLine();
			
			output.writeUTF(cpf);
			
			String result = input.readUTF();
			
			System.out.println(result);
			
			socket.close();
		} catch (Exception e) {
			System.out.printf("Uma exceção ocorreu na conexão com o servidor: %s", e.getMessage());
		}
	}
}
