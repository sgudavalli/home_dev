package connectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapRConnectivityUsingNFS {

	public static void main(String[] args) throws IOException {

		
		String FILENAME = "/mapr/demo.mapr.com/data/customers.all.csv";

		BufferedReader br = null;
		FileReader fr = null;
		
		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

	}

}
