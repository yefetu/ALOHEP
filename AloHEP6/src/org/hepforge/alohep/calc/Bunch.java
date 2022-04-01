package org.hepforge.alohep.calc;

import java.util.ArrayList;
import java.util.Random;

import org.hepforge.alohep.AloHEP;

public class Bunch {


	private AloHEP alohep;
	private Beam beam;
	private Slice slice[];
	private Vector3d sigma;
	private Vector2d emmitance;
	private Vector2d beta;
	private double gamma;
	private double velZ;
	private ArrayList<MacroParticle> MPs;
	
	// number of particle in Bunch
	private double numberP;

	
	//
	private int posZ;
	private double forceConstant;
	
		
	public Bunch(AloHEP alohep, Beam beam)
	{
		this.alohep = alohep;
		this.beam = beam;
	}
	
	public void init()
	{
		
		emmitance = new Vector2d(0, 0);
		numberP = getData("NumParInBun");
		
		beta = new Vector2d(getData("BetaHor"), getData("BetaVer"));
		gamma = getData("EnBeam") * 1.0e9 / (beam.getParticle().getMass());
		emmitance.x = getData("EmNorHor") / gamma;
		emmitance.y = getData("EmNorVer") / gamma;
		double sigmaX = Math.sqrt(emmitance.x * beta.x);
		double sigmaY = Math.sqrt(emmitance.y * beta.y);
		sigma = new Vector3d(sigmaX, sigmaY, getData("BunLen") / 2);
	}

	public void initMPs()
	{
		Vector3d IPScale = alohep.getLuminosityCalc().getIPScale();
		Vector3d IPDiff = alohep.getLuminosityCalc().getIPDiff();

		MPs = new ArrayList<MacroParticle>();
		int MPcount = (int)alohep.getLuminosityCalc().getSettingsData("NumMacPar");

		int sliceSize = (int)(sigma.z*IPScale.z/IPDiff.z)*2+2;
		slice = new Slice[sliceSize];
		for(int i = 0 ; i < sliceSize; i++)
		{
			slice[i] = new Slice(alohep, i-sliceSize/2);
		}
		Random rand = new Random();
		for(int i = 0; i < MPcount; i++)
		{
			Vector3d pos = new Vector3d();
			int index;
			
			do {
				pos.x = rand.nextGaussian();
			} while(Math.abs(pos.x) >= IPScale.x);

			do {
				pos.y = rand.nextGaussian();
			}while(Math.abs(pos.y) >= IPScale.y);
			
			do {
				pos.z = rand.nextGaussian();
				
			}while(Math.abs(pos.z) >= IPScale.z);
			if(!alohep.getLuminosityCalc().isHourglass())
			{
				pos.multiply(sigma);
			}
			else
			{
				double s = posZ*IPDiff.z+pos.z*sigma.z;
				Vector2d sigma = new Vector2d(this.sigma).pow(2).multiply(beta.copy().pow(-2).multiply(s*s).add(1)).pow(0.5);
				pos.multiply(new Vector3d(sigma.x, sigma.y, this.sigma.z));
			}
			index = (int)(pos.z/IPDiff.z+sliceSize/2);


			pos.add(new Vector3d(0, 0, posZ*IPDiff.z));
			MacroParticle MP = new MacroParticle(pos, gamma);
			MP.setVel(new Vector3d(0,0,velZ));
			slice[index].addMP(MP);
			MPs.add(MP);

		}
		forceConstant = -4*getParticle().getRad()*numberP/(MPcount*IPDiff.z);
	}

	public void update(double dt)
	{
		for(MacroParticle MP: MPs)
		{
			MP.update(dt);
		}
	}
	public ArrayList<MacroParticle> getMPs()
	{
		return MPs;
	}
	public double minSliceIndex()
	{
		return posZ-slice.length/2;
	}
	public double maxSliceIndex()
	{
		return posZ+slice.length/2;
	}
	public double getSettings(String key)
	{
		return alohep.getDataManager().getSettingsData().get(key);
	}
	public void updatePosZ(int z)
	{
		posZ +=z;
	}
	public double getData(String key)
	{
		return beam.getData(key);
	}
	
	public Vector3d getSigma()
	{
		return sigma;
	}
	
	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public Vector2d getEmmitance() {
		return emmitance;
	}

	public void setEmmitance(Vector2d emmitance) {
		this.emmitance = emmitance;
	}

	public Vector2d getBeta() {
		return beta;
	}

	public void setBeta(Vector2d beta) {
		this.beta = beta;
	}

	public Beam getBeam() {
		return beam;
	}

	public void setBeam(Beam beam) {
		this.beam = beam;
	}

	public Slice[] getSlice() {
		return slice;
	}

	public void setSlice(Slice[] slice) {
		this.slice = slice;
	}

	public void setSigma(Vector3d sigma) {
		this.sigma = sigma;
	}

	public int getPosZ() {
		return posZ;
	}

	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}

	public Particle getParticle() {
		
		return beam.getParticle();
	}

	public boolean hasData(String str) {
		return beam.hasData(str);
	}

	public double getNumberP() {
		return numberP;
	}

	public void setNumberP(double numberP) {
		this.numberP = numberP;
	}

	public double getForceConstant() {
		return forceConstant;
	}

	public void setForceConstant(double forceConstant) {
		this.forceConstant = forceConstant;
	}

	public double getVelZ() {
		return velZ;
	}

	public void setVelZ(double velZ) {
		this.velZ = velZ;
	}
}
