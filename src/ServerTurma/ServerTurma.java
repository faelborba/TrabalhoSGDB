package ServerTurma;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.google.gson.Gson;

import GerenciadorServer.ConverteEmString;

public class ServerTurma {
	public static void main(String[] args) {
		try {
			String textoConfig = "";
			File arquivo = null;
			Scanner pegaTexto = null;
			ConfigClass config = null;
			Gson gson = new Gson();
			int porta = 0;

			System.out.println("Server Turma: Iniciando Server de Banco Turma...");
			arquivo = new File("tmp/ConfigClass.json");// fazendo um objeto arquivo
			if (!arquivo.exists()) {// testando se o arquivo já existe, caso não exista cria um novo
				System.out.println("Server Turma: Arquivo de configuração inexistente");
			}
			System.out.println("Server Turma: Abrindo arquivo de configuração.");
			pegaTexto = new Scanner(arquivo); // pega texto do arquivo
			textoConfig = new ConverteEmString().converteJson(pegaTexto);
			System.out.println("Server Turma: *Arquivo ConfigClass.json*\n" + textoConfig);

			config = gson.fromJson(textoConfig, ConfigClass.class);// converte json em object
			porta = config.getPort();

			ServerSocket server = new ServerSocket(porta);// abre uma conexão na porta definida no arquivo de
															// configuração.
			System.out.println("Server Turma: Server de Banco Turma online...");
			while (true) {
				System.out.println("Server Turma: Aguardando Conexão...");
				Socket cliente = server.accept();
				System.out.println("Server Turma: Conectado!");
				TrataTurma aluno = new TrataTurma(cliente);
				aluno.start();
			}
		} catch (UnknownHostException ex) {
			System.out.println("Server Turma: Host desconhecido");
		} catch (IOException ex) {
			System.out.println("Server Turma: Erro na conexao: " + ex.getMessage());
		}
	}
}
