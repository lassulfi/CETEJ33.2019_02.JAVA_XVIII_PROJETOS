package br.com.utfpr.servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import br.com.utfpr.servico.ContaPiada;

public class Servidor {
			
	private static final int PORTA_SERVIDOR = 40000; 

	public static void main(String[] args) {
		
		ContaPiada contaPiada = null;
		
		try {
			DatagramSocket socket = new DatagramSocket(PORTA_SERVIDOR);
			System.out.printf("Servidor UDP iniciado na porta %d", PORTA_SERVIDOR);
			
			contaPiada = new ContaPiada();
			contaPiada.abrirArquivo();
			
			while(true) {
				byte[] msg = new byte[128];
				
				DatagramPacket packet = new DatagramPacket(msg, msg.length);
				
				socket.receive(packet);
				
				String solicitacao = new String(packet.getData());
				
				if(solicitacao.contains("Mande uma piada")) {
					InetAddress endereco = packet.getAddress();
					int porta = packet.getPort();
					
					String piada = contaPiada.lerPiada();
					
					msg = new byte[128];
					
					msg = piada.getBytes();
					
					packet = new DatagramPacket(msg, msg.length, endereco, porta);
					
					socket.send(packet);
					System.out.printf("\nPacote enviado para %s:%d", endereco.getHostAddress(), porta);
				}
			}
			
		} catch (Exception e) {
			System.out.printf("\nErro ao receber o datagram packet: %s", e.getMessage());
			e.printStackTrace();
		} finally {
			contaPiada.fechaReader();
		}
		
		
		
	}
}
