package GerenciadorServer;

import java.io.Serializable;
import java.util.ArrayList;

public class AlunoSemTurma implements Serializable {
	private int idAluno;
	private String nomeAluno;

	public int getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

}
