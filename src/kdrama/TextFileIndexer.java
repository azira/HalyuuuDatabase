package kdrama;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;  
import org.apache.lucene.document.Document;  
import org.apache.lucene.document.Field;  
import org.apache.lucene.index.IndexWriter;  
import org.apache.lucene.index.IndexWriterConfig;  
import org.apache.lucene.index.Term;  
import org.apache.lucene.store.FSDirectory;  
import org.apache.lucene.util.Version;  
import java.io.File;  
import java.io.IOException; 
import org.apache.lucene.queryParser.ParseException;  
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.util.List;  
import java.io.*;
import java.util.ArrayList;
import org.apache.lucene.index.IndexReader;    
import org.apache.lucene.queryParser.QueryParser;  
import org.apache.lucene.search.*;  


/**
 * This terminal application creates an Apache Lucene index in a folder and adds
 * files into this index based on the input of the user.
 */
public class TextFileIndexer {
	static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);

	private IndexWriter writer;
	private ArrayList<File> queue = new ArrayList<File>();
	public static final String INDEX = "index";
	   private static IndexSearcher searcher;  

	public static void main(String[] args) throws Exception {

		TextFileIndexer indexer = null;
		try {
			indexer = new TextFileIndexer(INDEX);
		} catch (Exception ex) {
			System.out.println("Cannot create index..." + ex.getMessage());
			System.exit(-1);
		}

		// Reading files to index line by line

		// try to add file into the index
		//indexer.indexFileOrDirectory("/Users/Azira/Documents/WebSearchDrama/Halyuuu/src/com/halyuuu/client/data/");
		//indexer.closeIndex();
		
		searchFile("guy");
		//showFile();
	}

	// ===================================================
	// after adding, we always have to call the
	// closeIndex, otherwise the index is not created
	// ===================================================
	// indexer.closeIndex();
	// }

	

	/**
	 * Constructor
	 * 
	 * @param indexDir
	 *            the name of the folder in which the index should be created
	 * @throws java.io.IOException
	 */
	TextFileIndexer(String indexDir) throws IOException {
		// the boolean true parameter means to create a new index everytime,
		// potentially overwriting any existing files there.
		FSDirectory dir = FSDirectory.open(new File(indexDir));

		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);

		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_34,
				analyzer);

		writer = new IndexWriter(dir, config);
	}

	/**
	 * Indexes a file or directory
	 * 
	 * @param fileName
	 *            the name of a text file or a folder we wish to add to the
	 *            index
	 * @throws java.io.IOException
	 */
	public void indexFileOrDirectory(String fileName) throws IOException {
		// ===================================================
		// gets the list of files in a folder (if user has submitted
		// the name of a folder) or gets a single file name (is user
		// has submitted only the file name)
		// ===================================================
		addFiles(new File(fileName));

		int originalNumDocs = writer.numDocs();
		for (File f : queue) {
			FileReader file = null;
			//System.out.print(f);
			try {
				Document doc = new Document();
				//System.out.println("Went here");
				// ===================================================
				// add contents of file
				// ===================================================
				file = new FileReader(f);
				// command line parameter
				FileInputStream fstream = new FileInputStream(f);
				System.out.println(f);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader dramaList = new BufferedReader(
						new InputStreamReader(in));
				String drama;
				// Read File Line By Line
				while ((drama = dramaList.readLine()) != null) {
					if (drama.contains("/**")) {
						continue;
					}
					//System.out.println(drama);
					String[] kname = drama.split("::");
					
					String title = kname[0];
					String weburl = kname[1];
					//System.out.println(kname[0]);
					//System.out.println(weburl);
					doc.add(new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED));
					doc.add(new Field("weburl", weburl, Field.Store.YES, Field.Index.NOT_ANALYZED));

				}

			

			   writer.addDocument(doc);
				// System.out.println("Added: " + f);
			} catch (Exception e) {
				System.out.println("Could not add: " + f);
			} finally {
				file.close();
			}
		}

		int newNumDocs = writer.numDocs();
		System.out.println("");
		System.out.println("************************");
		System.out
				.println((newNumDocs - originalNumDocs) + " documents added.");
		System.out.println("************************");

		queue.clear();
	}

	private void addFiles(File file) {

		if (!file.exists()) {
			System.out.println(file + " does not exist.");
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				addFiles(f);
			}
		} else {
			String filename = file.getName().toLowerCase();
			// ===================================================
			// Only index text files
			// ===================================================
			if (filename.endsWith(".txt")) {
				queue.add(file);
			} else {
				System.out.println("Skipped " + filename);
			}
		}
	}
	
	public static void searchFile(String queryString) throws ParseException, IOException {
		 QueryParser parser = new QueryParser(Version.LUCENE_36,"title", analyzer);
		 IndexSearcher searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(INDEX)))); 
		 // create query from the incoming query string.  
	     Query query = parser.parse("guy");  
	     // execute the query and get the results  
	     ScoreDoc[] queryResults = searcher.search(query, 50).scoreDocs;  
	     System.out.println(queryResults.length);
	     // process the results  
	     for (ScoreDoc scoreDoc : queryResults) {  
	       Document doc = searcher.doc(scoreDoc.doc);  
	       System.out.println("Reach here?");
	       System.out.println(doc.get("title"));
	   
	     }  
	      
//		System.out.println("\nSearching for '" + string + "' using TermQuery");
//		FSDirectory directory = FSDirectory.open(new File(INDEX));
//		IndexReader indexReader = IndexReader.open(directory);
//		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//		TermQuery tq = new TermQuery(new Term("title", string));
//		ScoreDoc[] hits = indexSearcher.search(tq, null, 1000).scoreDocs;
//		System.out.println("Number of hits: " + hits.length);
//
//		for (int i = 0; i < hits.length; i++) {
//			Document document = indexSearcher.doc(hits[i].doc);
//
//			String title = document.get("title");
//			System.out.println("Hit: " + title);
//		}

	}


		
	
	
	public static void showFile() throws IOException {
		   FSDirectory dir = FSDirectory.open(new File(INDEX));
		 IndexReader r = IndexReader.open(dir);
		

		 int num = r.numDocs();
		 for ( int i = 0; i < num; i++)
		 {
		     if ( ! r.isDeleted( i))
		     {
		         Document d = r.document(i);
		         System.out.println( d + "\n");
		     }
		 }
		 r.close();
		
	}

	/**
	 * Close the index.
	 * 
	 * @throws java.io.IOException
	 */
	public void closeIndex() throws IOException {
		writer.close();
	}
}