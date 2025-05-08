import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Week5 {
	public static void main(String[] args) throws IOException {
		InputStream fis = new FileInputStream ("C:\\Users\\User_Name\\Desktop\\input.txt");
		InputStreamReader isr = new InputStreamReader (fis);
		BufferedReader br = new BufferedReader(isr);
		String data = br.readLine();
		System.out.println(data);
		br.close(); isr.close(); fis.close();
	}
}