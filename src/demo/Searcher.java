package demo;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Searcher {

    private IndexSearcher searcher;
    private QueryParser titleParser;

    public Searcher(String indexDir) throws IOException {
        // open the index directory to search
        searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(indexDir))));
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);

        // defining the query parser to search items by title field.
        titleParser = new QueryParser(Version.LUCENE_36, indexDrama.TITLE, analyzer);

    }

    /**
      * This method is used to find the indexed items by the title.
      * @param queryString - the query string to search for
      */
    public List<indexDrama> findByTitle(String phrase, int numOfResults) throws ParseException, IOException {
    
        //create query from the incoming query string.
    	titleParser.setPhraseSlop(1);
   
        Query query = titleParser.parse("\""+ phrase + "\"");
       //query.getBoost();
         //TermQuery tq = new TermQuery(new Term(phrase, indexDrama.TITLE));
    	
    	//pq.setSlop(1);
    	//pq.setBoost(1.5f);
      // BooleanQuery query = new BooleanQuery();
       //query.add(tq, BooleanClause.Occur.SHOULD);
    	//PhraseQuery pq = new PhraseQuery();
    	//pq.add(new Term(indexDrama.TITLE, phrase));
    	//pq.setSlop(0);

        // execute the query and get the results
       
       
        ScoreDoc[] queryResults = searcher.search(query, numOfResults).scoreDocs;

        List<indexDrama> results = new ArrayList<indexDrama>();
        // process the results
        for (ScoreDoc scoreDoc : queryResults) {
            Document doc = searcher.doc(scoreDoc.doc);
            
            
            results.add(new indexDrama(doc.get(indexDrama.TITLE), doc.get(indexDrama.WEBURL)));
        }

         return results;
    }


    public void close() throws IOException {
        searcher.close();
    }
}
