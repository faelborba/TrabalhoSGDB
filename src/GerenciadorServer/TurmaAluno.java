package GerenciadorServer;

import java.io.Serializable;

import ServerAluno.Alunos;

@SuppressWarnings("serial")
public class TurmaAluno implements Serializable {
	private int idTurma = 0;
	private String nomeTurma = null;
	private Alunos alunos = null;

	public Alunos getAlunos() {
		return alunos;
	}

	public void setAlunos(Alunos alunos) {
		this.alunos = alunos;
	}

	public int getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}

	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

}
