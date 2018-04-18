package ServerAluno;

import java.io.Serializable;
import java.util.ArrayList;

public class Aluno implements Serializable {
	private int idAluno;
	private String nomeAluno;
	private ArrayList<Integer> turmas = null;

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

	public ArrayList<Integer> getTurmas() {
		return turmas;
	}

	public void setTurmas(ArrayList<Integer> turmas) {
		this.turmas = turmas;
	}

}
