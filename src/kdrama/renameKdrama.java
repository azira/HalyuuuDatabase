package kdrama;

import java.io.*;
import java.net.URL;

class renameKdrama {
	public static void main(String args[]) {
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(
					"/Users/Azira/Documents/WebSearchDrama/Halyuuu/src/com/halyuuu/client/data/wikipedia.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				
				// Print the content on the console
				//String[] kname = strLine.split("::");
				String[] kname = strLine.split("::");
				
			
				writeToFile(kname[1], kname[0]);
				//System.out.println(kname[1]);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void writeToFile(String name, String url) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					"/Users/Azira/new.txt"), true));
			bw.write(name + "::" + url);
			bw.newLine();
			bw.close();
			System.out.println("File created successfully!");
		} catch (Exception e) {
		}
	}

	

}