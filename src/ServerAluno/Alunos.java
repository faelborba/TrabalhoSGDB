package ServerAluno;

import java.io.Serializable;
import java.util.ArrayList;

public class Alunos implements Serializable {
	private ArrayList<Aluno> alunos;
	
	public Alunos(ArrayList<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	public ArrayList<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(ArrayList<Aluno> alunos) {
		this.alunos = alunos;
	}

}
