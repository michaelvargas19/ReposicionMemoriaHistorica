package Model;

/**
 * @author Christian
 */
public class FilePersistence {
	
	private String name;
	private Boolean[] verify = new Boolean[8];
	private String[] line = new String[8];
	private int number_lines = 0;
	
	public FilePersistence(String name) {
		super();
		this.name = name;
		for(int i=0; i<8; i++){
			this.verify[i] = false;
			this.line[i] =" ";
		}
	}
	
	public String getName() {
		return name;
	}
	public void setNombre(String name) {
		this.name = name;
	}
	public Boolean[] getVerify() {
		return verify;
	}
	public void setVerify(Boolean[] verify) {
		this.verify = verify;
	}
	public int getNumber_lines() {
		return number_lines;
	}
	public void setNumber_lines(int number_lines) {
		this.number_lines = number_lines;
	}
	public String[] getLine() {
		return line;
	}
	public void setLine(String[] line) {
		this.line = line;
	} 
	
	
	
}
