package ServerTurma;

import java.io.Serializable;

public class Turma implements Serializable{
	private int idTurma = 0;
	private String nomeTurma = null;
	
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
