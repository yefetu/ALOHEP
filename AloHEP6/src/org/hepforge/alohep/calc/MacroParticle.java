package org.hepforge.alohep.calc;

public class MacroParticle {

	private Vector3d pos;
	private Vector3d vel;
	private Vector3d acc;
	private double gamma;
	public MacroParticle(Vector3d pos, double gamma)
	{
		this.setPos(pos);
		vel = new Vector3d();
		acc = new Vector3d();
		this.gamma = gamma;
	}

	public void update(double dt)
	{
		vel.add(acc.copy().multiply(dt));
		pos.add(vel.copy().multiply(dt));
		acc.set(0,0,0);
	}
	public Vector3d getPos() {
		return pos;
	}

	public void setPos(Vector3d pos) {
		this.pos = pos;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public Vector3d getVel() {
		return vel;
	}

	public void setVel(Vector3d vel) {
		this.vel = vel;
	}

	public Vector3d getAcc() {
		return acc;
	}

	public void setAcc(Vector3d acc) {
		this.acc = acc;
	}
}
