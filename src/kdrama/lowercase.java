package kdrama;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

class lowercase {
	public static void main(String args[]) {
		Set<String> lines = new HashSet<String>();
		try {
			// Add to lines whats in new1.txt
			FileInputStream fstream1 = new FileInputStream(
					"/Users/Azira/theDic.txt");
			// Get the object of DataInputStream
			DataInputStream in1 = new DataInputStream(fstream1);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String line;
			while ((line = br1.readLine()) != null) {
				
						lines.add(line.toLowerCase());
						
				
			}
			
			Set<String> treeSet = new TreeSet<String>();
			treeSet.addAll(lines);
			System.out.println(treeSet);
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
						"/Users/Azira/new5.txt"), true));
				
				
				Iterator hashSetIterator = treeSet.iterator();
		        while (hashSetIterator.hasNext()) {  
		        	bw.write(hashSetIterator.next() + "");  
		        	bw.newLine();
		        }  
					
		      
					bw.close();
					
				
				
				System.out.println("File created successfully!");
			} catch (Exception e) {
			}
			
			// Close the input stream
			in1.close();
			
//			// Open the file that is the first
//			// command line parameter
//			FileInputStream fstream = new FileInputStream(
//					"/Users/Azira/Documents/WebSearchDrama/Halyuuu/src/com/halyuuu/client/data/KDrama2012.txt");
//			// Get the object of DataInputStream
//			DataInputStream in = new DataInputStream(fstream);
//			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			String strLine;
//			// Read File Line By Line
//			//int count = 0;
//			while ((strLine = br.readLine()) != null) {
//				String[] kname = strLine.split("::");
//				String[] splitName = kname[0].split(" ");
//				for(int i=0; i < splitName.length; i++) {
//				
//					if (!(lines.contains(splitName[i])))  {
//						lines.add(splitName[i]);
//						writeToFile(splitName[i]);
//					}
//				}
//			}
//			System.out.println(lines);
//			// Close the input stream
//			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void writeToFile(String name) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					"/Users/Azira/new1.txt"), true));
			
			
				bw.write(name);
				bw.newLine();
				bw.close();
				
			
			
			System.out.println("File created successfully!");
		} catch (Exception e) {
		}
	}

	

}