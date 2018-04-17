package ServerAluno;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import GerenciadorServer.ConverteEmString;

public class TrataAluno extends Thread implements Serializable {
	// variáveis do server
	private Socket socket;
	private Scanner entrada;
	private PrintWriter saida;

	// preparando entrada e saída do server
	public TrataAluno(Socket socket) throws IOException {
		this.socket = socket;
		this.entrada = new Scanner(this.socket.getInputStream());
		this.saida = new PrintWriter(this.socket.getOutputStream());
	}

	public void run() {
		ObjectInputStream inputStream = null;
		ObjectOutputStream streamSaida = null;
		ArrayList<Aluno> alunos = null;
		Alunos tabelaAluno = null;
		Gson objJson = new GsonBuilder().setPrettyPrinting().create();
		Gson gson = new Gson();
		Scanner pegaTexto = null;
		String caminhoBanco = "", textoConfig = "";
		ConfigStudent config = null;

		int id;
		String nome;
		String protocolo;
		String textoRetorna = null;
		File arquivo = null;// Criando uma variavel para trabalhar com arquivos

		protocolo = entrada.nextLine();
		System.out.println("Server Turma: Protocolo \"" + protocolo + "\" Recebido.");
		String dados[] = protocolo.split("/");// Dividindo o protocolo em várias strings

		arquivo = new File("tmp/ConfigStudent.json");// fazendo um objeto arquivo
		if (!arquivo.exists()) {// testando se o arquivo já existe, caso não exista cria um novo
			System.out.println("Server Aluno: Arquivo de configuração inexistente");
		} else {
			System.out.println("Server Aluno: Abrindo arquivo de configuração...");
			try {
				pegaTexto = new Scanner(arquivo);
				textoConfig = new ConverteEmString().converteJson(pegaTexto);
				System.out.println("Server Aluno: *Arquvio ConfigStudent.json*\n" + textoConfig);
				
				config = gson.fromJson(textoConfig, ConfigStudent.class);
				caminhoBanco = config.getDatafile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
