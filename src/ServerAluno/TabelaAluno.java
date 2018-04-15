package ServerAluno;
import java.util.ArrayList;

public class TabelaAluno  {
	
	private ArrayList <Aluno> tabelaAluno = new ArrayList<>();
	private Aluno aluno = null;
	
	public void insereAluno(int id, String nome) {
		aluno = new Aluno();
		this.aluno.setIdAluno(id);
		this.aluno.setNomeAluno(nome);
		tabelaAluno.add(aluno);
	}
	
}
