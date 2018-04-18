package ServerAluno;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import GerenciadorServer.CodigoRetorna;
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
		Aluno aluno = null;
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

			arquivo = new File(caminhoBanco);
			if (!arquivo.exists()) {
				System.out.println("Server Aluno: Arquivo insexistente, Criando DB");
				try {
					streamSaida = new ObjectOutputStream(new FileOutputStream(arquivo));
					alunos = new ArrayList<>();
					tabelaAluno = new Alunos(alunos);
					streamSaida.writeObject(tabelaAluno);
					streamSaida.close();
					System.out.println("Server Aluno: *Arquivo criado com sucesso");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				int i = 0;
				System.out.println("Server Aluno: Abrindo arquivo Aluno...");
				inputStream = new ObjectInputStream(new FileInputStream(arquivo));
				System.out.println("Server Aluno: Buscando tabela de aluno");
				tabelaAluno = (Alunos) inputStream.readObject();
				System.out.println("Server Aluno: Tabela Aluno encontrada");
				System.out.print("Server Aluno: Vou executar o comando:");

				// Arquivo criado aagora vamos trabalhar com ele
				if (dados[1].equals("incluiTurma")) {
					System.out.println(dados[1]);
					CodigoRetorna codigoRetorna = new CodigoRetorna();
					for (i = 0; i < tabelaAluno.getAlunos().size(); i++) {// teste ID já cadastrado.
						if (Integer.parseInt(dados[2]) == tabelaAluno.getAlunos().get(i).getIdAluno()) {
							System.out.println("Server Aluno: ID Aluno Já Cadastrado.");
							codigoRetorna.setCodRetorno(1);
							codigoRetorna.setDescricaoRetorno("Registro Já cadastrado");
							textoRetorna = objJson.toJson(codigoRetorna);
							System.out.println(textoRetorna);// mostra o que retorna
							this.saida.println(textoRetorna);
							this.saida.flush();
							this.saida.close();
							break;
						}
					}
					if (i == tabelaAluno.getAlunos().size()) {
						aluno = new Aluno();
						aluno.setIdAluno(Integer.parseInt(dados[2]));
						aluno.setNomeAluno(dados[3]);
						tabelaAluno.getAlunos().add(aluno);// add aluno em arraylist

						System.out.println("Server Aluno: Gravando: " + dados[1]);
						streamSaida = new ObjectOutputStream(new FileOutputStream(arquivo));
						streamSaida.writeObject(tabelaAluno);
						streamSaida.close();
						
						codigoRetorna.setCodRetorno(0);
						codigoRetorna.setDescricaoRetorno("Requisição OK");
						textoRetorna = objJson.toJson(codigoRetorna);
						System.out.println(textoRetorna);// mostrando o conteudo json

						this.saida.println(textoRetorna);// devolvendo para o cliente em json
						this.saida.flush();
						this.saida.close();
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

	}
}
