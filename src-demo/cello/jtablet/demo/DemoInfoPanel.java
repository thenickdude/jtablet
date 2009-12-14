package cello.jtablet.demo;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cello.jtablet.TabletDevice;
import cello.jtablet.TabletManager;
import cello.jtablet.TabletDevice.Support;
import cello.jtablet.events.TabletEvent;
import cello.jtablet.events.TabletFunneler;

/**
 * This panel displays tablet information for  
 * 
 * @author marcello
 */
public class DemoInfoPanel extends JPanel {

	private JLabel typeValue = new JLabel("Type");
	private JLabel xValue = new JLabel("X");
	private JLabel yValue = new JLabel("Y");
	private JLabel pressureValue = new JLabel("Pressure");
	private JLabel tiltXValue = new JLabel("Tilt X");
	private JLabel tiltYValue = new JLabel("Tilt Y");
	private JLabel sidePressureValue = new JLabel("Side Pressure");
	private JLabel rotationValue = new JLabel("Rotation");
	private JLabel ppsValue = new JLabel("Rate");
	private JLabel labels[] = {
		ppsValue,
		typeValue,
		xValue,
		yValue,
		pressureValue,
		sidePressureValue,
		tiltXValue,
		tiltYValue,
		rotationValue
	};
	private Map<JLabel,JLabel> labelPrefixes = new HashMap<JLabel,JLabel>(labels.length);
	
	private NumberFormat nf = DecimalFormat.getNumberInstance();
	{ 
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(true);
	}
	
	/**
	 * Constructs a new DemoInfoPanel targetting a given component
	 * @param targetComponent
	 */
	public DemoInfoPanel(Component targetComponent) {
		super (new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx=0;
		gbc.gridy=0;
		gbc.insets = new Insets(5,5,5,5);
		
		for (JLabel label : labels) {
			gbc.gridx=0;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.fill = GridBagConstraints.NONE;
			JLabel label2 = new JLabel(label.getText()+":",JLabel.LEFT);
			labelPrefixes.put(label, label2);
			add(label2, gbc);
			
			gbc.gridx=2;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			label.setText("XXXXXXXXX");
			label.setPreferredSize(label.getPreferredSize());
			label.setText("");
			add(label, gbc);
			
			gbc.gridy++;
		}

		TabletManager.getManager().addTabletListener(targetComponent, new TabletFunneler() {
			private int eventCount;
			private long lastTime = System.currentTimeMillis();
			protected void handleEvent(TabletEvent ev) {
				eventCount++;
				long time = System.currentTimeMillis();
				if (time-lastTime > 1000) {
					ppsValue.setText(eventCount+" pps");
					lastTime = time;
					eventCount = 0;
				}
				TabletDevice device = ev.getDevice();
				setText(typeValue, 			device.getType().toString(), 			Support.SUPPORTED);
				setText(xValue,				nf.format(ev.getRealX()),				Support.SUPPORTED);
				setText(yValue,				nf.format(ev.getRealY()),				Support.SUPPORTED);
				setText(pressureValue,		nf.format(ev.getPressure()),			device.supportsPressure());
				setText(sidePressureValue,	nf.format(ev.getSidePressure()),		device.supportsSidePressure());
				setText(tiltXValue,			nf.format(Math.toDegrees(ev.getTiltX()))+"º",		device.supportsTilt());
				setText(tiltYValue,			nf.format(Math.toDegrees(ev.getTiltY()))+"º",		device.supportsTilt());
				setText(rotationValue,		nf.format(Math.toDegrees(ev.getRotation()))+"º",		device.supportsRotation());
			}
			private void setText(JLabel label, String value, TabletDevice.Support supported) {
				if (supported != null) {
					JLabel prefix = labelPrefixes.get(label);
					switch (supported) {
					case NONE:
						label.setEnabled(false);
						prefix.setEnabled(false);
						label.setForeground(new Color(0x800000));
						break;
					case SUPPORTED:
						label.setEnabled(true);
						prefix.setEnabled(true);
						label.setForeground(new Color(0x008000));
						break;
					case UNKNOWN:
						label.setEnabled(true);
						prefix.setEnabled(true);
						label.setForeground(new Color(0x808000));
						break;
					}
				}
				label.setText(value);

			}
		});
	}

}
