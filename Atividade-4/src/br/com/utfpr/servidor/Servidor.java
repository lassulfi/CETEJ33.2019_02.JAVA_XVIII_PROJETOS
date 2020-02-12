package br.com.utfpr.servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import br.com.utfpr.servico.ContaPiada;

public class Servidor {
			
	private static final int PORTA_SERVIDOR = 40000; 

	public static void main(String[] args) {
		new Servidor().iniciar();
	}
	
	/**
	 * Inicia o servidor
	 */
	private void iniciar() {
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
				
				this.validaSolicitacao(solicitacao, msg, socket, packet, contaPiada);
				
			}
		} catch (Exception e) {
			System.out.printf("\nErro ao receber o datagram packet: %s", e.getMessage());
			e.printStackTrace();
		} finally {
			contaPiada.fechaReader();
		}
	}
	
	/**
	 * Valida a solicitação recebida do cliente
	 * 
	 * @param solicitacao String contendo a solictação do cliente
	 * @param msg array de bytes que irá conter a mensagem
	 * @param socket {@link DatagramSocket}
	 * @param packet {@link DatagramPacket}
	 * @param contaPiada {@link ContaPiada} instancia do servico conta piada
	 * @throws Exception 
	 */
	private void validaSolicitacao(String solicitacao, byte[] msg, 
			DatagramSocket socket, 
			DatagramPacket packet, 
			ContaPiada contaPiada) throws Exception {
		
		if(solicitacao.contains("Mande uma piada")) {
			this.enviaPiada(socket, msg, packet, contaPiada);
		}
	}
	
	/**
	 * Envia uma piada para o cliente
	 * 
	 * @param socket {@link DatagramSocket}
	 * @param msg array de bytes que irá conter a mensagem
	 * @param packet {@link DatagramPacket}
	 * @param contaPiada {@link ContaPiada} instancia do servico conta piada
	 * @throws Exception
	 */
	private void enviaPiada(DatagramSocket socket, 
			byte[] msg, 
			DatagramPacket packet, 
			ContaPiada contaPiada) throws Exception {
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
