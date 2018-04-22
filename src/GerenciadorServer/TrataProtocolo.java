package GerenciadorServer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ServerTurma.Turmas;
import ServerTurma.Turma;

public class TrataProtocolo extends Thread implements Serializable {
	// variáveis do server
	private Socket socket;
	private Scanner entrada;
	private PrintWriter saida;

	// preparando entrada e saída do server
	public TrataProtocolo(Socket socket) throws IOException {
		this.socket = socket;
		this.entrada = new Scanner(this.socket.getInputStream());
		this.saida = new PrintWriter(this.socket.getOutputStream());
	}

	@Override
	public void run() {
		String protocolo = null;
		String ipTurma = null, ipAluno = null;
		int portaTurma = 0, portaAluno = 0;
		String textoJson = "", textoConfig = "", textoRetorna = "";
		File arquivo = null;
		Scanner pegaTexto = null;
		ConfigGerenciador config = null;
		// ConverteEmString converte = new ConverteEmString();
		// Gson gson = new Gson();
		Gson objJson = new GsonBuilder().setPrettyPrinting().create();

		protocolo = entrada.nextLine();
		System.out.println("GerenciadorServer: Protocolo \"" + protocolo + "\" Recebido.");

		String dados[] = protocolo.split("/");// Dividindo o protocolo em várias strings
		System.out.println("GerenciadorServer: Vou executar o comando " + dados[1]);

		arquivo = new File("tmp/ConfigGerenciador.json");// fazendo um objeto arquivo
		if (!arquivo.exists()) {// testando se o arquivo de configuração existe
			System.out.println("GerenciadorServer: Arquivo de configuração inexistente");
			CodigoRetorna codigoRetorna = new CodigoRetorna();
			codigoRetorna.setCodRetorno(3);
			codigoRetorna.setDescricaoRetorno("Servidor Indisponível");
			textoRetorna = objJson.toJson(codigoRetorna);
			System.out.println(textoRetorna);// mostra o que retorna
			this.saida.println(textoRetorna);
			this.saida.flush();
			this.saida.close();
		} else {
			try {
				System.out.println("GerenciadorServer: Abrindo arquivo de configuração...");
				pegaTexto = new Scanner(arquivo); // pega texto do arquivo
				textoConfig = new ConverteEmString().converteJson(pegaTexto);
				System.out.println("GerenciadorServer: *Arquivo ConfigGerenciador.json*\n" + textoConfig);

				config = objJson.fromJson(textoConfig, ConfigGerenciador.class);// converte json em object
				portaTurma = config.getClassServerPort();
				ipTurma = config.getClassServerHost();
				portaAluno = config.getStudentServerPort();
				ipAluno = config.getStudentServerHost();

				Socket socketTurma = new Socket(ipTurma, portaTurma);// conecta no serverturma
				Scanner entradaTurma = new Scanner(new InputStreamReader(socketTurma.getInputStream()));
				PrintWriter saidaTurma = new PrintWriter(socketTurma.getOutputStream());

				Socket socketAluno = new Socket(ipAluno, portaAluno);// conecta no serveraluno
				Scanner entradaAluno = new Scanner(new InputStreamReader(socketAluno.getInputStream()));
				PrintWriter saidaAluno = new PrintWriter(socketAluno.getOutputStream());
				
				if (dados[1].equals("incluiTurma") || dados[1].equals("apagaTurma")) {
					System.out.println("GerenciadorServer: Enviando protocolo " + protocolo);
					saidaTurma.println(protocolo);// envia protocolo ao server
					saidaTurma.flush();

					textoJson = new ConverteEmString().converteJson(entradaTurma);// converte entrada e emstring
					System.out.println(textoJson);// convertemos string em objeto
					CodigoRetorna codigoRetorna = objJson.fromJson(textoJson, CodigoRetorna.class);
					textoRetorna = objJson.toJson(codigoRetorna);
					System.out.println(textoRetorna);// mostrando o conteudo json
					this.saida.println(textoRetorna);// devolvendo para o cliente em json
					this.saida.flush();
					this.saida.close();
					socketTurma.close();
				}
				if (dados[1].equals("turma")) {
					System.out.println("GerenciadorServer: Enviando protocolo " + protocolo);
					saidaTurma.println(protocolo);// envia protocolo ao server
					saidaTurma.flush();

					textoJson = new ConverteEmString().converteJson(entradaTurma);// converte entrada e em string
					System.out.println(textoJson);// teste do que veio de turma
					Turma turma = objJson.fromJson(textoJson, Turma.class);// Converte String em Objeto turma

					/*
					 * TurmaAluno turmaAluno = new TurmaAluno();
					 * turmaAluno.setIdTurma(turma.getIdTurma());
					 * turmaAluno.setNomeTurma(turma.getNomeTurma());
					 * 
					 * Alunos aluos =
					 */

					textoRetorna = objJson.toJson(turma);
					System.out.println(textoRetorna);// mostrando o conteudo json
					this.saida.println(textoRetorna);// devolvendo para o cliente em json
					this.saida.flush();
					this.saida.close();
					socketTurma.close();
				}
				if (dados[1].equals("turmas")) {
					System.out.println("GerenciadorServer: Enviando protocolo " + protocolo);
					saidaTurma.println(protocolo);// envia protocolo ao server
					saidaTurma.flush();

					textoJson = new ConverteEmString().converteJson(entradaTurma);// converte entrada e em string
					System.out.println(textoJson);// teste
					Turmas turmas = objJson.fromJson(textoJson, Turmas.class);// Converte String em Objeto turma
					textoRetorna = objJson.toJson(turmas);
					System.out.println(textoRetorna);// mostrando o conteudo json
					this.saida.println(textoRetorna);// devolvendo para o cliente em json
					this.saida.flush();
					this.saida.close();
					socketTurma.close();
				}

				if (dados[1].equals("incluiAluno") || dados[1].equals("apagaAluno")) {
					System.out.println("GerenciadorServer: Enviando protocolo " + protocolo);
					saidaAluno.println(protocolo);// envia protocolo ao server
					saidaAluno.flush();

					textoJson = new ConverteEmString().converteJson(entradaAluno);// converte entrada e emstring
					System.out.println(textoJson);
					CodigoRetorna codigoRetorna = objJson.fromJson(textoJson, CodigoRetorna.class);
					textoRetorna = objJson.toJson(codigoRetorna);
					System.out.println(textoRetorna);// mostrando o conteudo json
					this.saida.println(textoRetorna);// devolvendo para o cliente em json
					this.saida.flush();
					this.saida.close();
					socketAluno.close();
				}

				this.entrada.close();
				this.saida.close();

			} catch (UnknownHostException ex) {
				System.out.println("GerenciadorServer: Host desconhecido");
			} catch (IOException ex) {
				System.out.println("GerenciadorServer: Erro de conexão: " + ex.getMessage());
			}
		}

	}
}