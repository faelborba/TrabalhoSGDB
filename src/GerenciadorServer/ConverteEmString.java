package GerenciadorServer;

import java.util.Scanner;

public class ConverteEmString {
	private String linha = "";

	public String converteJson(Scanner entrada) {
		while (entrada.hasNextLine()) {
			linha = linha + entrada.nextLine() + "\n";
			// System.out.println(entrada.nextLine());
		}
		return this.linha;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

}
