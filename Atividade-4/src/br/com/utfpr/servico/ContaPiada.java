package br.com.utfpr.servico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * <h3>Classe de serviço para leitura de um arquivo contendo piadas</h3>
 * 
 * <p>As piadas estao disponiveis no arquivo piada.txt.
 * O arquivo é estruturado de modo que cada linha do arquivo contém uma piada.</p>
 * 
 * @author lassulfi
 *
 */
public class ContaPiada {

	private static final String FILENAME = "piadas.txt";
	
	private BufferedReader reader;
	
	public ContaPiada() {
		
	}
	
	/**
	 * Abre o arquivo para leitura
	 */
	public void abrirArquivo() {
		try {
			reader = new BufferedReader(new FileReader(FILENAME));
		} catch (FileNotFoundException e) {
			System.out.printf("\nErro em ContaPiada - arquivo não encontrado: %s", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Le uma piada do arquivo em uma linha do arquivo.
	 * 
	 * @return String contendo uma piada
	 * @throws IOException
	 */
	public String lerPiada() throws IOException {
		String mensagemEndOfFile = "Sem mais piadas para enviar";
		
		String piada = reader.readLine();
		
		return piada != null ? piada : mensagemEndOfFile; 
	}
	
	/**
	 * Fecha a instancia {@link BufferedReader}
	 */
	public void fechaReader() {
		if(reader != null)
			try {
				reader.close();
			} catch (IOException e) {
				System.out.printf("\nUm erro ocorreu no serviço ContaPiada: %s", e.getMessage());
				e.printStackTrace();
			}
	}
}
