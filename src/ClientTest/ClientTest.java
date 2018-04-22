package ClientTest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import GerenciadorServer.ConverteEmString;

public class ClientTest {
	public static void main(String[] args) {
		String ip = "localhost";
		String porta = "1235";
		String protocolo = null;
		String r = null, textoJson = null;
		Scanner teclado = new Scanner(System.in);
		System.out.println("Cliente Rodando!");
		try {
			while (true) {
				Socket s = new Socket(ip, Integer.parseInt(porta));// conecta no server
				Scanner entrada = new Scanner(new InputStreamReader(s.getInputStream()));// stancia uma variável p/
																							// entrada de dados
				PrintWriter saida = new PrintWriter(s.getOutputStream());

				System.out.println("O que deseja fazer?\n 1 adicionar uma Turma? \t| 5 Adicionar Aluno?\n"
						+ " 2 PEsquisar uma turma? \t| 6 Buscar um Aluno?\n"
						+ " 3 Excluir turma? \t\t| 7 Apagar Aluno?\n"
						+ " 4 Mostrar todas as turmas? \t| 8 Buscar todos os alunos?");
				r = teclado.nextLine();
				if (r.equals("1")) {
					System.out.println("Digite o codigo da turma a ser inserida");
					protocolo = "/incluiTurma/" + teclado.nextLine();
					System.out.println("Digite o nome da turma a ser inserida");
					protocolo = protocolo + "/" + teclado.nextLine();
				} else if (r.equals("2")) {
					System.out.println("Digite o codigo da turma Para busca");
					protocolo = "/turma/" + teclado.nextLine();
				} else if (r.equals("3")) {
					System.out.println("Digite o codigo da turma Para Excluir");
					protocolo = "/apagaTurma/" + teclado.nextLine();
				} else if (r.equals("4")) {
					System.out.println("exclusão de Turma");
					protocolo = "/turmas/";
				} else if (r.equals("5")) {
					System.out.println("Digite o codigo do Aluno a ser inserido");
					protocolo = "/incluiAluno/" + teclado.nextLine();
					System.out.println("Digite o nome do aluno a ser inserido.");
					protocolo = protocolo + "/" + teclado.nextLine();
					System.out.println("Digite a Lista de turmas separado por virgula (,).");
					protocolo = protocolo + "/" + teclado.nextLine();
				} else if (r.equals("6")) {
					System.out.println("Digite o código do aluno para Buscar.");
					protocolo = "/aluno/" + teclado.nextLine();
				} else if (r.equals("7")) {
					System.out.println("Digite o codigo do aluno para ser excluido.");
					protocolo = "/apagaAluno/" + teclado.nextLine();					
				} else if (r.equals("8")) {
					System.out.println("exclusão de Aluno.");
					protocolo = "/alunos";
				}

				System.out.println("Enviando protocolo " + protocolo + " para o server7!");
				saida.println(protocolo);// envia protocolo ao server
				saida.flush();

				textoJson = new ConverteEmString().converteJson(entrada);// converte entrada e em string
				System.out.println(textoJson);// teste
			}

		} catch (UnknownHostException ex) {
			System.out.println("Host desconhecido");
		} catch (IOException ex) {
			System.out.println("Erro de conexão: " + ex.getMessage());
		}
	}
}
