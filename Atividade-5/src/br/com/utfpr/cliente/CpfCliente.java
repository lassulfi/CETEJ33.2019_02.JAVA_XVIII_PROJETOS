package br.com.utfpr.cliente;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

import br.com.utfpr.service.CpfService;

public class CpfCliente {

	public static void main(String[] args) {
		
		Scanner in = null;
		
		int opcao = -1;
		
		try {
			CpfService cpfService = (CpfService) Naming.lookup("rmi://localhost:1099/CpfService");
			
			in = new Scanner(System.in);
			do {
				exibirMenuPrincipal();
				System.out.print("\nOpção: ");
				try {
					opcao = in.nextInt();
					in.nextLine();
				} catch (Exception e) {
					System.out.println("A opção informada deve ser um número.");
				}
				
				tratarOpcao(in, opcao, cpfService);
				if (opcao < 0 || opcao > 3) {
					System.out.println("Opção inválida. Informe selecione uma opção válida");
				}
				
			} while (opcao != 3);
			
		} catch (Exception e) {
			System.out.printf("Erro RMI: %s", e.getMessage());
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	private static void tratarOpcao(Scanner in, int opcao, CpfService cpfService) throws RemoteException, InterruptedException {
		switch (opcao) {
		case 1:
			System.out.println("\nInforme um CPF para ser validado (incluir ponto(.) e traço separador de digito (-) ex.: XXX.XXX.XXX-XX): ");
			System.out.print("CPF: ");
			String cpf = in.nextLine();
			System.out.println("\n" + cpfService.validaCpf(cpf));
			break;
		case 2:
			System.out.printf("\nNúmero de CPF's válidos: %s\n", cpfService.getQuantidadeCpfsValidos());
			break;
		case 3:
			System.out.println("\nFechando o cliente...");
			break;
		default:
			break;
		}
		
		Thread.sleep(1000);
	}

	private static void exibirMenuPrincipal() {
		System.out.println("\nSelecione uma opção: ");
		System.out.println("1 -> Validar CPF");
		System.out.println("2 -> Informar a quantidade de CPF's validados (Não implementado ainda)");
		System.out.println("3 -> Sair");
	}
}
