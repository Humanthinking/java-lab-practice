import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Reverse {
	public static void main(String args[]) throws IOException {
		FileInputStream fis = new FileInputStream("/Users/youngjae/eclipse-workspace/Class_7/input.txt");
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		FileOutputStream fos = new FileOutputStream("/Users/youngjae/eclipse-workspace/Class_7/output.txt");
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		
		ArrayList <String> rcd = new ArrayList<>();
		
		String data;
		while((data = br.readLine()) != null) {
			rcd.add(data);
		}
		
		br.close();
		isr.close();
		fis.close();
		
		for(int i = rcd.size()-1; i >= 0; i--) {
			bw.write(rcd.get(i));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		osw.close();
		fos.close();
	}
}
