package br.com.utfpr.servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
		
	private static ServerSocket server;
	private static DataInputStream input;
	
	private static final int PORT = 50000;
	
	public class ClienteHandler implements Runnable {
		
		private Socket socket;
		private DataInputStream input;	
		
		public ClienteHandler(Socket clienteSocket, DataInputStream clienteInput) {
			
			this.socket = clienteSocket;
			this.input = clienteInput;
		}

		@Override
		public void run() {
			
			String inputMessage;
			
			while(true) {
				try {
					inputMessage = this.input.readUTF();
					if(inputMessage.equals("sair")) {
						System.out.printf("\nCliente %s deseja se desconectar do servidor...", 
								socket.getInetAddress());
						System.out.println("Fechando a conexão...");
						this.socket.close();
						System.out.println("Conexão fechada");
						break;						
					} else {
						System.out.printf("\n%s disse: %s", this.socket.getInetAddress(), inputMessage);
					}
				} catch (IOException e) {
					System.out.printf("\nUm erro ocorreu ao ler mensagem do cliente: %s", e.getMessage());
				}
			}
			
			try {
				this.socket.close();
				this.input.close();
			} catch (IOException e) {
				System.out.printf("Erro ao fechar recursos: %s", e.getMessage());
			}
		} //Fecha o run
	} //Fecha a classe interna
	
	public static void main(String[] args) {
		new Servidor().inicar();
	}
	
	public void inicar() {
		try {
			System.out.printf("Iniciando servidor na porta %s", PORT);
			server = new ServerSocket(PORT);
			while(true) {
				Socket cliente = server.accept();
				System.out.printf("\nNovo cliente conectado: %s", cliente.getInetAddress());
				
				input = new DataInputStream(cliente.getInputStream());
				
				Thread t = new Thread(new ClienteHandler(cliente, input));
				t.start();
				System.out.printf("\nConexão aberta com %s", cliente.getInetAddress());
			}
		} catch (Exception e) {
			System.out.printf("\nErro ao iniciar o servidor: %s", e.getMessage());
		}
	}
}//Fecha classe Servidor
