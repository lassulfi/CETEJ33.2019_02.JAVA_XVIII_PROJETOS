package br.com.utfpr.cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
	
	private static final String HOST = "localhost";
	
	private static final int PORTA = 40000;

	public static void main(String[] args) {
		try {
			DatagramSocket socket = new DatagramSocket();
			
			// Preparando o pacote para envio
			byte[] msg = new byte[128];
			
			InetAddress endereco = InetAddress.getByName(HOST);
			
			for(int i = 0; i < 11; i++) {
				String solicitacao = "Mande uma piada";
				msg = solicitacao.getBytes();	
				
				DatagramPacket packet = new DatagramPacket(msg, msg.length, endereco, PORTA);
				
				System.out.printf("\nEnviando solicitacao '%s' para %s:%d", solicitacao, HOST, PORTA);
				socket.send(packet);
				
				//Preparando o pacote para recebimento
				msg = new byte[128];
				
				packet = new DatagramPacket(msg, msg.length);
				socket.receive(packet);
				System.out.println("\nPacote recebido");
				
				String piada = new String(packet.getData());
				
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
}
