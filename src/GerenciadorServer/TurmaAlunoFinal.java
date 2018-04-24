package GerenciadorServer;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TurmaAlunoFinal implements Serializable {
	private int idTurma = 0;
	private String nomeTurma = null;
//	private AlunosSemTurma alunos = null;
	private ArrayList<AlunoSemTurma> alunos = null;

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

	public ArrayList<AlunoSemTurma> getAlunos() {
		return alunos;
	}

	public void setAlunos(ArrayList<AlunoSemTurma> alunos) {
		this.alunos = alunos;
	}

	/*public AlunosSemTurma getAlunos() {
		return alunos;
	}

	public void setAlunos(AlunosSemTurma alunos) {
		this.alunos = alunos;
	}*/
}
