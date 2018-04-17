package ServerAluno;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.google.gson.Gson;

import GerenciadorServer.ConverteEmString;

public class ServerAluno {
	public static void main(String[] args) {
		try {
			String textoConfig = "";
			File arquivo = null;
			Scanner pegaTexto = null;
			ConfigStudent config = null;
			Gson gson = new Gson();
			int porta = 0;

			System.out.println("Server Aluno: Iniciando Server de Banco Turma...");
			arquivo = new File("tmp/ConfigStudent.json");// fazendo um objeto arquivo
			if (!arquivo.exists()) {// testando se o arquivo já existe, caso não exista cria um novo
				System.out.println("Server Aluno: Arquivo de configuração inexistente");
			}
			System.out.println("Server Aluno: Abrindo arquivo de configuração.");
			pegaTexto = new Scanner(arquivo); // pega texto do arquivo
			textoConfig = new ConverteEmString().converteJson(pegaTexto);
			System.out.println("Server Aluno: *Arquivo ConfigStudent.json*\n" + textoConfig);

			config = gson.fromJson(textoConfig, ConfigStudent.class);// converte json em object
			porta = config.getPort();

			ServerSocket server = new ServerSocket(porta);// abre uma conexão na porta definida no arquivo de
															// configuração.
			System.out.println("Server Aluno: Server de Banco Aluno online...");
			while (true) {
				System.out.println("Server Aluno: Aguardando Conexão...");
				Socket cliente = server.accept();
				System.out.println("Server Aluno: Conectado!");
				TrataAluno aluno = new TrataAluno(cliente);
				aluno.start();
			}
		} catch (UnknownHostException ex) {
			System.out.println("Server Aluno: Host desconhecido");
		} catch (IOException ex) {
			System.out.println("Server Aluno: Erro na conexao: " + ex.getMessage());
		}
	}
}
