package ClientTest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTest {
	public static void main(String[] args) {
		String ip = "localhost";
		String porta = "1236";
		String protocolo = null;
		String r = null, cod = null;
		Scanner teclado = new Scanner(System.in);
		System.out.println("Cliente Rodando!");
		try {
			while (true) {
				Socket s = new Socket(ip, Integer.parseInt(porta));// conecta no server
				Scanner entrada = new Scanner(new InputStreamReader(s.getInputStream()));// stancia uma variável p/
																							// entrada de dados
				PrintWriter saida = new PrintWriter(s.getOutputStream());

				System.out.println("O que deseja fazer?\n 1 adicionar uma Turma?\n 2 PEsquisar uma turma?"
						+ "\n 3 Excluir turma?\n 4 Mostrar todas as turmas." + "\n 5 Adicionar Aluno");
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
					protocolo = "/turmas/";
				} else if (r.equals("5")) {
					System.out.println("Digite o codigo do Aluno a ser inserido");
					protocolo = "/incluiAluno/" + teclado.nextLine();
					System.out.println("Digite o nome do aluno a ser inserido.");
					protocolo = protocolo + "/" + teclado.nextLine();
					System.out.println("Digite a Lista de turmas separado por virgula (,).");
					protocolo = protocolo + "/" + teclado.nextLine();
				}

				System.out.println("Enviando protocolo " + protocolo + " para o server gerente!");
				saida.println(protocolo);// envia protocolo ao server
				saida.flush();
			}

		} catch (UnknownHostException ex) {
			System.out.println("Host desconhecido");
		} catch (IOException ex) {
			System.out.println("Erro de conexão: " + ex.getMessage());
		}
	}
}
