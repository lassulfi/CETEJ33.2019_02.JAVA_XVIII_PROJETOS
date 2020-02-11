package br.com.utfpr.servico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ContaPiada {

	private static final String FILENAME = "piadas.txt";
	
	private BufferedReader reader;
	
	public ContaPiada() {
		
	}
	
	public void abrirArquivo() {
		try {
			reader = new BufferedReader(new FileReader(FILENAME));
		} catch (FileNotFoundException e) {
			System.out.printf("\nErro em ContaPiada - arquivo não encontrado: %s", e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String lerPiada() throws IOException {
		String mensagemEndOfFile = "Sem mais piadas para enviar";
		
		String piada = reader.readLine();
		
		return piada != null ? piada : mensagemEndOfFile; 
	}
	
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
