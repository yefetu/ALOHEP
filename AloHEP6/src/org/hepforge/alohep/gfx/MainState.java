package org.hepforge.alohep.gfx;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import org.hepforge.alohep.AloHEP;
import org.hepforge.alohep.calc.Bunch;
import org.hepforge.alohep.calc.MacroParticle;
import org.hepforge.alohep.calc.Vector3d;

public class MainState extends State {

	private AloHEP alohep;
	private Bunch bunchL;
	private Bunch bunchR;
	private boolean mouseButtonPressed = false;
	private Point start;
	private Point prevCenter;
	private Point center;
	public MainState(AloHEP alohep) {
		super(alohep.getAnimationPanel());
		this.alohep = alohep;
	}

	@Override
	public void init(AnimationPanel animationPanel) {
		bunchL = alohep.getLuminosityCalc().getBunchL();
		bunchR = alohep.getLuminosityCalc().getBunchR();
		start = new Point(0,0);
		center = new Point(0,0);
		prevCenter = new Point(0,0);
	}

	@Override
	public void update() {
		if(getinput().isKeyPressing(KeyEvent.VK_RIGHT))
		{

		}
		if(getinput().isKeyPressing(KeyEvent.VK_LEFT))
		{

		}
		if(getinput().isKeyClicked(KeyEvent.VK_X))
		{
			cleanCenter();
			getCamera().setAxis(Camera.Axis.X);
		}
		
		if(getinput().isKeyClicked(KeyEvent.VK_Y))
		{
			cleanCenter();
			getCamera().setAxis(Camera.Axis.Y);
		}
		
		if(getinput().isKeyClicked(KeyEvent.VK_Z))
		{
			cleanCenter();
			getCamera().setAxis(Camera.Axis.Z);
		}
		if(getinput().getWheel()>0)
		{

			/*Point p = board.getDisplay().getMousePosition();
			if(p != null)
			{
				center.x-=p.x*0.1;
				center.y+=p.y*0.1;
			}*/
			center.x*=1.1;
			center.y*=1.1;
			getCamera().scaleChange(0.9);
			getinput().setWheel(0);
		}
		if(getinput().getWheel()<0)
		{

			/*Point p = board.getDisplay().getMousePosition();
			if(p != null)
			{
				center.x+=p.x*0.1;
				center.y-=p.y*0.1;
			}*/
			center.x*=0.9;
			center.y*=0.9;
			getCamera().scaleChange(1.1);
			getinput().setWheel(0);

		}
		if(getinput().isMousePressing(MouseEvent.BUTTON1))
		{
			Point p = board.getDisplay().getMousePosition();
			if(p != null)
			{
				if(!mouseButtonPressed)
				{
					start = p;
					mouseButtonPressed = true;
				}
				else
				{
					center.x = prevCenter.x + (p.x - start.x);
					center.y = prevCenter.y + (p.y - start.y);
				}
			}
		}
		else
		{
			mouseButtonPressed = false;
			prevCenter.x = center.x;
			prevCenter.y = center.y;
		}
		getCamera().setCenter(center);
	}
	private void cleanCenter()
	{
		center.x = 0;
		center.y = 0;
		prevCenter.x = 0;
		prevCenter.y = 0;
		
	}
	
	@Override
	public void render(BufferedImage image) 
	{
		Graphics2D g = image.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setColor(new Color(255,255,255,255));
		g.fillRect(0,0,image.getWidth(),image.getHeight());
		g.setColor(new Color(0x0070C0));
		for(MacroParticle MP:bunchL.getMPs())
		{
			Point p = getCamera().getCameraPos(MP.getPos());
			//g.drawString(vec.print(), p.x, p.y);
			g.fillOval(p.x, p.y, 3, 3);
		}
		g.setColor(new Color(0xDE7F00));
		for(MacroParticle MP:bunchR.getMPs())
		{
			Point p = getCamera().getCameraPos(MP.getPos());
		//	g.drawString(vec.print(), p.x, p.y);
			g.fillOval(p.x, p.y, 3, 3);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif", 1, 12));
		g.drawString(getCamera().getAxis() + " Axis", Display.width-80, 20);
		g.drawString("Scale x"+new DecimalFormat("0.0").format(getCamera().getScaleFactor()), Display.width-80, 40);
		
		g.dispose();
	}

}
