package GerenciadorServer;

import java.io.Serializable;
import java.util.ArrayList;

import ServerAluno.Aluno;

public class AlunosSemTurma implements Serializable {
	private ArrayList<AlunoSemTurma> alunos = null;

	public ArrayList<AlunoSemTurma> getAlunos() {
		return alunos;
	}

	public void setAlunos(ArrayList<AlunoSemTurma> alunos) {
		this.alunos = alunos;
	}
}
