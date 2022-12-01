package org.hepforge.alohep.calc;

import java.util.HashMap;

import org.hepforge.alohep.AloHEP;
import org.hepforge.alohep.gui.ResultPanel;

public class LuminosityCalc {

	private HashMap<String, Particle> particles;  
	
	public static double c = 2.99e8;
	public static double hbar = 6.582119E-16;
	private boolean isRunning = false;
	private boolean isFinished = true;
	private boolean hasCrossingAngle = true;
	private AloHEP alohep;
	
	private Vector3d IPsize;
	private Vector3d IPRes;
	private Vector3d IPDiff;
	private Vector3d IPScale;
	private Vector2d PhaseSpaceScale;
	private Vector2d MPSize;
	private Bunch bunchL;
	private Bunch bunchR;
	private double luminosity = 0;
	
	public LuminosityCalc(AloHEP alohep)
	{
		this.alohep = alohep;
		initParticleInterface();
	}
	
	public void start()
	{
		if(!getCheckbox("simulation"))
		{
			alohep.getMainPanel().getControlPanel().getSettingsPanel().matchedBeamsSelected();
		}
		init();
		if(getCheckbox("simulation"))
		{
			Vector3d sigmaL = bunchL.getSigma();
			Vector3d sigmaR = bunchR.getSigma();
			IPScale = new Vector3d(getSettingsData("IPScale"),getSettingsData("IPScale"),getSettingsData("IPScale"));
			PhaseSpaceScale = new Vector2d(getSettingsData("IPScale"),getSettingsData("IPScale"));
			IPsize = new Vector3d(Math.max(sigmaL.x, sigmaR.x)*IPScale.x*2, Math.max(sigmaL.y, sigmaR.y)*IPScale.y*2, (sigmaL.z+sigmaR.z)*IPScale.z);
			if(getCheckbox("hourglass"))
			{
				double scale = 2*Math.max(bunchL.getSigma().z / bunchL.getBeta().y, bunchR.getSigma().z / bunchR.getBeta().y);
				if(scale > 1)
					IPsize.y*=scale;
			}
			int XRes = (int)getSettingsData("XRes");
			int YRes = (int)getSettingsData("YRes");
			int ZRes = (int)getSettingsData("ZRes");
			
			double chargeDimScaleMP = alohep.getLuminosityCalc().getSettingsData("chargeDimScaleMP");
			IPRes = new Vector3d(XRes, YRes, ZRes);
			IPDiff = IPsize.copy().divide(IPRes);
			MPSize = new Vector2d(IPDiff.x*chargeDimScaleMP, IPDiff.y*chargeDimScaleMP);
			int posZ = (int)((sigmaL.z+sigmaR.z)/2*IPScale.z/IPDiff.z);
			bunchL.setPosZ(-posZ);
			bunchR.setPosZ(posZ);
			bunchL.setVelZ(1.0);
			bunchR.setVelZ(-1.0);
			
			bunchL.initMPs();
			bunchR.initMPs();
	
		}
	}
	public void run()
	{ 
		isFinished = false;
		isRunning = true;
		luminosity = 0;

		if(getCheckbox("simulation"))
		{
			for(int z = 0; z < IPRes.z*2;z++)
			{
				if(!isRunning)
				{
					isFinished = true;
					return;
				}
				update();
				bunchR.updatePosZ(-1);
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    alohep.getMainPanel().getPbar().setValue((int)(z*1.0/(IPRes.z*2)*100));
			   
			}

		}
		
		ResultPanel resultPanel = new ResultPanel(alohep);
		resultPanel.finalResults();
		isFinished = true;
	}
	public void init()
	{
		bunchL = new Bunch(alohep, new Beam(alohep, AloHEP.PanelType.LEFT));
		bunchR = new Bunch(alohep, new Beam(alohep, AloHEP.PanelType.RIGHT));
		bunchL.init(-getSettingsData("crossingAngle"));
		bunchR.init(getSettingsData("crossingAngle"));

	}

	public void update()
	{
			double dt = IPDiff.z/2;
			double sign = -Math.signum(bunchL.getParticle().getCharge()*bunchR.getParticle().getCharge());
			double lowerLimit = Math.max(bunchL.minSliceIndex() , bunchR.minSliceIndex());
			double upperLimit = Math.min(bunchL.maxSliceIndex() , bunchR.maxSliceIndex());

			for (double  i=lowerLimit; i<upperLimit ; i++) 
			{
				int indexL = (int)(i-bunchL.minSliceIndex());
				int indexR = (int)(i-bunchR.minSliceIndex());

				Slice sliceL = bunchL.getSlice()[indexL];
				Slice sliceR = bunchR.getSlice()[indexR];

				sliceL.updateQ();
				sliceR.updateQ();
				sliceL.updateFi();
				sliceR.updateFi();
				sliceL.updateForce();
				sliceR.updateForce();

				luminosity+=sliceL.getCollision(sliceR);
				if(getCheckbox("pinch"))
				{
					for(int k = 0; k < sliceL.getMP().size(); k++)
					{
						MacroParticle MP = sliceL.getMP().get(k);
						Vector2d force = sliceR.getForce(MP.getPos());
						
						double accX = sign*bunchL.getForceConstant()/MP.getGamma()*force.x;
						double accY = sign*bunchL.getForceConstant()/MP.getGamma()*force.y;
						
						MP.getAcc().add(new Vector3d(accX, accY, 0));
					}
					for(int k = 0; k < sliceR.getMP().size(); k++)
					{
						MacroParticle MP = sliceR.getMP().get(k);
						Vector2d force = sliceL.getForce(MP.getPos());
						
						double accX = sign*bunchR.getForceConstant()/MP.getGamma()*force.x;
						double accY = sign*bunchR.getForceConstant()/MP.getGamma()*force.y;
						MP.getAcc().add(new Vector3d(accX, accY, 0));
					}
				}
			}
		bunchL.update(dt);
		bunchR.update(dt);
	}
	
	public double getLuminosity()
	{
		double NL = bunchL.getData("NumParInBun");
		double NR = bunchR.getData("NumParInBun");
		double ltL = bunchL.getParticle().getLifetime();
		double ltR = bunchR.getParticle().getLifetime();
		double circumL = bunchL.hasData("Circum") ? bunchL.getData("Circum") : 1;
		double circumR = bunchR.hasData("Circum") ? bunchR.getData("Circum") : 1;
		
		double dltL = bunchL.getGamma() * bunchL.getParticle().getLifetime();
		double dltR = bunchR.getGamma() * bunchR.getParticle().getLifetime();

		double timeL = ltL; //muons are colllide until of their lifetime
		double timeR = ltR;
		
		double delayedtimeL = timeL*bunchL.getGamma(); 
		double delayedtimeR = timeR*bunchR.getGamma();

		double circulationCountL = bunchL.hasData("Turn") ? bunchL.getData("Turn") : delayedtimeL*c/1000/circumL;
		double circulationCountR = bunchR.hasData("Turn") ? bunchR.getData("Turn") : delayedtimeR*c/1000/circumR;
		double tTimeL = circumL * 1000 * circulationCountL / c;
		double tTimeR = circumR * 1000 * circulationCountR / c;
		double tTime = Math.min(tTimeL, tTimeR);
		
		double Nsquare = NL * NR;
		
		if(getCheckbox("decay"))
		{
			if(ltR != -1 && ltL != -1)
			{
				Nsquare = NL * NR * dltL * dltR / (dltL + dltR) * (-Math.pow(Math.E, tTime * -(1 / dltL + 1 / dltR)) + 1) /tTime;
			}
			else if(ltL != -1)
			{
				NL = NL * dltL * (-Math.pow(Math.E, -tTimeL / dltL) + 1) / tTimeL; 
				
				Nsquare = NL * NR;
			}
			else if(ltR != -1)
			{
				NR = NR * dltR * (-Math.pow(Math.E, -tTimeR / dltR) + 1) / tTimeR; 
				Nsquare = NL * NR;
			}
	
		}
		
		double fc = Math.min(getFCollision(bunchL), getFCollision(bunchR));

		Vector3d sigmaL = bunchL.getSigma();
		Vector3d sigmaR = bunchR.getSigma();

		double dutyFacR = bunchR.hasData("DutyFac") ? bunchR.getData("DutyFac") : 1;
		double dutyFacL = bunchL.hasData("DutyFac") ? bunchL.getData("DutyFac") : 1;

		if(getCheckbox("simulation"))
		{	
			double numMacPar = getSettingsData("NumMacPar");
			return 2 * fc * Nsquare * (IPDiff.z/2) / (IPDiff.x *IPDiff.y * IPDiff.z * numMacPar * numMacPar) * luminosity * Math.min(dutyFacR, dutyFacL)*1e-4;
		}
		return fc * Nsquare / (4 * Math.PI * Math.max(sigmaL.x, sigmaR.x) * Math.max(sigmaL.y, sigmaR.y)) * 1e-4 *Math.min(dutyFacR, dutyFacL);
	}
	public double getFCollision(Bunch bunch)
	{
		double fc = 0;
		if(bunch.hasData("ColFrq"))
		{
			fc = bunch.getData("ColFrq");
			return fc;
		}
		else if(bunch.hasData("RevFrq"))
		{
			fc = bunch.getData("RevFrq");
		}
		else if(bunch.hasData("PulFrq"))
		{
			fc = bunch.getData("PulFrq");
		}
		else if(bunch.hasData("RepFrq"))
		{
			double circulationCount = 0;
			if(bunch.hasData("Turn"))
				circulationCount = bunch.getData("Turn");
			else
			{
				double value = bunch.getParticle().getLifetime();
				double delayedtime = value*bunch.getGamma();
				circulationCount = delayedtime*c/1000/bunch.getData("Circum");
			}
			fc = bunch.getData("RepFrq") * circulationCount;
			System.out.println(circulationCount);
		}
		else if(bunch.hasData("Circum"))
		{
			fc = c/1000/bunch.getData("Circum");
		}
		

		fc *= bunch.getData("NumBunInBeam");
		
		return fc;
	}
	public double getLuminosityRaw()
	{

		double fc = Math.min(getFCollision(bunchL), getFCollision(bunchR));	
		double NL = bunchL.getData("NumParInBun");
		double NR = bunchR.getData("NumParInBun");
		Vector3d sigmaL = bunchL.getSigma();
		Vector3d sigmaR = bunchR.getSigma();
		return fc * NL * NR / (4 * Math.PI * Math.max(sigmaL.x, sigmaR.x) * Math.max(sigmaL.y, sigmaR.y)) * 1e-4;
	}
	
	public double[] getDivergence(Bunch B) {
		
		double [] A = new double [2];
		A[0]=0.000;
		A[1]=0.000;

		A[0] = B.getSigma().z / B.getData("BetaHor") ;
		A[1] = B.getSigma().z / B.getData("BetaVer") ;
		return A;
	}
	
	public double [] getDisruption(Bunch A, Bunch B) {
		double []  D = new double[2];
		D[0]=0;
		D[1]=0;
		
			double gamma = B.getGamma();
			double Z     = Math.abs(A.getParticle().getCharge());
			double N     = A.getNumberP();
			double rad   = B.getParticle().getRad(); 
			Vector3d sigma = A.getSigma();
			
			D[0] = (2 * Z * N * rad * (2 * sigma.z)) / ( gamma * sigma.x * (sigma.x + sigma.y) ); //sigmaZ = 2 * sigma.z 
			D[1] = (2 * Z * N * rad * (2 * sigma.z)) / ( gamma * sigma.y * (sigma.x + sigma.y) ); 

		return D;	
	}
	
	
	public double [] getBeamBeam(Bunch A, Bunch B) {
		double []  BB = new double[2];
		BB[0]=0;
		BB[1]=0;

			double gamma = B.getGamma();
			double Z     = Math.abs(A.getParticle().getCharge());
			double N     = A.getData("NumParInBun");
			double rad   = B.getParticle().getRad(); 
			double betaStarVer = B.getData("BetaVer");
			double betaStarHor = B.getData("BetaHor");
			double betaStar = Math.sqrt(betaStarHor * betaStarVer);
			Vector3d sigma = A.getSigma();
			
			BB[0] = (Z * N * rad *  betaStar) / (2 * Math.PI * gamma * sigma.x * (sigma.x+sigma.y));
			BB[1] = (Z * N * rad *  betaStar) / (2 * Math.PI * gamma * sigma.y * (sigma.x+sigma.y));
			
		return BB;
	}
	
	public void initParticleInterface()
	{
		particles = new HashMap<String, Particle>();
		particles.put("positron", new Particle("positron", 1.0, 0.510998950e6, -1));
		particles.put("electron", new Particle("electron", -1.0, 0.510998950e6, -1));
		particles.put("proton", new Particle("proton", 1.0, 938.28e6, -1));
		particles.put("muon", new Particle("muon", -1.0, 105.658e6, 2.197e-6));
		particles.put("muon+", new Particle("muon+", 1.0, 105.658e6, 2.197e-6));
		particles.put("Pb", new Particle("Pb", +82.0, 207.2*938.28e6, -1)); //A: 207.2  
		particles.put("Au", new Particle("Au", +79.0, 197*938.28e6, -1)); //A: 197  
		
	}
	public double getSettingsData(String name)
	{
		return alohep.getMainPanel().getControlPanel().getSettingsPanel().getSettingsVarPanels().get(name).getValue();
	}
	public boolean getCheckbox(String name)
	{
		return alohep.getMainPanel().getControlPanel().getCheckPanel().getCheckboxes().get(name).isSelected();
	}
	public HashMap<String, Particle> getParticles() {
		return particles;
	}
	
	public void setParticles(HashMap<String, Particle> particles) {
		this.particles = particles;
	}
	public double getSqrtS()
	{
		return 2*Math.sqrt(bunchL.getData("EnBeam")*bunchR.getData("EnBeam"));
	}
	public Bunch getBunchL() {
		return bunchL;
	}

	public void setBunchL(Bunch bunchL) {
		this.bunchL = bunchL;
	}

	public Bunch getBunchR() {
		return bunchR;
	}

	public void setBunchR(Bunch bunchR) {
		this.bunchR = bunchR;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Vector3d getIPsize() {
		return IPsize;
	}

	public void setIPsize(Vector3d iPsize) {
		IPsize = iPsize;
	}

	public Vector3d getIPRes() {
		return IPRes;
	}

	public void setIPRes(Vector3d iPRes) {
		IPRes = iPRes;
	}

	public Vector3d getIPDiff() {
		return IPDiff;
	}

	public void setIPDiff(Vector3d iPDiff) {
		IPDiff = iPDiff;
	}

	public Vector3d getIPScale() {
		return IPScale;
	}

	public void setIPScale(Vector3d iPScale) {
		IPScale = iPScale;
	}

	public Vector2d getMPSize() {
		return MPSize;
	}

	public void setMPSize(Vector2d mPSize) {
		MPSize = mPSize;
	}
	
	public Vector2d getPhaseSpaceScale() {
		return PhaseSpaceScale;
	}

	public void setPhaseSpaceScale(Vector2d phaseSpaceScale) {
		PhaseSpaceScale = phaseSpaceScale;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public boolean hasCrossingAngle() {
		return hasCrossingAngle;
	}

	public void setCrossingAngle(boolean hasCrossingAngle) {
		this.hasCrossingAngle = hasCrossingAngle;
	}

}
