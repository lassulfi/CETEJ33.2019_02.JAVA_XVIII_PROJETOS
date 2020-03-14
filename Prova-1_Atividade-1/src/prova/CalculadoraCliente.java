package prova;

import java.rmi.Naming;

public class CalculadoraCliente {

	public static void main(String[] args) {
		try {
			Calculadora calc = (Calculadora) Naming.lookup("rmi://localhost:1099/calc");
			
			System.out.println("Operação de soma: 2 + 2 = " + calc.add(2L, 2L));
			
			System.out.println("Operação de subtracao: 3 - 2 = " + calc.sub(3L, 2L));
			
			System.out.println("Operação de multiplicacao: 2 x 2 = " + calc.mult(2L, 2L));
			
			System.out.println("Operação de divisao: 10 / 2 = " + calc.add(10L, 2L));
			
		} catch (Exception e) {
			System.out.printf("Erro RMI: %s", e.getMessage());
		}
	}
}
