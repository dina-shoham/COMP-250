public abstract class Unit {
	private Tile pos; // position of the unit
	private double hp; // health points of the unit
	private final int range; // range of the unit
	private String faction; // faction to which the unit belongs
		
	public Unit(Tile pos, double hp, int range, String faction) { // constructor
		this.pos = pos;
		this.hp = hp;
		this.range = range;
		this.faction = faction;	
	
		if(!this.pos.addUnit(this)) {
			throw new IllegalArgumentException("you done fucked up");
		}
	}
		
	public final Tile getPosition() {
		return this.pos;
	}

	public final double getHP() {
		return this.hp;
	}
	
	public final String getFaction() {
		return this.faction;
	}
	
	public boolean moveTo(Tile target_tile) {
		if(Tile.getDistance(target_tile, this.pos) >= (this.range + 1) ) {	// if distance b/w tiles is out of range
			return false;
			}
		else if(!target_tile.addUnit(this)) {	// adding this to target tile's list
			return false;	// using method addUnit from Tile class to check if there is an enemy military unit
		}
		this.pos.removeUnit(this);	// remove this from this's ListOfUnits
		this.pos = target_tile;	// updating this position to be the position target_tile
		return true;
	}
	
	public void receiveDamage(double damage) {
		if(this.pos.isCity() == true) {
			this.hp -= damage*0.9;
		}
		else {
			this.hp -= damage;
		}
		
		if(this.hp <= 0) {
			this.pos.removeUnit(this);
		}
	}
	
	public abstract void takeAction(Tile t);
	
	public boolean equals(Object o) {	
		if(o instanceof Unit) {
			return (((Unit)o).getPosition().equals(this.pos) && ((Unit)o).getHP() == this.hp && ((Unit)o).getFaction().equals(this.faction));	
		}
		return false;
	}
	
}
