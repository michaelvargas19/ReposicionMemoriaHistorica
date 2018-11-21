package Persistence;

import Model.FilePersistence;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Christian
 *
 */
public class ManejoArchivos {

	
	
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
	
	
	public static void writeFile (List<FilePersistence> file) throws IOException{
		String[] line = new String[8];
		for (FilePersistence f: file) {
			java.io.File files = new java.io.File(f.getName()+".txt");// + "
			line = f.getLine();
			
			FileWriter write = new FileWriter(files);//,true
			
			for(String s: line) {
				write.write(s+"\r\n");
			}
			write.close();
		}
	}
        
	public static void writeFile (String file,int line,String data) throws IOException{
		List<String> lines = new ArrayList<>();// = new String[8];
                try {
                lines=readClientFile(file+".txt");
                
            } catch (Exception e) {
            }
                while (lines.size()<8) {
                lines.add("");
                }
                lines.set(line-1, data);
                java.io.File files = new java.io.File(file+".txt");// + "
                FileWriter write = new FileWriter(files);//,true
                
                for(String s: lines) {
                    write.write(s+"\r\n");
                }
                write.close();
		
                
	}
	public static void writeFileNew (String file,String data) throws IOException{
                java.io.File files = new java.io.File(file);// + "
                FileWriter write = new FileWriter(files,true);//,true
                    write.write("\r\n"+data);
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