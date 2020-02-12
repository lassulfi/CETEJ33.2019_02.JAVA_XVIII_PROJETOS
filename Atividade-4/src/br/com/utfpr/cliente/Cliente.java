package br.com.utfpr.cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
	
	private static final String HOST = "localhost";
	
	private static final int PORTA = 40000;

	public static void main(String[] args) {
		new Cliente().iniciar();
	}
	
	/**
	 * Inicia o cliente
	 */
	private void iniciar() {
		
		try {
			DatagramSocket socket = new DatagramSocket();
			
			// Preparando o pacote para envio
			byte[] msg = new byte[128];
			
			InetAddress endereco = InetAddress.getByName(HOST);
			
			String piada = "";
			
			while(!piada.contains("Sem mais piadas para enviar")) {
				
				DatagramPacket packet = this.enviarSolicitacao(msg, socket, endereco);
				
				//Preparando o pacote para recebimento
				piada = receberMensagem(msg, packet, socket, piada);
				
				if(!piada.contains("Sem mais piadas para enviar")) {
					System.out.printf("A piada é: %s\n", piada);					
				} else {
					System.out.printf("%s\n", piada);					
				}
				
				Thread.sleep(1000); // Faz uma pequena pausa para o envio da proxima solicitacao
			}
			
		} catch (Exception e) {
			System.out.printf("Um erro ocorreu: %s", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Envia uma solicitacao para o servidor
	 * @param msg array de bytes que irá conter a mensagem
	 * @param socket {@link DatagramSocket}
	 * @param endereco {@link InetAddress}
	 * @return {@link DatagramPacket}
	 * @throws Exception
	 */
	private DatagramPacket enviarSolicitacao(byte[] msg, 
			DatagramSocket socket, 
			InetAddress endereco) throws Exception {
		
		String solicitacao = "Mande uma piada";
		msg = solicitacao.getBytes();	
		
		DatagramPacket packet = new DatagramPacket(msg, msg.length, endereco, PORTA);
		
		System.out.printf("\nEnviando solicitacao '%s' para %s:%d", solicitacao, HOST, PORTA);
		socket.send(packet);
		
		return packet;
	}
	
	/**
	 * Recebe uma mensagem do servidor
	 * 
	 * @param msg array de bytes que irá conter a mensagem
	 * @param packet {@link DatagramPacket}
	 * @param socket {@link DatagramSocket}
	 * @param piada String contendo a piada
	 * @return String contendo a piada ou mensagem de que não há mais piadas
	 * @throws Exception
	 */
	private String receberMensagem(byte[] msg, DatagramPacket packet, DatagramSocket socket, String piada) throws Exception {
		msg = new byte[128];
		
		packet = new DatagramPacket(msg, msg.length);
		socket.receive(packet);
		System.out.println("\nPacote recebido");
		
		piada = new String(packet.getData());
		
		return piada;
	}
}
