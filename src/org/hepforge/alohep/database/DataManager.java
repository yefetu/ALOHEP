package org.hepforge.alohep.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class DataManager {

	private LinkedHashMap<String, VariableData> variableDataMap;
	private LinkedHashMap <String, Double> settingsData;
	private HashMap<String, ParticleData> particleDataMap;
	private String save = "save";
	public DataManager()
	{
		variableDataMap = new LinkedHashMap<String, VariableData>();
		settingsData = new LinkedHashMap<String, Double>();
		particleDataMap = new HashMap<String, ParticleData>();
		
		loadVariableData();
		//loadSettingsData();
		//loadParticlesData();
		try {
			loadFromJSON();
			loadSettingsFromJSON();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadParticlesData()
	{
		
		AcceleratorData ad;
		ParticleData pd;
		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e10);
		ad.put("EnBeam", 125.0);
		ad.put("BetaVer", 0.04e-2);
		ad.put("BetaHor", 1.3e-2);
		ad.put("EmNorVer", 0.035e-6);
		ad.put("EmNorHor", 1e-5);
		ad.put("ColFrq", 6560.0);
		ad.put("BunLen", 0.03e-2);
		pd.put("ILC-125", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e10);
		ad.put("EnBeam", 250.0);
		ad.put("BetaVer", 4.8e-4);
		ad.put("BetaHor", 1.1e-2);
		ad.put("EmNorVer", 3.5e-8);
		ad.put("EmNorHor", 1.0e-5);
		ad.put("PulFrq", 5.0);
		ad.put("NumBunInBeam", 1312.0);
		ad.put("BunLen", 3e-4);
		pd.put("ILC-250", ad);

		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.74e10);
		ad.put("EnBeam", 500.0);
		ad.put("BetaVer", 2.3e-4);
		ad.put("BetaHor", 1.1e-2);
		ad.put("EmNorVer", 3.0e-8);
		ad.put("EmNorHor", 1.0e-5);
		ad.put("PulFrq", 4.0);
		ad.put("NumBunInBeam", 2450.0);
		ad.put("BunLen", 2.25e-4);
		pd.put("ILC-500", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 3.72e9);
		ad.put("EnBeam", 1500.0);
		ad.put("BetaVer", 6.8e-5);
		ad.put("BetaHor", 6.9e-3);
		ad.put("EmNorVer", 2e-8);
		ad.put("EmNorHor", 6.6e-7);
		ad.put("PulFrq", 50.0);
		ad.put("NumBunInBeam", 312.0);
		ad.put("BunLen", 4.4e-5);
		pd.put("CLIC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 0.5e10);
		ad.put("EnBeam", 125.0);
		ad.put("BetaVer", 0.03e-2);
		ad.put("BetaHor", 25e-2);
		ad.put("EmNorVer", 0.035e-6);
		ad.put("EmNorHor", 20e-6);
		ad.put("ColFrq", 2e8);
		ad.put("BunLen", 0.03e-2);
		ad.put("DutyFac", 0.33);
		pd.put("ERLC(Telnov)", ad);

		ad = new AcceleratorData();
		ad.put("NumParInBun", 2.5e10);
		ad.put("EnBeam", 125.0);
		ad.put("BetaVer", 0.03e-2);
		ad.put("BetaHor", 25e-2);
		ad.put("EmNorVer", 0.035e-6);
		ad.put("EmNorHor", 20e-6);
		ad.put("ColFrq", 4e7);
		ad.put("BunLen", 0.03e-2);
		ad.put("DutyFac", 0.33);
		pd.put("ERLC(upgraded)", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1e10);
		ad.put("EnBeam", 5000.0);
		ad.put("BetaVer", 9.9e-5);
		ad.put("BetaHor", 11e-3);
		ad.put("EmNorVer", 3.5e-8);
		ad.put("EmNorHor", 1.0e-5);
		ad.put("PulFrq", 5.0e3);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 2e-5);
		pd.put("PWFA", ad);
		
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2.3e10);
		ad.put("EnBeam", 1.0);
		ad.put("BetaVer", 0.5e-2);
		ad.put("BetaHor", 0.5e-2);
		ad.put("EmNorVer", 7.57e-6);
		ad.put("EmNorHor", 7.57e-6);
		ad.put("BunLen", 0.5e-2);
		ad.put("ColFrq", 500e6);
		pd.put("TAC", ad);
		
		
		pd.setLinear();
		particleDataMap.put("electron-linac", pd);
		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e10);
		ad.put("EnBeam", 125.0);
		ad.put("BetaVer", 0.04e-2);
		ad.put("BetaHor", 1.3e-2);
		ad.put("EmNorVer", 0.035e-6);
		ad.put("EmNorHor", 1e-5);
		ad.put("ColFrq", 6560.0);
		ad.put("BunLen", 0.03e-2);
		pd.put("ILC-125", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e10);
		ad.put("EnBeam", 250.0);
		ad.put("BetaVer", 4.8e-4);
		ad.put("BetaHor", 1.1e-2);
		ad.put("EmNorVer", 3.5e-8);
		ad.put("EmNorHor", 1.0e-5);
		ad.put("PulFrq", 5.0);
		ad.put("NumBunInBeam", 1312.0);
		ad.put("BunLen", 3e-4);
		pd.put("ILC-250", ad);

		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.74e10);
		ad.put("EnBeam", 500.0);
		ad.put("BetaVer", 2.3e-4);
		ad.put("BetaHor", 1.1e-2);
		ad.put("EmNorVer", 3.0e-8);
		ad.put("EmNorHor", 1.0e-5);
		ad.put("PulFrq", 4.0);
		ad.put("NumBunInBeam", 2450.0);
		ad.put("BunLen", 2.25e-4);
		pd.put("ILC-500", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 3.72e9);
		ad.put("EnBeam", 1500.0);
		ad.put("BetaVer", 6.8e-5);
		ad.put("BetaHor", 6.9e-3);
		ad.put("EmNorVer", 2e-8);
		ad.put("EmNorHor", 6.6e-7);
		ad.put("PulFrq", 50.0);
		ad.put("NumBunInBeam", 312.0);
		ad.put("BunLen", 4.4e-5);
		pd.put("CLIC", ad);
	
		ad = new AcceleratorData();
		ad.put("NumParInBun", 0.5e10);
		ad.put("EnBeam", 125.0);
		ad.put("BetaVer", 0.03e-2);
		ad.put("BetaHor", 25e-2);
		ad.put("EmNorVer", 0.035e-6);
		ad.put("EmNorHor", 20e-6);
		ad.put("ColFrq", 2e8);
		ad.put("BunLen", 0.03e-2);
		ad.put("DutyFac", 0.33);
		pd.put("ERLC(Telnov)", ad);

		ad = new AcceleratorData();
		ad.put("NumParInBun", 2.5e10);
		ad.put("EnBeam", 125.0);
		ad.put("BetaVer", 0.03e-2);
		ad.put("BetaHor", 25e-2);
		ad.put("EmNorVer", 0.035e-6);
		ad.put("EmNorHor", 20e-6);
		ad.put("ColFrq", 4e7);
		ad.put("BunLen", 0.03e-2);
		ad.put("DutyFac", 0.33);
		pd.put("ERLC(upgraded)", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1e10);
		ad.put("EnBeam", 5000.0);
		ad.put("BetaVer", 9.9e-5);
		ad.put("BetaHor", 11e-3);
		ad.put("EmNorVer", 3.5e-8);
		ad.put("EmNorHor", 1.0e-5);
		ad.put("PulFrq", 5.0e3);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 2e-5);
		pd.put("PWFA", ad);
		
		pd.setLinear();
		particleDataMap.put("positron-linac", pd);
		
		pd = new ParticleData();

		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.8e10);
		ad.put("EnBeam", 3.56);
		ad.put("BetaVer", 0.5e-2);
		ad.put("BetaHor", 0.5e-2);
		ad.put("EmNorVer", 27e-6);
		ad.put("EmNorHor", 27e-6);
		ad.put("BunLen", 0.5e-2);
		ad.put("ColFrq", 500e6);
		pd.put("TAC", ad);
		
		particleDataMap.put("positron-ring", pd);
		
		pd = new ParticleData();

		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.8e10);
		ad.put("EnBeam", 3.56);
		ad.put("BetaVer", 0.5e-2);
		ad.put("BetaHor", 0.5e-2);
		ad.put("EmNorVer", 27e-6);
		ad.put("EmNorHor", 27e-6);
		ad.put("BunLen", 0.5e-2);
		ad.put("ColFrq", 500e6);
		pd.put("TAC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 6.2e10);
		ad.put("EnBeam", 18.0); //mass of au
		ad.put("BetaVer", 196e-2);
		ad.put("BetaHor", 41e-2);
		ad.put("EmNorVer", 705e-6);
		ad.put("EmNorHor", 20e-6);
		ad.put("RevFrq", 77986.0); // 3e8/3.834e3
		ad.put("NumBunInBeam", 290.0);
		ad.put("BunLen", 0.9e-2);
		ad.put("Circum", 3.834);
		pd.put("EIC", ad);
		
		particleDataMap.put("electron-ring", pd);
		
		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.15e11);
		ad.put("EnBeam", 7000.0);
		ad.put("BetaVer", 0.55);
		ad.put("BetaHor", 0.55);
		ad.put("EmNorVer", 3.75e-6);
		ad.put("EmNorHor", 3.75e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 2808.0);
		ad.put("BunLen", 7.7e-2);
		ad.put("Circum", 26.7);
		pd.put("LHC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2.2e11);
		ad.put("EnBeam", 7000.0);
		ad.put("BetaVer", 0.15);
		ad.put("BetaHor", 0.15);
		ad.put("EmNorVer", 2.5e-6);
		ad.put("EmNorHor", 2.5e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 2760.0);
		ad.put("BunLen", 90e-3);
		ad.put("Circum", 26.7);
		pd.put("HL-LHC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2.2e11);
		ad.put("EnBeam", 13500.0);
		ad.put("BetaVer", 0.45);
		ad.put("BetaHor", 0.45);
		ad.put("EmNorVer", 2.5e-6);
		ad.put("EmNorHor", 2.5e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 2808.0);
		ad.put("BunLen", 90e-3);
		ad.put("Circum", 26.7);
		pd.put("HE-LHC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.6e10);
		ad.put("EnBeam", 4000.0);
		ad.put("BetaVer", 0.8);
		ad.put("BetaHor", 0.8);
		ad.put("EmNorVer", 2e-6);
		ad.put("EmNorHor", 2e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 338.0);
		ad.put("BunLen", 7.55e-2);
		ad.put("Circum", 27.0);
		pd.put("LHC-Pb", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1e11);
		ad.put("EnBeam", 50000.0);
		ad.put("BetaVer", 1.1);
		ad.put("BetaHor", 1.1);
		ad.put("EmNorVer", 2.2e-6);
		ad.put("EmNorHor", 2.2e-6);
		ad.put("RevFrq", 2998.0);
		ad.put("NumBunInBeam", 10400.0);
		ad.put("BunLen", 0.08);
		ad.put("Circum", 100.0);
		pd.put("FCC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e11);
		ad.put("EnBeam", 35.6e3);
		ad.put("BetaVer", 75e-2);
		ad.put("BetaHor", 75e-2);
		ad.put("EmNorVer", 4.1e-6);
		ad.put("EmNorHor", 4.1e-6);
		ad.put("RevFrq", 5485.0);
		ad.put("NumBunInBeam", 5835.0);
		ad.put("BunLen", 75.5e-3);
		ad.put("Circum", 54.7);
		pd.put("SppC-35.6", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e11);
		ad.put("EnBeam", 68.0e3);
		ad.put("BetaVer", 24e-2);
		ad.put("BetaHor", 24e-2);
		ad.put("EmNorVer", 3.05e-6);
		ad.put("EmNorHor", 3.05e-6);
		ad.put("RevFrq", 3000.0);
		ad.put("NumBunInBeam", 10667.0);
		ad.put("BunLen", 15.8e-3);
		ad.put("Circum", 100.0);
		pd.put("SppC-68", ad);
		
		
		particleDataMap.put("proton", pd);
		
		pd = new ParticleData();

		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.25e12);
		ad.put("EnBeam", 1000.0);
		ad.put("BetaVer", 15e-2);
		ad.put("BetaHor", 15e-2);
		ad.put("EmNorVer", 12.5e-6);
		ad.put("EmNorHor", 12.5e-6);
		ad.put("RevFrq", 47000.0);
		ad.put("NumBunInBeam", 30.0);
		ad.put("BunLen", 15e-2);
		pd.put("Tevatron-high", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 5e12);
		ad.put("EnBeam", 1000.0);
		ad.put("BetaVer", 15e-2);
		ad.put("BetaHor", 15e-2);
		ad.put("EmNorVer", 50e-6);
		ad.put("EmNorHor", 50e-6);
		ad.put("RevFrq", 47000.0);
		ad.put("NumBunInBeam", 10.0);
		ad.put("BunLen", 15e-2);
		pd.put("Tevatron-low", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 80e10);
		ad.put("EnBeam", 50000.0);
		ad.put("BetaVer", 10e-2);
		ad.put("BetaHor", 10e-2);
		ad.put("EmNorVer", 30e-6);
		ad.put("EmNorHor", 30e-6);
		ad.put("RepFrq", 7.9);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.002);
		ad.put("Circum", 100.0);
		ad.put("Turn", 1350.0);
		pd.put("100TeV", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 3e11);
		ad.put("EnBeam", 275.0);
		ad.put("BetaVer", 5e-2);
		ad.put("BetaHor", 5e-2);
		ad.put("EmNorVer", 0.2e-6);
		ad.put("EmNorHor", 0.2e-6);
		ad.put("RepFrq", 15.0);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.002);
		ad.put("Circum", 1.822);
		ad.put("Turn", 3279.0);
		pd.put("acosta", ad);
		
		particleDataMap.put("proton-mu", pd);
		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2.2e11);
		ad.put("EnBeam", 7000.0);
		ad.put("BetaVer", 0.07);
		ad.put("BetaHor", 0.07);
		ad.put("EmNorVer", 2e-6);
		ad.put("EmNorHor", 2e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 2760.0);
		ad.put("BunLen", 90e-3);
		ad.put("Circum", 26.7);
		pd.put("HL-LHC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2.5e11);
		ad.put("EnBeam", 13500.0);
		ad.put("BetaVer", 0.1);
		ad.put("BetaHor", 0.1);
		ad.put("EmNorVer", 2.5e-6);
		ad.put("EmNorHor", 2.5e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 2808.0);
		ad.put("BunLen", 90e-3);
		ad.put("Circum", 26.7);
		pd.put("HE-LHC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1e11);
		ad.put("EnBeam", 50000.0);
		ad.put("BetaVer", 0.15);
		ad.put("BetaHor", 0.15);
		ad.put("EmNorVer", 2.2e-6);
		ad.put("EmNorHor", 2.2e-6);
		ad.put("RevFrq", 2998.0);
		ad.put("NumBunInBeam", 10400.0);
		ad.put("BunLen", 0.08);
		ad.put("Circum", 100.0);
		pd.put("FCC", ad);
		
		particleDataMap.put("proton-ERL60", pd);
		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 7e7);
		ad.put("EnBeam", 5.74e5);
		ad.put("BetaVer", 0.5);
		ad.put("BetaHor", 0.5);
		ad.put("EmNorVer", 1.5e-6);
		ad.put("EmNorHor", 1.5e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 592.0);
		ad.put("BunLen", 7.94e-2);
		ad.put("Circum", 27.0);
		pd.put("LHC", ad);
				
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e8);
		ad.put("EnBeam", 4.1e6);
		ad.put("BetaVer", 0.3);
		ad.put("BetaHor", 0.3);
		ad.put("EmNorVer", 1.5e-6);
		ad.put("EmNorHor", 1.5e-6);
		ad.put("RevFrq", 2998.0);
		ad.put("NumBunInBeam", 5400.0);
		ad.put("BunLen", 0.08);
		pd.put("FCC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.2e8);
		ad.put("EnBeam", 328000.0);
		ad.put("BetaVer", 0.8);
		ad.put("BetaHor", 0.8);
		ad.put("EmNorVer", 1.5e-6);
		ad.put("EmNorHor", 1.5e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 338.0);
		ad.put("BunLen", 7.55e-2);
		ad.put("Circum", 27.0);
		pd.put("LHC-p", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 0.18e10);
		ad.put("EnBeam", 3075000.0);
		ad.put("BetaVer", 75e-2);
		ad.put("BetaHor", 75e-2);
		ad.put("EmNorVer", 0.22e-6);
		ad.put("EmNorHor", 0.22e-6);
		ad.put("RevFrq", 3000.0);
		ad.put("NumBunInBeam", 10080.0);
		ad.put("BunLen", 7e-2);
		ad.put("Circum", 27.0);
		pd.put("CEPC-SppC", ad);
		
		
		particleDataMap.put("Pb", pd);
		
		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 0.08e10);
		ad.put("EnBeam", 100.0*197); //mass of Au
		ad.put("BetaVer", 0.7);
		ad.put("BetaHor", 0.7);
		ad.put("EmNorVer", 2.22e-6 / 3.14);
		ad.put("EmNorHor", 2.22e-6/3.14);
		ad.put("RevFrq", 78247.0); 
		ad.put("NumBunInBeam", 111.0);
		ad.put("BunLen", 7e-2);
		ad.put("Circum", 3.834);
		pd.put("RHIC", ad);
				
		particleDataMap.put("Au", pd);
		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.8e8);
		ad.put("EnBeam", 0.574e6);
		ad.put("BetaVer", 0.07);
		ad.put("BetaHor", 0.07);
		ad.put("EmNorVer", 1.5e-6);
		ad.put("EmNorHor", 1.5e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("BunLen", 0.08);
		ad.put("NumBunInBeam", 1200.0);
		pd.put("HL-LHC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.8e8);
		ad.put("EnBeam", 1.03e6);
		ad.put("BetaVer", 0.1);
		ad.put("BetaHor", 0.1);
		ad.put("EmNorVer", 1.0e-6);
		ad.put("EmNorHor", 1.0e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("BunLen", 0.08);
		ad.put("NumBunInBeam", 1200.0);
		pd.put("HE-LHC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 1.8e8);
		ad.put("EnBeam", 4.1e6);
		ad.put("BetaVer", 0.15);
		ad.put("BetaHor", 0.15);
		ad.put("EmNorVer", 0.9e-6);
		ad.put("EmNorHor", 0.9e-6);
		ad.put("RevFrq", 2998.0);
		ad.put("NumBunInBeam", 2072.0);
		ad.put("BunLen", 0.08);
		pd.put("FCC", ad);
		
		particleDataMap.put("Pb-ERL60", pd);
/*		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 4.67e9);
		ad.put("EnBeam", 60.0);
		ad.put("BetaVer", 0.07);
		ad.put("BetaHor", 0.07);
		ad.put("EmNorVer", 1.5e-6);
		ad.put("EmNorHor", 1.5e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 1200.0);
		pd.put("HL-LHC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 6.2e9);
		ad.put("EnBeam", 60.0);
		ad.put("BetaVer", 0.1);
		ad.put("BetaHor", 0.1);
		ad.put("EmNorVer", 1.0e-6);
		ad.put("EmNorHor", 1.0e-6);
		ad.put("RevFrq", 11245.0);
		ad.put("NumBunInBeam", 1200.0);
		pd.put("HE-LHC", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 12.5e9);
		ad.put("EnBeam", 60.0);
		ad.put("BetaVer", 0.15);
		ad.put("BetaHor", 0.15);
		ad.put("EmNorVer", 0.9e-6);
		ad.put("EmNorHor", 0.9e-6);
		ad.put("RevFrq", 2998.0);
		ad.put("NumBunInBeam", 2072.0);
		pd.put("FCC", ad);
		
		pd.setLinear();
		particleDataMap.put("electron-ERL60+Pb", pd);
*/
		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e12);
		ad.put("EnBeam", 750.0);
		ad.put("BetaVer", 0.01);
		ad.put("BetaHor", 0.01);
		ad.put("EmNorVer", 2.5e-5);
		ad.put("EmNorHor", 2.5e-5);
		ad.put("RepFrq", 15.0);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.01);
		ad.put("Circum", 2.5);
		pd.put("MAF750", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e12);
		ad.put("EnBeam", 1500.0);
		ad.put("BetaVer", 0.005);
		ad.put("BetaHor", 0.005);
		ad.put("EmNorVer", 2.5e-5);
		ad.put("EmNorHor", 2.5e-5);
		ad.put("RepFrq", 12.0);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.005);
		ad.put("Circum", 4.5);
		pd.put("MAF1500", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e12);
		ad.put("EnBeam", 3000.0);
		ad.put("BetaVer", 0.0025);
		ad.put("BetaHor", 0.0025);
		ad.put("EmNorVer", 2.5e-5);
		ad.put("EmNorHor", 2.5e-5);
		ad.put("RepFrq", 6.0);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.002);
		ad.put("Circum", 6.0);
		pd.put("MAF3000", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 80e10);
		ad.put("EnBeam", 50000.0);
		ad.put("BetaVer", 0.0025);
		ad.put("BetaHor", 0.0025);
		ad.put("EmNorVer", 8.7e-6);
		ad.put("EmNorHor", 8.7e-6);
		ad.put("RepFrq", 7.9);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.002);
		ad.put("Circum", 100.0);
		ad.put("Turn", 1350.0);
		pd.put("100TeV", ad);
		
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 20e11);
		ad.put("EnBeam", 960.0);
		ad.put("BetaVer", 0.01);
		ad.put("BetaHor", 0.01);
		ad.put("EmNorVer", 25e-6);
		ad.put("EmNorHor", 25e-6);
		ad.put("RepFrq", 15.0);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.002);
		ad.put("Circum", 1.8220);
		ad.put("Turn", 3279.0);
		pd.put("acosta", ad);
		
		particleDataMap.put("muon", pd);
		
		pd = new ParticleData();

		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e12);
		ad.put("EnBeam", 200.0);
		ad.put("BetaVer", 7.5e-2);
		ad.put("BetaHor", 7.5e-2);
		ad.put("EmNorVer", 50e-6);
		ad.put("EmNorHor", 50e-6);
		ad.put("RepFrq", 30.0);
		ad.put("NumBunInBeam", 2.0);
		ad.put("Turn", 2000.0);
		ad.put("BunLen", 15e-2);
		ad.put("Circum", 0.690);
		pd.put("MuRing-high", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 5e11);
		ad.put("EnBeam", 200.0);
		ad.put("BetaVer", 7.5e-2);
		ad.put("BetaHor", 7.5e-2);
		ad.put("EmNorVer", 200e-6);
		ad.put("EmNorHor", 200e-6);
		ad.put("RepFrq", 10.0);
		ad.put("NumBunInBeam", 2.0);
		ad.put("BunLen", 15e-2);
		ad.put("Turn", 2000.0);
		ad.put("Circum", 0.690);
		pd.put("MuRing-low", ad);
		particleDataMap.put("muon-tevatron", pd);
		
		pd = new ParticleData();
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e12);
		ad.put("EnBeam", 750.0);
		ad.put("BetaVer", 0.01);
		ad.put("BetaHor", 0.01);
		ad.put("EmNorVer", 2.5e-5);
		ad.put("EmNorHor", 2.5e-5);
		ad.put("RepFrq", 15.0);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.01);
		ad.put("Circum", 2.5);
		ad.put("Turn", 1000.0);
		pd.put("MAF", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e12);
		ad.put("EnBeam", 1500.0);
		ad.put("BetaVer", 0.005);
		ad.put("BetaHor", 0.005);
		ad.put("EmNorVer", 2.5e-5);
		ad.put("EmNorHor", 2.5e-5);
		ad.put("RepFrq", 12.0);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.005);
		ad.put("Circum", 4.5);
		ad.put("Turn", 1000.0);
		pd.put("MAF1500", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 2e12);
		ad.put("EnBeam", 3000.0);
		ad.put("BetaVer", 0.0025);
		ad.put("BetaHor", 0.0025);
		ad.put("EmNorVer", 2.5e-5);
		ad.put("EmNorHor", 2.5e-5);
		ad.put("RepFrq", 6.0);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.002);
		ad.put("Circum", 6.0);
		ad.put("Turn", 1000.0);

		pd.put("MAF3000", ad);
		
		ad = new AcceleratorData();
		ad.put("NumParInBun", 80e10);
		ad.put("EnBeam", 50000.0);
		ad.put("BetaVer", 0.0025);
		ad.put("BetaHor", 0.0025);
		ad.put("EmNorVer", 8.7e-6);
		ad.put("EmNorHor", 8.7e-6);
		ad.put("RepFrq", 7.9);
		ad.put("NumBunInBeam", 1.0);
		ad.put("BunLen", 0.002);
		ad.put("Circum", 100.0);
		ad.put("Turn", 1350.0);
		pd.put("100TeV", ad);
		
		particleDataMap.put("muon+", pd);

	}
	public void loadFromJSON() throws IOException
	{
		Gson gson = new Gson();
		File file = new File(save);
		if(!file.exists())
			file.mkdir();
		for(String folder1 : file.list())
		{
			String path1 = save+"/"+folder1;
			if(path1.contains("."))
				continue;
			file = new File(path1);
			ParticleData pd = new ParticleData();
			if(folder1.contains("linac"))
				pd.setLinear();
			particleDataMap.put(folder1, pd);
			
			if(file.list().length == 0)
			{
				file.delete();
				continue;
			}
			for(String folder2 : file.list())
			{
				String path2 = path1+"/"+folder2;
				file = new File(path2);
				FileReader fileReader = new FileReader(file);
				JsonReader jsonReader = new JsonReader(fileReader);
				AcceleratorData acc = gson.fromJson(jsonReader, AcceleratorData.class);
				
				particleDataMap.get(folder1).getAccelerators().put(folder2.substring(0,folder2.indexOf('.')), acc);
			}
		}
	}
	public void removeAccFromJSON(String particle, String name)
	{
		File file = new File(save+"/"+particle+"/"+name+".JSON");
		if(file.exists())
			file.delete();
	}
	public void saveNewAccToJSON(String particle, String name, AcceleratorData accData) throws IOException
	{
		String path = save+"/"+particle;
		File file = new File(path);

		if(!file.exists())
		{
			file.mkdir();
		}

		try (Writer writer = new FileWriter(path+"/"+name+".JSON")) {
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(accData, writer);
		}
	}
	
	public void loadSettingsFromJSON() throws FileNotFoundException
	{
		Gson gson = new Gson();
		File file = new File(save+"/settings.JSON");
		FileReader fileReader;
		fileReader = new FileReader(file);

		JsonReader jsonReader = new JsonReader(fileReader);
		settingsData = gson.fromJson(jsonReader, settingsData.getClass());
		
	}
	
	public void saveSettingsToJSON() throws IOException
	{
		File file = new File(save);
		if(!file.exists())
			file.mkdir();
		try (Writer writer = new FileWriter(save+"/settings.JSON")) {
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(settingsData, writer);
		}

	}
	public void loadVariableData()
	{
		variableDataMap.put("NumParInBun", new VariableData("Number of particle per bunch(N):",""));
		variableDataMap.put("EnBeam", new VariableData("Particle beam energy:","GeV"));
		variableDataMap.put("BetaVer", new VariableData("Vertical Beta function of particle beam at IP:","m"));
		variableDataMap.put("BetaHor", new VariableData("Horizontal Beta function of particle beam at IP:","m"));
		variableDataMap.put("Beta", new VariableData("Beta function of particle beam at IP:","m"));
		variableDataMap.put("EmNorVer", new VariableData("Norm. Vertical Emittance of particle beam:","m"));
		variableDataMap.put("EmNorHor", new VariableData("Norm. Horizontal Emittance of particle beam:","m"));
		variableDataMap.put("EmVer", new VariableData("Trans. Vertical Emittance of particle beam:","m"));
		variableDataMap.put("EmHor", new VariableData("Trans. Horizontal Emittance of particle beam:","m"));
		variableDataMap.put("EmNor", new VariableData("Norm. Emittance of particle beam:","m"));
		variableDataMap.put("PulFrq", new VariableData("Pulse Frequency of beams:","Hz"));
		variableDataMap.put("RevFrq", new VariableData("Revolution Frequency of beam:","Hz"));
		variableDataMap.put("RepFrq", new VariableData("Repetition Rate of beam:","Hz"));
		variableDataMap.put("ColFrq", new VariableData("Collision Frequency of beams:","Hz"));

		variableDataMap.put("NumBunInBeam", new VariableData("Bunches in particle beam:",""));
		variableDataMap.put("BunLen", new VariableData("Particle Beam Bunch Length:","m"));
		variableDataMap.put("BunSpace", new VariableData("Bunch Spacing of Particle Beam:","m"));
		variableDataMap.put("Circum", new VariableData("Circumference:","km"));
		variableDataMap.put("Turn", new VariableData("Number of Turns:","turn"));
		variableDataMap.put("DutyFac", new VariableData("Duty Factor:",""));

		
		variableDataMap.put("PowLim", new VariableData("Power Limit for Particle Beam:","MW"));
		variableDataMap.put("DisrLim", new VariableData("Disruption Limit:",""));
		variableDataMap.put("BeamParLim", new VariableData("Beam-Beam Parameter Limit:",""));
		variableDataMap.put("NumMacPar", new VariableData("Number of Macroparticles:",""));
		variableDataMap.put("IPScale", new VariableData("Scale of Sigma:",""));
		variableDataMap.put("XRes", new VariableData("Resolution of X-axis:",""));
		variableDataMap.put("YRes", new VariableData("Resolution of Y-axis:",""));
		variableDataMap.put("ZRes", new VariableData("Resolution of Z-axis:",""));
		variableDataMap.put("chargeDimScaleMP", new VariableData("Cloud scale of Macroparticle:",""));
		variableDataMap.put("crossingAngle", new VariableData("Crossing angle of Collision:","rad"));


		variableDataMap.put("sqrtS", new VariableData("<html>Center-of-mass, &radic;<span style=\"text-decoration: overline\">S</span>: <html>","GeV"));
		variableDataMap.put("noLum", new VariableData("Nominal Luminosity: ","<html>cm<sup style=\"font-size:8px\">-1</sup>s<sup style=\"font-size:8px\">-1</sup><html>"));
		variableDataMap.put("efLum", new VariableData("Effective Luminosity: ","<html>cm<sup style=\"font-size:8px\">-1</sup>s<sup style=\"font-size:8px\">-1</sup><html>"));
		variableDataMap.put("reFac", new VariableData("Enhancement/Reduction Factor: ",""));
		
		variableDataMap.put("sigmaX", new VariableData("<html>  SigmaX (&sigma<sub>x</sub>): <html>","m"));
		variableDataMap.put("sigmaY", new VariableData("<html>  SigmaY (&sigma<sub>y</sub>): <html>","m"));

		variableDataMap.put("disX", new VariableData("<html>  Disruption (D<sub>x</sub>): <html>",""));
		variableDataMap.put("disY", new VariableData("<html>  Disruption (D<sub>y</sub>): <html>",""));
		variableDataMap.put("divX", new VariableData("<html>  Divergence (A<sub>x</sub>): <html>",""));
		variableDataMap.put("divY", new VariableData("<html>  Divergence (A<sub>y</sub>): <html>",""));
		variableDataMap.put("bbTunX", new VariableData("<html>  BB Tuneshift (&xi<sub>x</sub>): <html>",""));
		variableDataMap.put("bbTunY", new VariableData("<html>  BB Tuneshift (&xi<sub>y</sub>): <html>",""));
		
		
	}
	
	public void loadSettingsData()
	{

		settingsData.put("NumMacPar", 50000.0);
		settingsData.put("IPScale", 3.0);
		settingsData.put("XRes", 30.0);
		settingsData.put("YRes", 30.0);
		settingsData.put("ZRes", 30.0); 		 //it is how many grid of IP size in X, Y and Z-axis 
		settingsData.put("chargeDimScaleMP", 1.0);
		settingsData.put("crossingAngle", 0.0);
		
	}

	public String getTitle(String key)
	{
		return variableDataMap.get(key).getTitle();
	}
	public String getUnit(String key)
	{
		return variableDataMap.get(key).getUnit();
	}
	public LinkedHashMap<String, VariableData> getVariableDataMap() {
		return variableDataMap;
	}
	public void setVariableDataMap(LinkedHashMap<String, VariableData> variableDataMap) {
		this.variableDataMap = variableDataMap;
	}
	public LinkedHashMap<String, Double> getSettingsData() {
		return settingsData;
	}
	public void setSettingsData(LinkedHashMap<String, Double> settingsData) {
		this.settingsData = settingsData;
	}
	public HashMap<String, ParticleData> getParticleDataMap() {
		return particleDataMap;
	}
	public void setParticleDataMap(HashMap<String, ParticleData> particleDataMap) {
		this.particleDataMap = particleDataMap;
	}
	
}
