import java.io.IOException;
import java.io.InputStreamReader;


public class streamExercise {
	public static void main(String[] args) throws IOException {
		InputStreamReader isr = new InputStreamReader (System.in);
		char[] buffer= new char[128];
		// read characters on buffer
		while (isr.read(buffer) != -1) {
			System.out.print(buffer);
		}
		isr.close();
	}
}
