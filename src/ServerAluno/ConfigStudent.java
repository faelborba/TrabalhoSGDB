package ServerAluno;

public class ConfigStudent {
	public int port = 0; // Porta onde vai aceitar requisições
	public String datafile = ""; // Localização do arquivo de dados

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDatafile() {
		return datafile;
	}

	public void setDatafile(String datafile) {
		this.datafile = datafile;
	}
}
