package org.hepforge.alohep.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import org.hepforge.alohep.AloHEP;
import org.hepforge.alohep.calc.LuminosityCalc;
import org.hepforge.alohep.gfx.AnimationPanel;
import org.hepforge.alohep.gfx.MainState;



public class ControlPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private SettingsPanel settingsPanel;
	private JPanel calculatePanel;
	private JButton calculateButton;
	private boolean isProcessing = false;
	
	private AloHEP alohep;
	
	public ControlPanel(AloHEP alohep)
	{
		this.alohep = alohep;
		settingsPanel = new SettingsPanel(alohep);
		calculatePanel = new JPanel();
		calculateButton = new JButton("Calculate");
		calculateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				calculateButtonPressed();
			}
		});
		calculateButton.setPreferredSize(new Dimension(250, 100));
		calculateButton.setFont(MainPanel.BUTTON_FONT);
		Border border = BorderFactory.createEmptyBorder(50,20,50,20);
		calculatePanel.setBorder(border);
		FlowLayout layout = new FlowLayout();
		
		calculatePanel.setLayout(layout);
		
		
		calculatePanel.add(calculateButton);

		setLayout(new BorderLayout());
		add(settingsPanel,BorderLayout.WEST);
		add(calculatePanel,BorderLayout.EAST);
	}
	public void calculateButtonPressed()
	{
		new Thread() {
			@Override
			public void run()
			{
				JProgressBar pb = alohep.getMainPanel().getPbar();
				if(!isProcessing)
				{
					isProcessing = true;
					calculateButton.setText("Cancel");
					pb.setVisible(true);
					alohep.getLuminosityCalc().start();
					if(alohep.getLuminosityCalc().isSimulation())
						startAnimation();
					alohep.getLuminosityCalc().run();
				}
				pb.setVisible(false);
				pb.setValue(0);
				alohep.getLuminosityCalc().setRunning(false);
				if(alohep.getLuminosityCalc().isSimulation())
				{
					alohep.getAnimationPanel().getDisplay().getFrame().dispose();
					alohep.getAnimationPanel().setRunning(false);
						
				}
				calculateButton.setText("Calculate");
				isProcessing = false;
				alohep.setLuminosityCalc(new LuminosityCalc(alohep));

			}

		}.start();

	}
	public void startAnimation()
	{
		alohep.setAnimationPanel(new AnimationPanel(alohep));
		MainState mainState = new MainState(alohep);
		alohep.getAnimationPanel().putState("MainState", mainState);
		alohep.getAnimationPanel().setState("MainState");
		alohep.getAnimationPanel().start();
	}
	public SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}
	public void setSettingsPanel(SettingsPanel settingsPanel) {
		this.settingsPanel = settingsPanel;
	}
}
