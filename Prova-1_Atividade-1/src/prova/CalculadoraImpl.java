package prova;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraImpl extends UnicastRemoteObject implements Calculadora {
	
	protected CalculadoraImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public long add(long a, long b) throws RemoteException {
		return a + b;
	}

	@Override
	public long sub(long a, long b) throws RemoteException {
		return a - b;
	}

	@Override
	public long mult(long a, long b) throws RemoteException {
		return 0;
	}

	@Override
	public long div(long a, long b) throws RemoteException {
		if(b == 0) {
			throw new RemoteException("Não é possível dividir por zero");
		} else {
			return a / b;
		}
	}

}
