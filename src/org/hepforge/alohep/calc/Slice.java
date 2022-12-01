package org.hepforge.alohep.calc;

import java.util.ArrayList;

import org.hepforge.alohep.AloHEP;

public class Slice {

	private AloHEP alohep;
	private int index;
	private ArrayList<MacroParticle> MP;
	private double Q[][];
	private double fi[][];
	private double Fx[][];
	private double Fy[][];
	private int extraBoundry = 2; 
	public Slice(AloHEP alohep, int index)
	{
		this.alohep = alohep;
		Vector3d res = alohep.getLuminosityCalc().getIPRes();
		this.setIndex(index);
		Q = new double[(int)res.x][(int)res.y];
		fi = new double[(int)res.x+2*extraBoundry+1][(int)res.y+2*extraBoundry+1];
		Fx = new double[(int)res.x+2*extraBoundry][(int)res.y+2*extraBoundry+1];
		Fy = new double[(int)res.x+2*extraBoundry+1][(int)res.y+2*extraBoundry];
		MP = new ArrayList<MacroParticle>();
	}
	
	public void init(double[][] data)
	{
		for(int i = 0; i < data.length; i++)
		{
			for(int j = 0; j < data[0].length; j++)
			{
				data[i][j] = 0;
			}
		}
	}
	public void divideQtoArea(double area)
	{
		for(int i = 0; i < Q.length; i++)
		{
			for(int j = 0; j < Q[0].length; j++)
			{
				Q[i][j] /= area;
			}
		}
	}

	
	public void updateQ()
	{
		Vector2d MPSize = alohep.getLuminosityCalc().getMPSize();
		Vector3d res = alohep.getLuminosityCalc().getIPRes();
		Vector3d diff = alohep.getLuminosityCalc().getIPDiff();
		
		init(Q);
		double centerShiftX = (res.x)*0.5*diff.x;
		double centerShiftY = (res.y)*0.5*diff.y;
		for(int i = 0; i < MP.size(); i++)
		{	
			MacroParticle mp = MP.get(i);
			Vector3d pos = mp.getPos();

			double minX = pos.x+centerShiftX-MPSize.x/2;
			double minY = -pos.y+centerShiftY-MPSize.y/2;
			double maxX = minX+MPSize.x;
			double maxY = minY+MPSize.y;
			
			int mi = (int)((minX)/diff.x);
			int ni = (int)((minY)/diff.y);
			
			double Xi = mi*diff.x;
			double Yi = ni*diff.y;
			int n = ni;
			double Y = Yi;
			double deltaY =  Math.min(maxY, Y+diff.y) - Math.max(minY, Y);
			while(deltaY > 0)
			{
				int m = mi;
				double X = Xi;
				double deltaX = Math.min(maxX, X+diff.x) - Math.max(minX, X);
				while(deltaX > 0)
				{
					if(m < 0 || n < 0 || m >= Q.length || n >= Q[0].length)
					{
						break;
					}
					Q[m][n] += deltaX*deltaY;
					X += diff.x;
					m++;
					deltaX = Math.min(maxX, X+diff.x) - Math.max(minX, X);
				}
				Y += diff.y;
				n++;
				deltaY = Math.min(maxY, Y+diff.y) - Math.max(minY, Y);

			}
		}
		divideQtoArea(MPSize.area());
	}
	
	public void updateFi()
	{
		Vector3d diff = alohep.getLuminosityCalc().getIPDiff();
		double diffX2 = diff.x*diff.x;
		double diffY2 = diff.y*diff.y;
		for(int m = 0; m < fi.length; m++)
		{
			for(int n = 0; n < fi[0].length; n++)
			{
				fi[m][n] = 0;
				for(int i = 0; i < Q.length; i++)
				{
					for(int j = 0; j < Q[0].length; j++)
					{
						fi[m][n] += Math.log(Math.pow(m-i-extraBoundry-0.5, 2)*diffX2+Math.pow(n-j-extraBoundry-0.5, 2)*diffY2)*Q[i][j];
					}
				}
				fi[m][n] = fi[m][n]/2;
			}
		}
	}
	
	public void updateForce()
	{
		
		Vector3d diff = alohep.getLuminosityCalc().getIPDiff();

		for(int m = 0; m < Fx.length; m++)
		{
			for(int n = 0; n < Fx[0].length; n++)
			{
				Fx[m][n] = (fi[m+1][n] - fi[m][n])/diff.x;
			}
		}
		for(int m = 0; m < Fy.length; m++)
		{
			for(int n = 0; n < Fy[0].length; n++)
			{
				Fy[m][n] = (fi[m][n+1] - fi[m][n])/diff.y;
			}
		}
	}
	public Vector2d getForce(Vector3d pos)
	{
		/*Vector3d diff = alohep.getLuminosityCalc().getIPDiff();
		
		double centerShiftX = (Fx.length-1)*0.5*diff.x;
		double centerShiftY = (Fx[0].length-1)*0.5*diff.y;
		double posX = pos.x+centerShiftX;
		double posY = -pos.y+centerShiftY;
		
		int i = (int)(posX/diff.x);
		int j = (int)(posY/diff.y);
		double x = i*diff.x;
		double y = j*diff.y;
		double FX = 0;
		try 
		{
			//FX = LIP(LIP(Fx[i][j],Fx[i+1][j],(posX-x)/diff.x), LIP(Fx[i][j+1],Fx[i+1][j+1],(posX-x)/diff.x),(posY-y)/diff.y);
			FX = getBicubicInterpolation(Fx, i, j, (posX-x)/diff.x, (posY-y)/diff.y);
		}catch(Exception e){}
		
		centerShiftX = (Fy.length-1)*0.5*diff.x;
		centerShiftY = (Fy[0].length-1)*0.5*diff.y;
		posX = pos.x+centerShiftX;
		posY = -pos.y+centerShiftY;
		
		i = (int)(posX/diff.x);
		j = (int)(posY/diff.y);
		x = i*diff.x;
		y = j*diff.y;
		
		double FY = 0;
		try 
		{
			FY = -getBicubicInterpolation(Fy, i, j, (posX-x)/diff.x, (posY-y)/diff.y);

			//FY = LIP(LIP(Fy[i][j],Fy[i][j+1],(posY-y)/diff.y), LIP(Fy[i+1][j],Fy[i+1][j+1],(posY-y)/diff.y),(posX-x)/diff.x);
		}catch(Exception e){}

		return new Vector2d(FX, FY);*/
		double FX = 0,FY = 0;
		try {FX = getInterPolation(pos, Fx);}catch(Exception e){}
		try {FY = -getInterPolation(pos, Fy);}catch(Exception e){}

		return new Vector2d(FX, FY);
	}
	public double getInterPolation(Vector3d pos, double[][] matrix)
	{
		Vector3d diff = alohep.getLuminosityCalc().getIPDiff();
		
		double centerShiftX = (matrix.length-1)*0.5*diff.x;
		double centerShiftY = (matrix[0].length-1)*0.5*diff.y;
		double posX = pos.x+centerShiftX;
		double posY = -pos.y+centerShiftY;
		
		int i = (int)(posX/diff.x);
		int j = (int)(posY/diff.y);
		double x = i*diff.x;
		double y = j*diff.y;

			//return LIP(LIP(matrix[i][j],matrix[i+1][j],(posX-x)/diff.x), LIP(matrix[i][j+1],matrix[i+1][j+1],(posX-x)/diff.x),(posY-y)/diff.y);
		return getBicubicInterpolation(matrix, i, j, (posX-x)/diff.x, (posY-y)/diff.y);
	}
	public double getCollision(Slice slice)
	{
		double coll = 0;
		for(int i = 0; i < Q.length; i++)
		{
			for(int j = 0; j < Q[0].length; j++)
			{
				coll += Q[i][j]*slice.Q[i][j];
			}
		}
		return coll;
	}
	
	public double getCollision(Slice slice, Vector2d factor)
	{
		double coll = 0;
		Vector2d delta = Vector2d.unitVec.copy().divide(factor);
		Vector2d start = new Vector2d(1,1).add(delta);
		Vector2d loc = start.copy();

		while(loc.x < Q.length-2)
		{
			
			int i = (int)loc.x;
			
			while(loc.y < Q[0].length-2)
			{
				int j = (int)loc.y;
				
				coll += getBicubicInterpolation(Q, i, j, loc.x-i, loc.y-j)*getBicubicInterpolation(slice.Q, i, j, loc.x-i, loc.y-j);
				loc.y += delta.y;
			}
			loc.x += delta.x;
			loc.y = start.y;
		}
		return coll*delta.area();
	}
	
	public double getBicubicInterpolation(double[][] F, int i, int j, double x, double y) 
	{
		double[][] p = new double[4][4];
		for(int m = 0; m < 4; m++)
		{
			for(int n = 0; n < 4; n++)
			{
				p[m][n] = F[i+m-1][j+n-1];
			}
		}
		double[] arr = new double[4];

			arr[0] = getCubicInterpolation(p[0], y);
			arr[1] = getCubicInterpolation(p[1], y);
			arr[2] = getCubicInterpolation(p[2], y);
			arr[3] = getCubicInterpolation(p[3], y);
			return getCubicInterpolation(arr, x);
	}
	private double getCubicInterpolation(double[] p, double x) {
	/*	double d1,d2;
		d1 = 0.5*(p[2]-p[0]);
		d2 = 0.5*(p[3]-p[1]);
		double a0 = p[1];
		double a1 = d1;
		double a2 = (3.0*(p[2]-p[1]))-(2.0*d1)-d2;
		double a3 = d1+d2+(2.0*(-p[2]+p[1]));
		return a0+x*(a1+x*(a2+x*a3));*/
		return p[1] + 0.5 * x*(p[2] - p[0] + x*(2.0*p[0] - 5.0*p[1] + 4.0*p[2] - p[3] + x*(3.0*(p[1] - p[2]) + p[3] - p[0])));
		//return (((-0.5*p[0]+1.5*p[1]-1.5*p[2]+0.5*p[3])*x+(p[0]-2.5*p[1]+2*p[2]-0.5*p[3]))*x+(-0.5*p[0]+0.5*p[2])*x)+p[1];
	}
	
	public double LIP(double v1, double v2, double w)
	{
		return v1*w+(1-w)*v2;
	}
	
	public void addMP(MacroParticle mp)
	{
		MP.add(mp);
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public ArrayList<MacroParticle> getMP() {
		return MP;
	}

	public void setMP(ArrayList<MacroParticle> MP) {
		this.MP = MP;
	}
}
