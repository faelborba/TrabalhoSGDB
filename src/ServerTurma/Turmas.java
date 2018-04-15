package ServerTurma;

import java.io.Serializable;
import java.util.ArrayList;

public class Turmas implements Serializable {
	private ArrayList<Turma> turmas;

	public Turmas(ArrayList<Turma> turmas) {
		this.turmas = turmas;
	}

	public ArrayList<Turma> getTurmas() {
		return turmas;
	}

	public void setTabelaTurma(ArrayList<Turma> tabelaTurma) {
		this.turmas = tabelaTurma;
	}

}
