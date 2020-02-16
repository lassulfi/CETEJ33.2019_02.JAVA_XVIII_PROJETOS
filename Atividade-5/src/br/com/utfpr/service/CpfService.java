package br.com.utfpr.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CpfService extends Remote {

	public String validaCpf(String cpf) throws RemoteException;
	
	public Integer getQuantidadeCpfsValidos() throws RemoteException;
}
