package GerenciadorServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

import ServerAluno.TabelaAluno;
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
		String ip = null;
		String porta = null;
		String textoJson = "", textoConfig = "";
		File arquivo = null;
		Scanner pegaTexto = null;
		ConfigGerenciador config = null;
		// ConverteEmString converte = new ConverteEmString();
		Gson gson = new Gson();

		protocolo = entrada.nextLine();
		System.out.println("GerenciadorServer: Protocolo \"" + protocolo + "\" Recebido.");

		String dados[] = protocolo.split("/");// Dividindo o protocolo em várias strings
		System.out.println("GerenciadorServer: Vou executar o comando " + dados[1]);

		try {
			if (dados[1].equals("incluiTurma") || dados[1].equals("turma") || dados[1].equals("turmas") || dados[1].equals("apagaTurma")) {
				arquivo = new File("tmp/ConfigGerenciador.json");// fazendo um objeto arquivo
				if (!arquivo.exists()) {// testando se o arquivo de configuração existe
					System.out.println("GerenciadorServer: Arquivo de configuração inexistente");
				}
				System.out.println("GerenciadorServer: Abrindo arquivo de configuração...");
				pegaTexto = new Scanner(arquivo); // pega texto do arquivo
				textoConfig = new ConverteEmString().converteJson(pegaTexto);
				System.out.println("GerenciadorServer: *teste*\n" + textoConfig);

				config = gson.fromJson(textoConfig, ConfigGerenciador.class);// converte json em object
				porta = Integer.toString(config.getClassServerPort());
				ip = config.getClassServerHost();

			}
			Socket s = new Socket(ip, Integer.parseInt(porta));// conecta no server
			// entrada, variável responsável pela retorno do server turma/aluno
			Scanner entrada = new Scanner(new InputStreamReader(s.getInputStream()));
			PrintWriter saida = new PrintWriter(s.getOutputStream());

			saida.println(protocolo);// envia protocolo ao server
			saida.flush();
			System.out.println("GerenciadorServer: Enviando protocolo " + protocolo + " para o banco Correspondente!");

			if (dados[1].equals("turma") || dados[1].equals("turmas")) {
				textoJson = new ConverteEmString().converteJson(entrada);// converte variavel entrada e em string
				System.out.println(textoJson);// teste
				Turma turma = gson.fromJson(textoJson, Turma.class);// Converte String em Objeto turma
			}
			if (dados[1].equals("incluiTurma") || dados[1].equals("apagaTurma")) {
				textoJson = new ConverteEmString().converteJson(entrada);// converte variavel entrada e em string
				System.out.println(textoJson);// teste
				CodigoRetorna codigoRetorna = gson.fromJson(textoJson, CodigoRetorna.class);// Converte String em Objeto
			}

			entrada.close();
			saida.close();
			s.close();

		} catch (UnknownHostException ex) {
			System.out.println("GerenciadorServer: Host desconhecido");
		} catch (IOException ex) {
			System.out.println("GerenciadorServer: Erro de conexão: " + ex.getMessage());
		}

	}
}