package br.com.utfpr.cliente;

import java.rmi.Naming;
import java.util.Scanner;

import br.com.utfpr.service.CpfService;

public class CpfCliente {

	public static void main(String[] args) {
		
		int opcao = -1;
		
		try {
			CpfService cpfService = (CpfService) Naming.lookup("rmi://localhost:1099/CpfService");
			
			Scanner in = new Scanner(System.in);
			System.out.println("Selecione uma opção: ");
			System.out.println("1 -> Validar CPF");
			System.out.println("2 -> Informar a quantidade de CPF's validados (Não implementado ainda)");
			System.out.print("Opção: ");
			do {
				try {
					opcao = in.nextInt();
					in.nextLine();
				} catch (Exception e) {
					System.out.println("A opção informada deve ser um número.");
				}
				
				switch (opcao) {
				case 1:
					System.out.println("Informe um CPF para ser validado (incluir ponto(.) e traço separador de digito (-) ex.: XXX.XXX.XXX-XX): ");
					System.out.print("CPF: ");
					String cpf = in.nextLine();
					System.out.println(cpfService.validaCpf(cpf));
					break;
				case 2:
					System.out.printf("Número de CPF's válidos: %s\n", cpfService.getQuantidadeCpfsValidos());
					break;
				default:
					break;
				}
				
				
				if (opcao < 0 || opcao > 2) {
					System.out.println("Opção inválida. Informe selecione uma opção válida");
					break;
				}
				
			} while (opcao < 0 || opcao > 2);
			
		} catch (Exception e) {
			System.out.printf("Erro RMI: %s", e.getMessage());
		}
	}
}
