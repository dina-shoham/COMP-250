public class Tile {
	private int x;	// x coordinate
	private int y;	// y coordinate
	private boolean city_built;	// whether a city has been built on the tile
	private boolean improved;	// whether improvements have been made on the tile
	private ListOfUnits list;	// list of units on the tile
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.city_built = false;
		this.improved = false;
		this.list = new ListOfUnits();
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean isCity() {
		return this.city_built;
	}
	
	public boolean isImproved() {
		return this.improved;
	}
	
	public void foundCity() {
		this.city_built = true;
	}
	
	public void buildImprovement() {
		this.improved = true;
	}
	
	public boolean addUnit(Unit u) {	// adds u to list
		String fac = u.getFaction();
		
		if(u instanceof MilitaryUnit) {	// checking for military units
			for(int i=0; i<list.size(); i++) {
				if (!fac.equals(list.get(i).getFaction())) {	// if they are different factions
					return false;
				}
			}
			this.list.add(u);
			return true;
		}
		else {
			this.list.add(u);
			return true;
		}
	}			
		
	public boolean removeUnit(Unit u) {
		Unit[] units = this.list.getUnits();
		
		for(int i=0; i<list.size(); i++) {
			if(units[i] == u) {
				this.list.remove(units[i]);
				return true;
			}
		}
		return false;
	}
		
	public Unit selectWeakEnemy(String faction) {
		Unit[] units = list.getUnits();	// make a temporary array for comparison purposes
		Unit weakEnemy = null;	// initializing weakEnemy as null so that if there are no units from a diff faction, method will return null
		
		for(int i=0; i<list.size(); i++) {
			if (!(faction.equals(units[i].getFaction()))) {	// if they are different factions
				weakEnemy = units[i];
				break;
				//weakEnemy = units[i];	// reinitializing weakEnemy
			}
		}
		for(int i=0; i<list.size(); i++) {
			if((units[i].getHP() < weakEnemy.getHP()) && (!(faction.equals(units[i].getFaction())))) {
				weakEnemy = units[i];	// updating weakEnemy to be the unit with lower hp
			}
		}
		return weakEnemy;
	}
	
	public static double getDistance(Tile t1, Tile t2) {	// gets distance between two tiles
		int x1 = t1.x;
		int x2 = t2.x;
		int y1 = t1.y;
		int y2 = t2.y;
		double distance = Math.sqrt((x1-x2)*(x1-x2) - (y1-y2)*(y1-y2));
		return distance;
	}
	
}
