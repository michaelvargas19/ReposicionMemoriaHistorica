package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Michael Vargas y María Camila Jaramilo. 
 *
 */
public class Utils {

	
	/**Method responsible for reading the file. 
	 * @param file_name
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static String[][] read_InFile(String file_name) throws FileNotFoundException, IOException, InterruptedException {	
		
		String filesNumber[][] = new String [8][2];
		String chain;
		int row = 0; 
		FileReader f = new FileReader(file_name);
		BufferedReader buffer = new BufferedReader(f);
		
		while ((chain = buffer.readLine()) != null) {
			
			StringTokenizer tokens = new StringTokenizer(chain,"-");
			int aux = 0;
			String token;
			
			while (tokens.hasMoreTokens()) {
				
				if(aux==0){
					token = tokens.nextToken();
					filesNumber[row][aux] = token;
					aux = 1; 
				}
				
				if(aux==1) {
					token = tokens.nextToken();
					filesNumber[row][aux]=token;
					row++;
					aux=0;
				}
			}
		}
                    
		buffer.close();
		return filesNumber;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//String fileName = "prueba.txt";
		//writeFile(newFile(read_InFile(fileName)));
		ArrayList<String> names = new ArrayList<String>();
		names = readClientFile("cliente.txt");
		for (String s: names) {
			System.out.println(s);
		}
		
	}
	
	/**Method that generates the new objects of type file, using the matrix from the method read_InFile. 
	 * @param filesNumber
	 * @throws IOException 
	 */
	public static List<File> newFile (String[][] filesNumber) throws IOException{
		List<File> newFile = new ArrayList<File>();
		Boolean[] verify = new Boolean[8];
		String[] line = new String[8];
		ArrayList<String> fileLines  = new ArrayList<String>();
		int aux_lines; 
		
		for(int i=0; i<8; i++) {
			File file = new File(filesNumber[i][0]);
			verify = file.getVerify();
			line = file.getLine();
			fileLines = readClientFile(filesNumber[i][0]);
			
			int  a = Integer.parseInt(filesNumber[i][1])-1;
			verify[a] = true; 
			line[a] = fileLines.get(a); 
			file.setLine(line);
			file.setVerify(verify);
			
			aux_lines = file.getNumber_lines();
			aux_lines++; 
			
			file.setNumber_lines(aux_lines);
			newFile.add(file);
 		}
		
		return newFile; 
		
	}
	
	/**Method that creates the new files. 
	 * @param file
	 * @throws IOException
	 */
	public static void writeFile (List<File> file) throws IOException{
		String[] line = new String[8];
		for (File f: file) {
			java.io.File files = new java.io.File(f.getName()+"Salida.txt");// + "
			line = f.getLine();
			
			FileWriter write = new FileWriter(files);//,true
			
			for(String s: line) {
				write.write(s+"\n");
			}
			write.close();
		}
	}
        
	public static void writeFile (String file,int line,String data) throws IOException{
		List<String> lines = null;// = new String[8];
                System.out.println("sacando arhibo "+file+"salida.txt");
                lines=readClientFile(file+"Salida.txt");
                while (lines.size()<8) {
                lines.add("");
                }
                lines.set(line-1, data);
                java.io.File files = new java.io.File(file+"Salida.txt");// + "
                FileWriter write = new FileWriter(files);//,true
                for(String s: lines) {
                    write.write(s+"\n");
                }
                write.close();
		
                
	}
        
	public static ArrayList<String> readClientFile(String file_Name) throws IOException {
		ArrayList<String> nombres = new ArrayList<String>();
		String chain;
		FileReader f = new FileReader(file_Name);
		BufferedReader b = new BufferedReader(f);
		
		while ((chain = b.readLine()) != null) {
			
			nombres.add(chain);
		}
		b.close();
		
		return nombres;
	}
	
}