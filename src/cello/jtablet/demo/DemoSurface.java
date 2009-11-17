package cello.jtablet.demo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import cello.jtablet.TabletDevice;
import cello.jtablet.TabletManager;
import cello.jtablet.events.TabletAdapter;
import cello.jtablet.events.TabletEvent;

/**
 * Simple demo component that handles tablet input to draw lines 
 * 
 * @author marcello
 */
public class DemoSurface extends JComponent {

	private BufferedImage bi;
	private Graphics2D g2d;
	
	/**
	 * 
	 */
	public DemoSurface() {
		createBuffer();
		TabletManager.addTabletListener(this, new TabletAdapter() {

			double lastX,lastY,lastPressure;
			boolean dragged = false;
			

			@Override
			public synchronized void cursorDragged(TabletEvent ev) {
				dragged = true;
				float x = ev.getRealX();
				float y = ev.getRealY();
				float pressure = ev.getPressure() * 20;
				if (x==0 && y==0) {
					System.out.println("!");
				}
				if (lastPressure>0) {
					double angle = Math.atan2(y-lastY, x-lastX);
					
					// distance between points = c
					double c = Math.hypot(x-lastX, y-lastY);
					// radial difference = b - a
					double b = lastPressure-pressure;
					// remaining side length
					// c*c = a*a + b*b ->
					double a = Math.sqrt(c*c-b*b);
					
					double angle2 = Math.atan2(a, b);
					 
					double sin1 = Math.sin(angle-angle2);
					double cos1 = Math.cos(angle-angle2);
					double sin2 = Math.sin(angle+angle2);
					double cos2 = Math.cos(angle+angle2);
					GeneralPath p = new GeneralPath();
					p.moveTo((float)(lastX+cos1*lastPressure),(float)(lastY+sin1*lastPressure));
					p.lineTo((float)(lastX+cos2*lastPressure),(float)(lastY+sin2*lastPressure));
					p.lineTo((float)(x+cos2*pressure),        (float)(y+sin2*pressure));
					if (pressure>0) {
						p.lineTo((float)(x+cos1*pressure),   	  (float)(y+sin1*pressure));
					}
					p.closePath();					
					
					g2d.setColor(ev.getDevice() == TabletDevice.STYLUS_ERASER ? Color.WHITE : Color.BLACK);
					g2d.fill(new Ellipse2D.Float(x-pressure,y-pressure,2*pressure-0.5f,2*pressure-0.5f));
					g2d.fill(p);
					repaint();
				}
				lastX = x;
				lastY = y;
				lastPressure = pressure;
			}

			@Override
			public void cursorMoved(TabletEvent ev) {
				lastX = ev.getRealX();
				lastY = ev.getRealY();
				lastPressure = ev.getPressure();
				dragged = false;
			}

			@Override
			public void cursorPressed(TabletEvent ev) {
				lastX = ev.getRealX();
				lastY = ev.getRealY();
				lastPressure = ev.getPressure();
				dragged = false;
			}

			@Override
			public void cursorReleased(TabletEvent ev) {
				if (!dragged) {
					double x = ev.getRealX();
					double y = ev.getRealY();
					double pressure = ev.getPressure()*20;
					g2d.setColor(ev.getDevice() == TabletDevice.STYLUS_ERASER ? Color.WHITE : Color.BLACK);
					g2d.fill(new Ellipse2D.Double(x-pressure,y-pressure,2*pressure,2*pressure));
				}
				dragged = false;
			}
			
		});
	}

	private void createBuffer() {
		int width = Math.max(1,getWidth());
		int height= Math.max(1,getHeight());
		bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		g2d = bi.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0,0, width, height);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (getWidth()>bi.getWidth() || getHeight()>bi.getHeight()) {
			BufferedImage old = bi;
			createBuffer();
			g2d.drawImage(old,0,0,null);
		}
		g.drawImage(bi, 0, 0, null);
	}

	public String toString() {
		return getClass().getSimpleName();
	}
	
}