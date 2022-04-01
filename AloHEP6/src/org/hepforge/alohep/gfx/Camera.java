package org.hepforge.alohep.gfx;

import java.awt.Point;

import org.hepforge.alohep.AloHEP;
import org.hepforge.alohep.calc.LuminosityCalc;
import org.hepforge.alohep.calc.Vector3d;

public class Camera {

	private Vector3d scale;
	private double scaleFactor = 1;
	private Point center;
	private Axis axis = Axis.X;
	public static enum Axis{X, Y, Z}
	
	public Camera(AloHEP alohep)
	{

		scale = Vector3d.unitVec.copy().divide(alohep.getLuminosityCalc().getIPsize()).multiply(0.5);
		scale.multiply(new Vector3d(0.5,0.5,1.5));
		center = new Point(0,0);
	}
	public Point getCameraPos(Vector3d pos)
	{
		int x = 0, y = 0;

		switch(axis)
		{
		case X:
			x = center.x+(int)((pos.z*scale.z*scaleFactor+0.5)*Display.height+(Display.width-Display.height)/2);
			y = center.y+(int)((pos.y*scale.y*scaleFactor+0.5)*Display.height);
			break;
		case Y:
			x = center.x+(int)((pos.z*scale.z*scaleFactor+0.5)*Display.height+(Display.width-Display.height)/2);
			y = center.y+(int)((pos.x*scale.x*scaleFactor+0.5)*Display.height);

			break;
		case Z:
			x = center.x+(int)((pos.x*scale.x*scaleFactor+0.5)*Display.height+(Display.width-Display.height)/2);
			y = center.y+(int)((pos.y*scale.y*scaleFactor+0.5)*Display.height);
			break;
		}
		
		return new Point(x, y);
	}

	public Axis getAxis() {
		return axis;
	}
	public void setAxis(Axis axis) {
		this.axis = axis;
	}
	public Vector3d getScale() {
		return scale;
	}
	public void scaleChange(double k)
	{
		scaleFactor*=k;
	}
	public void setScale(Vector3d scale) {
		this.scale = scale;
	}
	public double getScaleFactor() {
		return scaleFactor;
	}
	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center.x = center.x;
		this.center.y = center.y;
	}
	
}
