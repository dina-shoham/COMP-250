public class Warrior extends MilitaryUnit{
	
	public Warrior(Tile pos, double hp, String faction) {
		super(pos, hp, 1, faction, 20.0, 1, 25);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Warrior) {
			return super.equals(o);	// SUPER IS CONFUSING AND I DOUBT I USED IT CORRECTLY
		}
		return false;
	}
}
