package GerenciadorServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GerenciadorServer {
	public static void main(String[] args) {
		try {
			System.out.println("GerenciadorServer: Iniciando...");
			ServerSocket server = new ServerSocket(1235);
			System.out.println("GerenciadorServer: online...");
			while (true) {
				System.out.println("GerenciadorServer: Aguardando Conexão...");
				Socket cliente = server.accept();
				System.out.println("GerenciadorServer: Conectado com cliente!");
				TrataProtocolo protocolo = new TrataProtocolo(cliente);
				protocolo.start();
			}
		} catch (UnknownHostException ex) {
			System.out.println("GerenciadorServer: Host desconhecido");
		} catch (IOException ex) {
			System.out.println("GerenciadorServer: Erro na conexao: " + ex.getMessage());
		}
	}
}
