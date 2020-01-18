package br.com.utfpr.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.utfpr.servidor.services.CpfService;

public class Servidor {
	
	private static Socket socket;
	private static ServerSocket server;
	
	private static DataInputStream input;
	private static DataOutputStream output;
	
	private static final int PORT = 50000;
	
	public static void main(String[] args) {
		try {
			server = new ServerSocket(PORT);
			System.out.printf("Servidor iniciado na porta %s. Aguardando conexão...", PORT);
			while (true) {
				socket = server.accept();
				System.out.printf("\nConexão recebida de %s", socket.getInetAddress());						
				
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				
				String cpf = input.readUTF();
				
				CpfService service = new CpfService(cpf);
				
				String result;
				if (service.validarCpf()) {
					result = "TRUE (valido)";
				} else {
					result = "FALSE (invalido)";
				}
				
				output.writeUTF("Resultado da validação do CPF: " + result);
				
				socket.close();
				System.out.printf("\nConexão fechada com %s", socket.getInetAddress());
			}
		} catch (Exception e) {
			System.out.printf("\nUma exceção ocorreu no servidor: %s", e.getMessage());
		} finally {
			try {
				System.out.printf("\nFechando servidor na porta %s", PORT);
				server.close();
			} catch (IOException e) {
				System.out.printf("\nUma exceção ocorreu ao fechar o servidor: %s", e.getMessage());
			}
		}
	}
}
