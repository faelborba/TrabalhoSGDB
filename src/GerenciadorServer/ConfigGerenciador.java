package GerenciadorServer;

public class ConfigGerenciador {
	private int port = 0;
	private String studentServerHost = "";
	private int studentServerPort = 0;
	private String classServerHost = "";
	private int classServerPort = 0;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getStudentServerHost() {
		return studentServerHost;
	}

	public void setStudentServerHost(String studentServerHost) {
		this.studentServerHost = studentServerHost;
	}

	public int getStudentServerPort() {
		return studentServerPort;
	}

	public void setStudentServerPort(int studentServerPort) {
		this.studentServerPort = studentServerPort;
	}

	public String getClassServerHost() {
		return classServerHost;
	}

	public void setClassServerHost(String classServerHost) {
		this.classServerHost = classServerHost;
	}

	public int getClassServerPort() {
		return classServerPort;
	}

	public void setClassServerPort(int classServerPort) {
		this.classServerPort = classServerPort;
	}

}
