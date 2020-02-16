package br.com.utfpr.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CpfServiceImpl extends UnicastRemoteObject implements CpfService {
	private static final long serialVersionUID = 1L;
	
	private Integer qtdeCpfsValidos;

	public CpfServiceImpl() throws RemoteException {
		super();
		this.qtdeCpfsValidos = 0;
	}

	@Override
	public String validaCpf(String cpf) throws RemoteException {
		String message = "CPF válido";
		
		if(cpf == null) {
			message = "CPF inválido";
			return message;
		} else {	
			cpf = this.removerCaracteres(cpf);
			
			if(verificarSeTamanhoInvalido(cpf)) {
				message = "CPF inválido";
				return message;
			}
			
			if(verificaSeDigitosIguais(cpf)) {
				message = "CPF inválido";
				return message;
			}
			
			String cpfGerado = cpf.substring(0, 9);
			cpfGerado = cpfGerado.concat(this.calculoCpf(cpfGerado));
			cpfGerado = cpfGerado.concat(this.calculoCpf(cpfGerado));
			if(!cpfGerado.equals(cpf)) {
				message = "CPF inválido";
				return message;
			}
		}
		
		if(message.equals("CPF válido")) {
			this.incrementarQuantidadeCpfsValidos();
		}
		
		return message;
	}	
	
	@Override
	public Integer getQuantidadeCpfsValidos() throws RemoteException {
		return this.qtdeCpfsValidos;
	}

	private String removerCaracteres(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		
		return cpf;
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
	
	private void incrementarQuantidadeCpfsValidos() {
		this.qtdeCpfsValidos++;
	}

}
