package ServerTurma;

import java.io.*;
import java.net.Socket;
import java.util.*;

import com.google.gson.*;

import GerenciadorServer.CodigoRetorna;
import GerenciadorServer.ConverteEmString;

public class TrataTurma extends Thread implements Serializable {
	// variáveis do server
	private Socket socket;
	private Scanner entrada;
	private PrintWriter saida;

	// preparando entrada e saída do server
	public TrataTurma(Socket socket) throws IOException {
		this.socket = socket;
		this.entrada = new Scanner(this.socket.getInputStream());
		this.saida = new PrintWriter(this.socket.getOutputStream());
	}

	public void run() {
		ObjectInputStream inputStream = null;
		ObjectOutputStream streamSaida = null;
		ArrayList<Turma> turmas = null;
		Turmas tabelaTurma = null;
		Turma turma = null;
		Gson objJson = new GsonBuilder().setPrettyPrinting().create();
		Scanner pegaTexto = null;
		String caminhoBanco = "", textoConfig = "";
		ConfigClass config = null;
		Gson gson = new Gson();

		int id;
		String nome;
		String protocolo;
		String textoRetorna = null;
		File arquivo = null;// Criando uma variavel para trabalhar com arquivos

		protocolo = entrada.nextLine();
		System.out.println("Server Turma: Protocolo \"" + protocolo + "\" Recebido.");
		String dados[] = protocolo.split("/");// Dividindo o protocolo em várias strings

		arquivo = new File("tmp/ConfigClass.json");// fazendo um objeto arquivo
		if (!arquivo.exists()) {// testando se o arquivo já existe, caso não exista cria um novo
			System.out.println("Server Turma: Arquivo de configuração inexistente");
		} else {
			System.out.println("Server Turma: Abrindo arquivo de configuração...");
			try {
				pegaTexto = new Scanner(arquivo); // pega texto do arquivo
				textoConfig = new ConverteEmString().converteJson(pegaTexto);
				System.out.println("Server Turma: *Arquivo ConfigClass.json*\n" + textoConfig);

				config = gson.fromJson(textoConfig, ConfigClass.class);// converte json em object
				caminhoBanco = config.getDatafile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			arquivo = new File(caminhoBanco);// fazendo um objeto arquivo para tabela de turma;
			if (!arquivo.exists()) {// testando se o arquivo já existe, caso não exista cria um novo
				System.out.println("Server Turma: Arquivo inexistente, Criando arquivo DB");
				try {
					streamSaida = new ObjectOutputStream(new FileOutputStream(arquivo));// aqui cria um arquivo conforme
																						// o objeto arquivo.
					turmas = new ArrayList<>();// cria uma array list de turmas
					tabelaTurma = new Turmas(turmas);// insere o arraylist turmas dentro da tabelaturma
					streamSaida.writeObject(tabelaTurma);// Grava o arraylist dentro do arquivo
					streamSaida.close();// fecha arquivo
					System.out.println("Server Turma: *Arquivo criado com sucesso.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				int i = 0;
				System.out.println("Server Turma: Abrindo arquivo Turma...");
				inputStream = new ObjectInputStream(new FileInputStream(arquivo));// abro o arquivo e traz o conteúdo
				System.out.println("Server Turma: Buscando Tabela de turma");
				tabelaTurma = (Turmas) inputStream.readObject();// busca o objeto tabelaTurma do arquivo
				System.out.println("Server Turma: Tabela Turma encontrada");
				System.out.print("Server Turma: Vou executar o comando: ");

				// A partir daqui o arquivo com certeza existe.
				if (dados[1].equals("incluiTurma")) {
					System.out.println(dados[1]);
					CodigoRetorna codigoRetorna = new CodigoRetorna();
					for (i = 0; i < tabelaTurma.getTurmas().size(); i++) {// testando ID já cadastrado.
						if (Integer.parseInt(dados[2]) == tabelaTurma.getTurmas().get(i).getIdTurma()) {
							System.out.println("Server Turma: Id Turma já cadastrada.");
							codigoRetorna.setCodRetorno(1);
							codigoRetorna.setDescricaoRetorno("Registro Já Cadastrado");
							textoRetorna = objJson.toJson(codigoRetorna);
							System.out.println(textoRetorna);// mostrando o conteudo json
							this.saida.println(textoRetorna);// devolvendo para o cliente em json
							this.saida.flush();
							this.saida.close();
							break;
						}
					}

					if (i == tabelaTurma.getTurmas().size()) {
						turma = new Turma();
						turma.setIdTurma(Integer.parseInt(dados[2]));
						turma.setNomeTurma(dados[3]);
						tabelaTurma.getTurmas().add(turma);// adicionando a turma em umarraylist

						System.out.println("Server Turma: Gravando: " + dados[1]);
						streamSaida = new ObjectOutputStream(new FileOutputStream(arquivo));
						streamSaida.writeObject(tabelaTurma);// Grava o arraylist dentro do arquivo
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
				if (dados[1].equals("apagaTurma")) {
					System.out.println(dados[1]);
					CodigoRetorna codigoRetorna = new CodigoRetorna();
					for (i = 0; i < tabelaTurma.getTurmas().size(); i++) {
						if (Integer.parseInt(dados[2]) == tabelaTurma.getTurmas().get(i).getIdTurma()) {
							System.out.println("Server Turma: Id Turma encontrada.\n Apagando Registro.");
							tabelaTurma.getTurmas().remove(i)/* .equals(tabelaTurma.getTurmas().get(i)) */;

							System.out.println("Server Turma: Gravando: " + dados[1]);
							streamSaida = new ObjectOutputStream(new FileOutputStream(arquivo));
							streamSaida.writeObject(tabelaTurma);// Grava o arraylist dentro do arquivo
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
					if (i == tabelaTurma.getTurmas().size()) {
						codigoRetorna.setCodRetorno(2);
						codigoRetorna.setDescricaoRetorno("Erro de Relacionamento");
						textoRetorna = objJson.toJson(codigoRetorna);
						System.out.println(textoRetorna);// mostrando o conteudo json

						this.saida.println(textoRetorna);// devolvendo para o cliente em json
						this.saida.flush();
						this.saida.close();
					}

				}
				if (dados[1].equals("turma")) {
					System.out.println(dados[1]);
					CodigoRetorna codigoRetorna = new CodigoRetorna();
					for (i = 0; i < tabelaTurma.getTurmas().size(); i++) {// testando ID já cadastrado.
						System.out.println("Server Turma: procurando a turma");
						if (Integer.parseInt(dados[2]) == tabelaTurma.getTurmas().get(i).getIdTurma()) {
							turma = new Turma();
							turma.setIdTurma(tabelaTurma.getTurmas().get(i).getIdTurma());
							turma.setNomeTurma(tabelaTurma.getTurmas().get(i).getNomeTurma());

							textoRetorna = objJson.toJson(turma);
							System.out.println(textoRetorna);// mostrando o conteudo json

							this.saida.println(textoRetorna);// devolvendo para o cliente em json
							this.saida.flush();
							this.saida.close();

							break;
						}

					}
					if (i == tabelaTurma.getTurmas().size()) {
						System.out.println("Server Turma: Id Turma Não encontrado.");
						codigoRetorna.setCodRetorno(2);
						codigoRetorna.setDescricaoRetorno("Erro de Relacionamento");
						textoRetorna = objJson.toJson(codigoRetorna);
						System.out.println(textoRetorna);// mostrando o conteudo json

						this.saida.println(textoRetorna);// devolvendo para o cliente em json
						this.saida.flush();
						this.saida.close();
					}
				}
				if (dados[1].equals("turmas")) {
					System.out.println(dados[1]);
					CodigoRetorna codigoRetorna = new CodigoRetorna();
					if (!(tabelaTurma.getTurmas().size() == 0)) {
						textoRetorna = objJson.toJson(tabelaTurma);
						System.out.println(textoRetorna);// mostrando o conteudo json

						this.saida.println(textoRetorna);// devolvendo para o cliente em json
						this.saida.flush();
						this.saida.close();
					} else {
						System.out.println("Server Turma: Tabela turma vazia.");
						codigoRetorna.setCodRetorno(2);
						codigoRetorna.setDescricaoRetorno("Erro de Relacionamento");
						textoRetorna = objJson.toJson(codigoRetorna);
						System.out.println(textoRetorna);// mostrando o conteudo json

						this.saida.println(textoRetorna);// devolvendo para o cliente em json
						this.saida.flush();
						this.saida.close();
					}

				}
				if (dados[1].equals("turmas")) {

				}

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

}