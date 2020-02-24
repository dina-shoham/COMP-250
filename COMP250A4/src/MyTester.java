import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
public class MyTester {
	public static <K, V> void main(String[] args) throws Exception {
		DecimalFormat f = new DecimalFormat("##0.000");
		SearchEngine s = new SearchEngine("test.xml");
		s.crawlAndIndex("www.cs.mcgill.ca");
		s.assignPageRanks(.0001);
		HashMap<String,Double> map = new HashMap<String,Double>();
		for(String site:s.internet.getVertices()) {
			map.put(site, s.internet.getPageRank(site));
		}
		ArrayList<String> sortedSites= Sorting.fastSort(map);

		System.out.println("**** Testing for edges into each site: ****\n");
		for(String v:s.internet.getVertices()) {
			System.out.print(+s.internet.getEdgesInto(v).size()+" Edges linked to "+v+": \t");
			System.out.println(s.internet.getEdgesInto(v)+"\n");
		}
		System.out.println();
		
		System.out.println("**** Testing if fastSort sorts sites is correct order: ****\n");
		ArrayList<String> results=Sorting.fastSort(map);
        ArrayList<String> actualResults = Sorting.slowSort(map);
		if (!actualResults.equals(results)) {
            System.out.println("fastSort implementation does not sort sites in correct order by rank");
            System.out.println("actual: "+actualResults);
            System.out.println("returned: "+results);
        }
        else {
        	System.out.println("fastSort sorts sites in correct order. congrats.");
            System.out.println("returned: "+results);
        }
		System.out.println();
		
		System.out.println("**** Testing how much faster fastSort is over slowSort: ****\n");
		HashMap<Integer,Integer> intMap = new HashMap<Integer,Integer>();
		for (int i = 1000; i <= 10000; i *= 2) {
            System.out.println("Testing fastSort method with " + i + " elements...");
            intMap.clear();
            for (int j = 0; j <= i; j++) {
                int val = j + (int)(Math.random() * (i-j+1));
                intMap.put(val, val);
            }

            long startTime = System.nanoTime();
            Sorting.slowSort(intMap);
            long endTime = System.nanoTime();
            long durationSlow = (endTime - startTime);
            System.out.println("time duration for slowSort: " + durationSlow + " ns");
                            
            intMap.clear();
            for (int j = 0; j <= i; j++) {
                int val = j + (int)(Math.random() * (i-j+1));
                intMap.put(val, val);
            }
            startTime = System.nanoTime();
            Sorting.fastSort(intMap);
            endTime = System.nanoTime();
            long durationFast = (endTime - startTime);
            System.out.println("time duration for fastSort: " + durationFast + " ns");

            if ((durationSlow/durationFast) >= 10) {
                System.out.println("Implementation for fastSort is fast enough for test case sample of " + i + " elements.");
                System.out.println("speedup over slowSort= "+(durationSlow/durationFast)+"x \n");
            }else {
            	System.out.println("Implementation for fastSort is **NOT** fast enough for test case sample of " + i + " elements.");
                System.out.println("speedup over slowSort= "+(durationSlow/durationFast)+"x \n");
            }
		
		}
		
		System.out.println("**** Testing if page ranks are within margin of error: ****\n");
		for(String v:sortedSites) {
			System.out.print(v+" rank: ");
			System.out.println(f.format(s.internet.getPageRank(v))+", ");
			System.out.println(Math.abs(Double.parseDouble(f.format(s.internet.getPageRank(v)))-(s.parser.getPageRank(v))) < 1e-5);
		}
		System.out.println();

		System.out.println("****Testing getResults(). Note that printed list should be in order of descending rank: ****\n");
		System.out.println("Results found for \"a\": "+s.getResults("a"));
		System.out.println("Results found for \"the\": "+s.getResults("the"));
		System.out.println("Results found for \"AND\": "+s.getResults("AND"));
		System.out.println("Results found for \"MCGILL\": "+s.getResults("mcGILL"));
		System.out.println();
		
		
		System.out.println("**** Testing wordIndex.  This is likely a long list of words : ****\n");

		for(String v:s.wordIndex.keySet()) {
			System.out.print("Sites containing \""+v+"\": \t");
			System.out.println(s.wordIndex.get(v));
		}
		
	}
	
}
