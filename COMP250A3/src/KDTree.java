/*
 * @author Dina Shoham
 * 260823582
 * COMP 250 ASSIGNMENT 3
 * 
 */
import java.util.ArrayList;
import java.util.Iterator;
public class KDTree implements Iterable<Datum>{ 

	KDNode 		rootNode;
	int    		k; 
	int			numLeaves;
	
	// constructor

	public KDTree(ArrayList<Datum> datalist) throws Exception {

		Datum[]  dataListArray  = new Datum[ datalist.size() ]; 

		if (datalist.size() == 0) {
			throw new Exception("Trying to create a KD tree with no data");
		}
		else
			this.k = datalist.get(0).x.length;

		int ct=0;
		for (Datum d :  datalist) {
			dataListArray[ct] = datalist.get(ct);
			ct++;
		}
		
	//   Construct a KDNode that is the root node of the KDTree.

		rootNode = new KDNode(dataListArray);
	}
	
	//   KDTree methods
	
	public Datum nearestPoint(Datum queryPoint) {
		return rootNode.nearestPointInNode(queryPoint);
	}
	

	public int height() {
		return this.rootNode.height();	
	}

	public int countNodes() {
		return this.rootNode.countNodes();	
	}
	
	public int size() {
		return this.numLeaves;	
	}

	//-------------------  helper methods for KDTree   ------------------------------

	public static long distSquared(Datum d1, Datum d2) {

		long result = 0;
		for (int dim = 0; dim < d1.x.length; dim++) {
			result +=  (d1.x[dim] - d2.x[dim])*((long) (d1.x[dim] - d2.x[dim]));
		}
		// if the Datum coordinate values are large then we can easily exceed the limit of 'int'.
		return result;
	}

	public double meanDepth(){
		int[] sumdepths_numLeaves =  this.rootNode.sumDepths_numLeaves();
		return 1.0 * sumdepths_numLeaves[0] / sumdepths_numLeaves[1];
	}
	
	class KDNode { 

		boolean leaf;
		Datum leafDatum;           //  only stores Datum if this is a leaf
		
		//  the next two variables are only defined if node is not a leaf

		int splitDim;      // the dimension we will split on
		int splitValue;    // datum is in low if value in splitDim <= splitValue, and high if value in splitDim > splitValue  

		KDNode lowChild, highChild;   //  the low and high child of a particular node (null if leaf)
		  //  You may think of them as "left" and "right" instead of "low" and "high", respectively

		KDNode(Datum[] datalist) throws Exception{

			/*
			 *  This method takes in an array of Datum and returns 
			 *  the calling KDNode object as the root of a sub-tree containing  
			 *  the above fields.
			 */

			//   ADD YOUR CODE BELOW HERE			
			if(datalist.length == 1) {	// if leaf
				leaf = true;
				leafDatum = datalist[0];
				lowChild = null;
				highChild = null;
				numLeaves++;
			}
			
			// finding max and min values, setting range
			else {
				int range = 0;
				for(int i = 0; i < k; i++) {	// loop thru dimensions
					int min = (datalist[0].x)[i];	// max and min of each dimension
					int max = (datalist[0].x)[i];;	// set both to the value at 0 in dimension i of datalist
				
					for(int j = 0; j < datalist.length; j++) {	// loop thru datum in array
						if(datalist[j].x[i] < min) {
							min = datalist[j].x[i];	// set min
						}
						else if(datalist[j].x[i] > max) {
							max = datalist[j].x[i];	// set max
						}
					}
					
					if(Math.abs(max-min) > range) {	// select the biggest range
						range = Math.abs(max-min);
						splitValue = min + range/2;
						splitDim = i;
					}	
				} 
				
				// initializing high and low arraylists (want dynamic sizing)
				ArrayList<Datum> lowList = new ArrayList<Datum>();
				ArrayList<Datum> highList = new ArrayList<Datum>();
				
				// edge case where list contains all duplicate points
				if(range == 0) {
					this.leaf = true;
					this.leafDatum = datalist[0];
					lowChild = null;
					highChild = null;
					numLeaves ++;
				}
				else {
					this.leaf = false;
					for(int i = 0; i < datalist.length; i++) {
						if (datalist[i].x[splitDim] <= splitValue) {
							lowList.add(datalist[i]);
						}
						else if (datalist[i].x[splitDim] > splitValue) {
							highList.add(datalist[i]);
						}
					}
					// creating low and high children
					lowChild = new KDNode(lowList.toArray(new Datum[lowList.size()]));
					highChild = new KDNode(highList.toArray(new Datum[highList.size()]));
				}
				
			}			

			//   ADD YOUR CODE ABOVE HERE

		}

		public Datum nearestPointInNode(Datum queryPoint) {
			Datum nearestPoint, nearestPoint_otherSide;
			double nearestDist = 0;
			double splitDist = 0;
		
			//   ADD YOUR CODE BELOW HERE
			// base case: queryPoint is a match to a leaf
			
			if(this.leaf) {
				return this.leafDatum;
			}	// case 1: check low child 
			else if(queryPoint.x[this.splitDim] >= this.splitValue) {		
				nearestPoint = lowChild.nearestPointInNode(queryPoint);

				nearestDist = distSquared(queryPoint, nearestPoint);	// distance between query and nearest point
				splitDist = (long)(queryPoint.x[this.splitDim] - this.splitValue) * (queryPoint.x[this.splitDim] -this.splitValue);	// distance to split
				
				if(nearestPoint.equals(queryPoint)) {	// if nearest point is the same as query point
					return nearestPoint;
				}
				if(splitDist > nearestDist) {
					return nearestPoint;
				}
				else {
					nearestPoint_otherSide = highChild.nearestPointInNode(queryPoint);	// go into high case
					if(distSquared(queryPoint, nearestPoint_otherSide) < nearestDist) {
						return nearestPoint_otherSide;
					}
				}
			}
			// case 2: checking high child
			else {
				nearestPoint = highChild.nearestPointInNode(queryPoint);

				nearestDist = distSquared(queryPoint, nearestPoint);	// distance between query and nearest point
				splitDist = (long)(queryPoint.x[this.splitDim] - this.splitValue) * (queryPoint.x[this.splitDim] - this.splitValue);	// distance to split

				if(nearestPoint.equals(queryPoint)) {	// if nearest point is the same as query point
					return nearestPoint;
				}
				if(splitDist > nearestDist) {
					return nearestPoint;
				}else {
					nearestPoint_otherSide = lowChild.nearestPointInNode(queryPoint);	// go into low case
					if(distSquared(queryPoint, nearestPoint_otherSide) < nearestDist) {
						return nearestPoint_otherSide;
					}
				}
			}
			return nearestPoint;
			//   ADD YOUR CODE ABOVE HERE

		}
		
		// -----------------  KDNode helper methods (might be useful for debugging) -------------------

		public int height() {
			if (this.leaf) 	
				return 0;
			else {
				return 1 + Math.max( this.lowChild.height(), this.highChild.height());
			}
		}

		public int countNodes() {
			if (this.leaf)
				return 1;
			else
				return 1 + this.lowChild.countNodes() + this.highChild.countNodes();
		}
		
		/*  
		 * Returns a 2D array of ints.  The first element is the sum of the depths of leaves
		 * of the subtree rooted at this KDNode.   The second element is the number of leaves
		 * this subtree.    Hence,  I call the variables  sumDepth_size_*  where sumDepth refers
		 * to element 0 and size refers to element 1.
		 */
				
		public int[] sumDepths_numLeaves(){
			int[] sumDepths_numLeaves_low, sumDepths_numLeaves_high;
			int[] return_sumDepths_numLeaves = new int[2];
			
			/*     
			 *  The sum of the depths of the leaves is the sum of the depth of the leaves of the subtrees, 
			 *  plus the number of leaves (size) since each leaf defines a path and the depth of each leaf 
			 *  is one greater than the depth of each leaf in the subtree.
			 */
			
			if (this.leaf) {  // base case
				return_sumDepths_numLeaves[0] = 0;
				return_sumDepths_numLeaves[1] = 1;
			}
			else {
				sumDepths_numLeaves_low  = this.lowChild.sumDepths_numLeaves();
				sumDepths_numLeaves_high = this.highChild.sumDepths_numLeaves();
				return_sumDepths_numLeaves[0] = sumDepths_numLeaves_low[0] + sumDepths_numLeaves_high[0] + sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
				return_sumDepths_numLeaves[1] = sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
			}	
			return return_sumDepths_numLeaves;
		}
		
	}

	public Iterator<Datum> iterator() {
		return new KDTreeIterator();
	}
	
	private class KDTreeIterator implements Iterator<Datum> {
		
		//   ADD YOUR CODE BELOW HERE
	// this iterator will try to replicate an iterator that uses a stack, using an arraylist
		ArrayList<Datum> trav = new ArrayList<Datum>();
		
		public KDTreeIterator() {
			fillTree(rootNode);
		}
		
		public void fillTree(KDNode node) {
			if(node.leaf) {
				this.trav.add(node.leafDatum);
			}else {	
				fillTree(node.lowChild);
				fillTree(node.highChild);
			}
		}
		
		public Datum next() {
			return trav.remove(0);
		}

		public boolean hasNext() {
			return !this.trav.isEmpty();
		}
				
		//   ADD YOUR CODE ABOVE HERE

	}

}

