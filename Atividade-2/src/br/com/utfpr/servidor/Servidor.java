package br.com.utfpr.servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

	private static List<Socket> clientes;
		
	private static ServerSocket server;
	
	private static final int PORT = 50000;
	
	public class ClienteHandler implements Runnable {
		
		private Socket socket;
		
		private DataInputStream input;
		
		public ClienteHandler(Socket clienteSocket) {
			
			try {
				socket = clienteSocket;
				input = new DataInputStream(socket.getInputStream());	
			} catch (IOException e) {
				System.out.printf("\nUm erro ocorreu ao ler dados do cliente: %s", e.getMessage());
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			
			String message;
			try {
				while((message = input.readUTF()) != null) {
					System.out.printf("\nMensagem recebida: %s", message);
				}
			} catch (IOException e) {
				System.out.printf("\nUm erro ocorreu ao ler mensagem do cliente: %s", e.getMessage());
				e.printStackTrace();
			}	
		} //Fecha o run
	} //Fecha a classe interna
	
	public static void main(String[] args) {
		new Servidor().inicar();
	}
	
	public void inicar() {
		clientes = new ArrayList<>();
		try {
			System.out.printf("Iniciando servidor na porta %s", PORT);
			server = new ServerSocket(PORT);
			while(true) {
				Socket cliente = server.accept();
				
				clientes.add(cliente);
				
				Thread t = new Thread(new ClienteHandler(cliente));
				t.start();
				System.out.printf("\nConexão aberta com %s", cliente.getInetAddress());
			}
		} catch (Exception e) {
			System.out.printf("\nErro ao iniciar o servidor: %s", e.getMessage());
			e.printStackTrace();
		}
	}
}//Fecha classe Servidor
