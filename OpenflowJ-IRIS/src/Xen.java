import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Xen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec("python xen.py");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} 

		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		
		try {
			while ((line = b.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
