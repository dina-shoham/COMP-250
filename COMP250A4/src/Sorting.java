import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
    	// ADD YOUR CODE HERE
    	// quickSort babey!! but kind of a weird implementation that creates arraylists for high and low keys
    	ArrayList<K> sorted = new ArrayList<K>();
    	sorted.addAll(results.keySet());	//Start with unsorted list of urls
    	
   		if(sorted.size() <= 1) {	// edge case: list short enough that it's already sorted
    		return sorted;
    	}
    	
   		sorted = quickSort(results, sorted, 0, sorted.size()-1);	// call quickSort on sorted, setting low index to 0 and high index to size-1
		return sorted;
    }
    
    private static <K, V extends Comparable> int placeAndDivide(HashMap<K,V> map, ArrayList<K> list, int low, int high) {
    	V pivot = map.get(list.get(high));
    	int wall = low;
    	for(int i = low; i < high; i++) {
    		if(map.get(list.get(i)).compareTo(pivot) > 0) {
    			// swap stuff
    			K tmp = list.get(wall);
    			list.set(wall, list.get(i));
    			list.set(i, tmp);
    			wall++;
    		}
    	}
    	// put tmp at wall
    	K tmp = list.get(wall);
    	list.set(wall, list.get(high));
    	list.set(high, tmp);
    	return wall;
    }
    
    private static <K, V extends Comparable> ArrayList<K> quickSort(HashMap<K,V> map, ArrayList<K> list, int low, int high) {
    	if(low < high) {
    		int split = placeAndDivide(map, list, low, high);
    		quickSort(map, list, low, split-1);
    		quickSort(map, list, split+1, high);
    	}
    	return list;
    }
}
