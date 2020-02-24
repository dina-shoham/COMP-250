public abstract class MilitaryUnit extends Unit {
	private double a_damage; // attack damage
	private int a_range; // attack range
	private int armour; // armor of the unit
	
	public MilitaryUnit(Tile pos, double hp, int range, String faction, double a_damage, int a_range, int armour) {
		super(pos, hp, 2, faction);
		this.a_damage = a_damage;
		this.a_range = a_range;
		this.armour = armour;
	}
	
	public void takeAction(Tile t) {
		if(Tile.getDistance(t, this.getPosition()) >= (this.a_range + 1) ) {	// if distance b/w tiles is out of range
			return;	// terminate
		}
		Unit enemy = t.selectWeakEnemy(this.getFaction());
		if(enemy == null) {
			return;
		}
		double damage = this.a_damage;
		if(t.isImproved()) {
			damage *= 1.05;
		}
		enemy.receiveDamage(damage);
	}
	
	public void receiveDamage(double damage) {
		damage *= 100/(100+armour);
		super.receiveDamage(damage);
	}
}
