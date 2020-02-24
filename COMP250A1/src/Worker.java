public class Worker extends Unit {
	private int jobs;
	
	public Worker(Tile pos, double hp, String faction) {	
		super(pos, hp, 2, faction);
		this.jobs = 0;
	}
	
	public void takeAction(Tile t) {
		if (this.getPosition().equals(t) && t.isImproved() == false) {
			t.buildImprovement();
			this.jobs ++;
		}
		if (this.jobs >= 10) {
			t.removeUnit(this);
		}
	}
	
	public boolean equals(Object o) {
		if(o instanceof Worker) {
			return (super.equals(o) && ((Worker)o).jobs == this.jobs);	// STILL KINDA CONFUSED BY SUPER BUT I THINK WE'RE SUPPOSED TO USE IT HERE
		}
		return false;
	}
}