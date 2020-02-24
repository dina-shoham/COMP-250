public class Settler extends Unit {
	public Settler(Tile pos, double hp, String faction) {
		super(pos, hp, 2, faction);
	}
		
	public void takeAction(Tile t) {
		if(this.getPosition() == t && t.isCity() == false) {	// if unit is stationed on t and there is no city on t. case settler to unit
			t.foundCity();	// build a city
			t.removeUnit(this);	// remove unit from tile
		}
		return;	// exit
	}
	
	public boolean equals(Object o) {
		if(o instanceof Settler) {
			return super.equals(o);	// SUPER IS CONFUSING AND I DOUBT I USED IT CORRECTLY
		}
		return false;
	}
}
