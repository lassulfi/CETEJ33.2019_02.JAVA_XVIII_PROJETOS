package prova;

import java.rmi.Naming;

public class CalculadoraServidor {

	public static void main(String[] args) {
		try {
			
			Calculadora calc = new CalculadoraImpl();
			
			Naming.rebind("rmi://localhost:1099/calc", calc);
			
			System.out.println("Servidor pronto.");
			
		} catch (Exception e) {
			a
		}
	}
}
