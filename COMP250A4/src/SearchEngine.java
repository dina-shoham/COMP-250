/*
 * @author Dina Shoham
 * 260823582
 * COMP 250 Assignment 4
 * 
 */

import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		// TODO : Add code here
		// updating internet
		internet.addVertex(url);
		internet.setVisited(url, true);
		
		// updating wordIndex
		ArrayList <String> wordsInUrl = parser.getContent(url);
		for(int i = 0; i < wordsInUrl.size(); i++) {
			if(!wordIndex.containsKey(wordsInUrl.get(i))) { // if one of the words in the url (the word at index i) isn't already a key 
				ArrayList <String> urls = new ArrayList <String>();	// list of urls containing the word
				urls.add(url);
				wordIndex.put(wordsInUrl.get(i), urls);	// adding the word as a key and adding the url to its list of values
			}
			else {	// else if the word is already a key in the map
				wordIndex.get(wordsInUrl.get(i)).add(url);	// add url to that word's list of values
			}
		}
		
		ArrayList <String> linksTo = parser.getLinks(url);	// list of outgoing edges of a vertex (urls on this webpage)
		for(int i = 0; i < linksTo.size(); i++) {	// loop thru outgoing urls
			String newUrl = linksTo.get(i);	// set the new url
			if(!internet.vertexList.containsKey(linksTo.get(i))) {
				internet.addVertex(linksTo.get(i));	// if new url isn't in the thing then add it as a vertex
			}
			internet.addEdge(url, newUrl);	// add edge between url and new url
			if(!internet.getVisited(newUrl)) {	// if url hasn't been visited
				crawlAndIndex(newUrl);	// recur on new url
			}
		}	
	}
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		// TODO : Add code here
		// setting everything to 1
		ArrayList <String> vertices = internet.getVertices();
		for(int i = 0; i < vertices.size(); i++) {
			internet.setPageRank(vertices.get(i), 1.0);
		}
		// computing ranks
		ArrayList <Double> ranks = new ArrayList<Double>();
		Boolean done = false;
		while(true) {
			done = true;
			ranks = computeRanks(vertices);
			for(int i = 0; i < vertices.size(); i++) {	// for each vertex
				Double difference = internet.getPageRank(vertices.get(i)) - ranks.get(i);
				if(Math.abs(difference) >= epsilon) {
					done = false;
				}
				internet.setPageRank(vertices.get(i), ranks.get(i));
			}
			if(done) {
				break;
			}
		}	 	
	} 
	
	/*
	 * The method takes as input an Array)List<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		// TODO : Add code here
	ArrayList<Double> ranks = new ArrayList<Double>();
		
		// do the convergence thing
		double cur_rank;
		ArrayList <String> pagesIn = new ArrayList <String>();
		
		for(int i = 0; i < vertices.size(); i++) {	// loop through vertices
			pagesIn = internet.getEdgesInto(vertices.get(i));
			cur_rank = 0.5;
			for(int j = 0; j < pagesIn.size(); j++) {
				cur_rank += 0.5 * (internet.getPageRank(pagesIn.get(j))) / internet.getOutDegree(pagesIn.get(j));
			}
			ranks.add(cur_rank);
		}
		return ranks;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		// TODO: Add code here
		query = query.toLowerCase();
		ArrayList<String> results = new ArrayList<String>();	// arraylist of urls containing query
		if(wordIndex.get(query) == null) {	// edge case: query doesn't exist
			return results;
		}
		results = wordIndex.get(query);	// arraylist of urls containing query
		
		HashMap<String, Double> resultsMap = new HashMap<String, Double>();
		for(int i = 0; i < results.size(); i++) {
			resultsMap.put(results.get(i), internet.getPageRank(results.get(i)));
		}
		
		results = Sorting.fastSort(resultsMap);
		return results;		
	}
}
