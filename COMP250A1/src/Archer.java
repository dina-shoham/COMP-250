public class Archer extends MilitaryUnit{
	private int arrows;
	
	public Archer(Tile pos, double hp, String faction) {
		super(pos, hp, 2, faction, 15.0, 2, 0);
		this.arrows = 5;
	}
	
	public void takeAction(Tile t) {
		if(arrows == 0) {	// <= OR == ????
			arrows = 5;	// reset arrows to 5
			return;	// exit (can't attack)
		}
		else {
			arrows -= 1;
			super.takeAction(t);
		}
	}
	
	public boolean equals(Object o) {
		if(o instanceof Archer) {
			return (super.equals(o) && ((Archer)o).arrows == this.arrows);	// STILL KINDA CONFUSED BY SUPER BUT I THINK WE'RE SUPPOSED TO USE IT HERE
		}
		return false;
	}

}
