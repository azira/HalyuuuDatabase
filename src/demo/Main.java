package demo;

/**
 * 
 * @author Hazirah Hamdani
 * Title: Halyuuu
 * Description: Korea drama search using Lucene
 * 
 * Tutorial from 
 * http://fazlansabar.blogspot.com.au/2012/06/apache-lucene-tutorial-lucene-for-text.html
 * Credit: http://lucene.apache.org/core/
 */


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;

public class Main {

	// location where the index will be stored.
	private static final String INDEX_DIR = "index";
	private static final int DEFAULT_RESULT_SIZE = 50;
	private static ArrayList<File> queue = new ArrayList<File>();

	public static void main(String[] args) throws ParseException, IOException {

		// Add Files need to be indexed
		addFiles(new File(
				"/Users/Azira/Documents/WebSearchDrama/Halyuuu/src/com/halyuuu/client/data/"));

		// Remember to comment if already have index
		// indexList();

		// creating the Searcher to the same index location as the Indexer
		Searcher searcher = new Searcher(INDEX_DIR);

		String query = "18";
		// Spell check query
		spellCheck checker = new spellCheck(INDEX_DIR);
		List spellCheck = checker.correctWords(query, searcher);
		if (spellCheck != null) {
			System.out.println("The Query was: " + query);
			System.out.println("Do you mean: ");
			Iterator spellCheckr = spellCheck.iterator();
	        while (spellCheckr.hasNext()) {  
	        	System.out.println(spellCheckr.next() + "");  
	 
	        }  

		} else {
			List<indexDrama> result = searcher.findByTitle(query,
					DEFAULT_RESULT_SIZE);
			print(result);

		}
		searcher.close();
	}

	private static void indexList() throws IOException {
		Indexer indexer = new Indexer(INDEX_DIR);
		// Each file, index line by line
		for (File f : queue) {
			FileReader file = null;

			try {

				file = new FileReader(f);
				FileInputStream fstream = new FileInputStream(f);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				@SuppressWarnings("resource")
				BufferedReader dramaList = new BufferedReader(
						new InputStreamReader(in));
				String drama;
				// Read File Line By Line

				while ((drama = dramaList.readLine()) != null) {

					if (drama.contains("/**")) {
						continue;
					}
					// System.out.println(drama);
					String[] kname = drama.split("::");

					String title = kname[0];
					String weburl = kname[1];

					// creating the indexer and indexing current items
					indexDrama indexItem = new indexDrama(title, weburl);
					indexer.index(indexItem);

				}
			} catch (Exception e) {
				System.out.println("Could not add: " + f);
			} finally {
				file.close();
			}
		}

		// close the index to enable them index
		indexer.close();

	}

	/**
	 * print the results.
	 */
	private static void print(List<indexDrama> result) {
		System.out.println("Total result: " + result.size());

		for (indexDrama item : result) {
			System.out.println(item);
		}
	}

	/** 
	 * Add files under directory
	 * 
	 * @param file
	 */
	private static void addFiles(File file) {

		if (!file.exists()) {
			System.out.println(file + " does not exist.");
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				addFiles(f);
			}
		} else {
			String filename = file.getName().toLowerCase();
			if (filename.endsWith(".txt")) {
				queue.add(file);
			}
		}
	}
}
