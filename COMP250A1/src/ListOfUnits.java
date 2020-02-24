public class ListOfUnits {
	private Unit[] units;	// array of Units
	private int num_units;	// number of units in ListOfUnits, initialized to 0
	
	public ListOfUnits() {	// constructor for ListOfUnits
		units = new Unit[10];	// assigning array size 10
		num_units = 0;
	}
	
	public int size() {	// returns the # of units in the array
		return num_units;
	}
	
	public Unit[] getUnits() {	// returns an array w/ size = number of units in this, containing all the units that were in this
		Unit[] tmp_array = new Unit[this.size()];	// creating a new, temporary array with size = number of units in ListOfUnits
		for (int i=0; i<this.size(); i++) {
			tmp_array[i] = this.units[i];	// copying all of the units in units into tmp_array
		}
		return tmp_array;	
	}
	
	public Unit get(int i) {	// returns Unit at position i in array
		if(i<this.size() && i>=0) {	// if i is within range
			return this.units[i];	
		}
		else {
			throw new IndexOutOfBoundsException("index must be positive and within range");	// gives an error if i is out of range
		}
	}

	private int updateCapacity() {	// private method that implements the new capacity formula
		int old_capacity = this.units.length;
		int new_capacity = old_capacity + old_capacity/2 + 1;	// update new_capacity variable
		return new_capacity;
	}
	
	public void add(Unit u) {	// adds unit at the end of a list
		int len = this.units.length;
		
		if(this.size() < len) {	// if there is enough space to add the current Unit
			this.units[this.size()] = u;	// add current unit to position at index = size
			num_units++;
		} else {	// if there isn't enough space
			Unit[] new_units = new Unit[updateCapacity()];	// creating a new array with the correct updated capacity
			for(int i=0; i<this.size(); i++) {
				new_units[i] = this.units[i];	// copying all of the units in array units into new_units
			}
			new_units[this.size()] = u;	// adding u at the end of the array
			this.units = new_units;
			num_units++;
		}
	}
		
	public int indexOf(Unit u) {	// returns the index of the first occurrence of u
		for(int i=0; i<this.size(); i++) {
			if(this.units[i].equals(u)) {	// IS IT CHILL TO USE EQUALS() HERE???
				return i;
			}	// NOT SURE HOW TO ONLY RETURN THE FIRST INSTANCE OF u
		} 
		return -1;
	}
	
	public boolean remove(Unit u) {	// removes first instance of u from units[]
		int pos = indexOf(u);
		if(pos == -1) {
			return false;
		}
		for (int i=pos; i<this.size()-1; i++) {
			this.units[i] = this.units[i+1];
		}		
		num_units--;
		return true;
	}
	
	public MilitaryUnit[] getArmy() {	// returns an array containing all military units in an array
		int m_count = 0;	// new variable to count number of military units
		MilitaryUnit[] army; 	// array w all military units
		for(int i=0; i<num_units; i++) {	// iterating thru array
			if(this.units[i] instanceof MilitaryUnit) {	// checks if it's a military unit
				m_count ++;	// increases count of military units
			}
		}
		army = new MilitaryUnit[m_count];	// initializing new array
		int place = 0; // index in army ***
		for(int i=0; i<num_units; i++) {
			if(this.units[i] instanceof MilitaryUnit) {
				army[place] = (MilitaryUnit) units[i];	// pushing all military units into new array
				place++;
			}
		}
		return army;
	}
	
}
