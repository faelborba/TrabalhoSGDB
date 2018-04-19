package ServerAluno;

import java.io.Serializable;

public class TurmaId implements Serializable {
	private int idTurma = 0;

	public TurmaId(int idTurma) {
		this.idTurma = idTurma;
	}

	public int getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}
}
