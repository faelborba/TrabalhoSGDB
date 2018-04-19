package ServerAluno;

import java.io.Serializable;
import java.util.ArrayList;

public class Aluno implements Serializable {
	private int idAluno;
	private String nomeAluno;
	private ArrayList<TurmaId> turmas = null;
	
	public Aluno(ArrayList<TurmaId> turmas) {
		this.turmas = turmas;
	}
	
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

	public ArrayList<TurmaId> getTurmas() {
		return turmas;
	}

	public void setTurmas(ArrayList<TurmaId> turmas) {
		this.turmas = turmas;
	}



}
