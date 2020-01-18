package br.com.utfpr.servidor.services;

public class CpfService {

	private String cpf;
	
	public CpfService(String cpf) {
		this.cpf = cpf;
	}
	
	public boolean validarCpf() {
		if(this.cpf == null) {
			return false;
		} else {	
			this.removerCaracteres();
			
			if(verificarSeTamanhoInvalido(this.cpf)) {
				return false;
			}
			
			if(verificaSeDigitosIguais(this.cpf)) {
				return false;
			}
			
			String cpfGerado = this.cpf.substring(0, 9);
			cpfGerado = cpfGerado.concat(calculoCpf(cpfGerado));
			cpfGerado = cpfGerado.concat(calculoCpf(cpfGerado));
			if(!cpfGerado.equals(this.cpf)) {
				return false;
			}
		}
		
		return true;
	}
	
	private void removerCaracteres() {
		this.cpf = this.cpf.replace(".", "");
		this.cpf = this.cpf.replace("-", "");
	}
	
	private boolean verificarSeTamanhoInvalido(String cpf) {
		if(cpf.length() != 11) {
			return true;
		}
		return false;
	}
	
	private String calculoCpf(String cpf) {
		int digitoGerado = 0;
		int multiplicador = cpf.length() + 1;
		char[] chars = cpf.toCharArray();
		for(int i = 0; i < cpf.length(); i++) {
			digitoGerado += (chars[i] - 48) * multiplicador--;
		}
		if(digitoGerado % 11 < 2) {
			digitoGerado = 0;
		} else {
			digitoGerado = 11 - digitoGerado % 11;
		}
		return String.valueOf(digitoGerado);
	}
	
	private boolean verificaSeDigitosIguais(String cpf) {	
		char primeiroDigito = '0';
		char[] chars = cpf.toCharArray();
		for(char c : chars) {
			if(c != primeiroDigito) {
				return false;
			}
		}
		
		return true;
	}
	
}
