import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Reverse2 {
	public static void main(String args[]) throws IOException {
		FileInputStream fis = new FileInputStream("/Users/youngjae/eclipse-workspace/Class_7/input.txt");
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		FileOutputStream fos = new FileOutputStream("/Users/youngjae/eclipse-workspace/Class_7/output.txt");
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		
		
		String data;
		while((data = br.readLine()) != null) {
			stk.push(data);
		}
		
		br.close();
		isr.close();
		fis.close();
		
		while(!stk.empty()) {
			bw.write(stk.pop());
			bw.newLine();
		}
		bw.flush();
		bw.close();
		osw.close();
		fos.close();
	}
}
