package br.com.utfpr.servidor;

import java.rmi.Naming;

import br.com.utfpr.service.CpfService;
import br.com.utfpr.service.CpfServiceImpl;

public class CpfServidor {

	public static void main(String[] args) {
			
		try {
			CpfService cpfService = new CpfServiceImpl();
			Naming.rebind("rmi://localhost:1099/CpfService", cpfService);
			
			System.out.println("Servidor pronto.");
			
		} catch (Exception e) {
			System.out.printf("Erro RMI: %s", e.getMessage());
		}
	}
}
