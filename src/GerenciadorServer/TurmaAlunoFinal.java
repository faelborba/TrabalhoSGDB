package GerenciadorServer;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TurmaAlunoFinal implements Serializable {
	private int idTurma = 0;
	private String nomeTurma = null;
	private AlunosSemTurma alunos = null;

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

	public AlunosSemTurma getAlunos() {
		return alunos;
	}

	public void setAlunos(AlunosSemTurma alunos) {
		this.alunos = alunos;
	}
}
