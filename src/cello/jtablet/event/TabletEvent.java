/*!
 * Copyright (c) 2009 Marcello Bastéa-Forte (marcello@cellosoft.com)
 * 
 * This software is provided 'as-is', without any express or implied
 * warranty. In no event will the authors be held liable for any damages
 * arising from the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 *     1. The origin of this software must not be misrepresented; you must not
 *     claim that you wrote the original software. If you use this software
 *     in a product, an acknowledgment in the product documentation would be
 *     appreciated but is not required.
 * 
 *     2. Altered source versions must be plainly marked as such, and must not be
 *     misrepresented as being the original software.
 * 
 *     3. This notice may not be removed or altered from any source
 *     distribution.
 */

package cello.jtablet.event;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.Serializable;

import cello.jtablet.TabletDevice;


/**
 * An event that indicates cursor input occurred on the given component. This class extends {@link MouseEvent} and 
 * provides a similar and extended API for accessing tablet events.
 * 
 * <p><b>Implementation Note:</b> currently keyboard modifiers (via {@link #getModifiers()}, {@link #getModifiersEx()}) 
 * and {@link #isPopupTrigger()} are not properly supported on all platforms.</p>
 * 
 * @author Marcello
 */
public class TabletEvent extends MouseEvent implements Serializable {
	
	private static final long serialVersionUID = 1;
	
	private final float x,y;
	private final float pressure;
	private final float sidePressure;
	
	private final float tiltX,tiltY;
	private final float rotation;
	
	private final float scrollX,scrollY;
	private final float zoomFactor;
	
	
	private final Type type;
	private final TabletDevice device;


	/**
	 * Constructs a new {@linkplain TabletEvent} with all the trimmings... 
	 * yourself.
	 * @param source
	 * @param type
	 * @param when
	 * @param device
	 * @param x 
	 * @param y 
	 * @param modifiers
	 * @param button 
	 * @param pressure
	 * @param tiltX 
	 * @param tiltY 
	 * @param sidePressure 
	 * @param rotation 
	 * @param deltaX 
	 * @param deltaY 
	 * @param zoom 
	 */
	public TabletEvent(Component source, Type type, long when, int modifiers, 
						TabletDevice device, float x, float y, float pressure,
						float tiltX, float tiltY, float sidePressure,
						float rotation, float deltaX, float deltaY, float zoom,
						int button) {
		
		super(source, type.getId(), when, modifiers,
				(int)x, (int)y, 0, false, button);

		this.type = type;
		this.device = device;
		this.x = x;
		this.y = y;
		this.pressure = pressure;
		this.tiltX = tiltX;
		this.tiltY = tiltY;
		this.sidePressure = sidePressure;
		this.rotation = rotation;
		this.scrollX = deltaX;
		this.scrollY = deltaY;
		this.zoomFactor = zoom;
	}
	/**
	 * Wrap a {@link MouseEvent} as a TabletEvent.
	 * @param e
	 * @param type 
	 * @param device 
	 */
	public TabletEvent(MouseEvent e, Type type, TabletDevice device) {
		super(e.getComponent(),e.getID(), e.getWhen(), e.getModifiersEx(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
		this.x = e.getX();
		this.y = e.getY();
		this.type = type;
		this.pressure = (e.getModifiersEx() & BUTTON1_DOWN_MASK) != 0 ? 1.0f : 0;
		this.tiltX = 0;
		this.tiltY = 0;
		this.sidePressure = 0;
		this.rotation = 0;
		this.device = device;
		this.scrollX = 0;
		this.scrollY = 0;
		this.zoomFactor = 0;
	} 

	


	/**
	 * Constructs a new {@linkplain TabletEvent} with all the trimmings...
	 * @param source
	 * @param type
	 * @param when
	 * @param modifiers
	 * @param device
	 * @param x
	 * @param y
	 * @param pressure
	 * @param tiltX
	 * @param tiltY
	 * @param sidePressure
	 * @param rotation
	 * @param button
	 */
	public TabletEvent(Component source, Type type, long when, int modifiers, TabletDevice device, 
			float x, float y, 
			float pressure, 
			float tiltX, float tiltY,
			float sidePressure, 
			float rotation, 
			int button) {
		this(source,type,when,modifiers,device,x,y,pressure,tiltX,tiltY,sidePressure,rotation,0,0,0,button);
	}
	/**
	 * Constructs a new {@linkplain TabletEvent} with some of the trimmings...
	 * @param source
	 * @param type
	 * @param when
	 * @param modifiers
	 * @param device
	 * @param x
	 * @param y
	 */
	public TabletEvent(Component source, Type type, long when, int modifiers, TabletDevice device, 
			float x, float y) {
		this(source,type,when,modifiers,device,x,y,0,0,0,0,0,0,0,0,NOBUTTON);
	}
	/**
	 * Constructs a new {@linkplain TabletEvent} with some of the trimmings...
	 * @param source
	 * @param type
	 * @param when
	 * @param modifiers
	 * @param device
	 * @param x
	 * @param y
	 * @param rotation
	 * @param deltaX
	 * @param deltaY
	 * @param zoom
	 */
	public TabletEvent(Component source, Type type, long when, int modifiers, TabletDevice device,
			float x, float y, 
			float rotation, 
			float deltaX, float deltaY,
			float zoom) {
		this(source,type,when,modifiers,device,x,y,0,0,0,0,rotation,deltaX,deltaY,zoom,NOBUTTON);
	}
	/**
	 * Constructs a new {@linkplain TabletEvent} with some of the trimmings...
	 * @param source
	 * @param type
	 * @param when
	 * @param device
	 * @param modifiers
	 * @param x
	 * @param y
	 * @param button
	 */
	public TabletEvent(Component source, Type type, long when, TabletDevice device, int modifiers, 
			float x, float y,
			int button) {
		this(source,type,when,modifiers,device,x,y,0,0,0,0,0,0,0,0,button);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getClass().getSimpleName());
		sb.append("[");
		sb.append("").append(type);
		sb.append(",when=").append(getWhen());
		sb.append(",device=").append(device);
		sb.append(",x=").append(x);
		sb.append(",y=").append(y);
		
        if (getModifiersEx() != 0) {
            sb.append(",modifiers=").append(getModifiersExText(getModifiersEx()));
        }
        if (pressure != 0) {
        	sb.append(",pressure=").append(pressure);
        }
        if (sidePressure != 0) {
            sb.append(",sidePressure=").append(sidePressure);
        }
        if (tiltX != 0 || tiltY != 0) {
            sb.append(",tilt=").append(tiltX).append(',').append(tiltY);
        }
        if (rotation != 0) {
        	sb.append(",rotation=").append(rotation);
        }
        if (scrollX != 0 || scrollY != 0) {
        	sb.append(",scroll=").append(scrollX).append(',').append(scrollY);
        }
        if (zoomFactor != 0) {
        	sb.append(",zoom=").append(zoomFactor);
        }
        sb.append("] on ").append(source);
        
		return sb.toString();
	}

	private static final int ID_START = RESERVED_ID_MAX + 1200;
	/**
	 * Type of input event this is
	 */
	public static enum Type {
		/** button/stylus tip pressed */
		PRESSED			( MOUSE_PRESSED ),
		/** button/stylus tip released */
		RELEASED		( MOUSE_RELEASED ),
		/** cursor enters proximity and/or component */
		ENTERED			( MOUSE_ENTERED ),
		/** cursor exits proximity and/or component*/
		EXITED			( MOUSE_EXITED ),
		/** cursor moved */
		MOVED			( MOUSE_MOVED ),
		/** cursor dragged */
		DRAGGED			( MOUSE_DRAGGED ),
		/** scrolled */
		SCROLLED		( MOUSE_WHEEL ),
		/** new device */
		NEW_DEVICE		( ID_START ),
		/** level changed */
		LEVEL_CHANGED	( ID_START+1 ),
		/** gestured */
		ZOOMED			( ID_START+2 ),
		/** gestured */
		ROTATED			( ID_START+3 ),
		/** gestured */
		SWIPED			( ID_START+4 )
		;
		
		private final int id;
		
		private Type(int id) {
			this.id = id;
		}
		/**
		 * Returns the AWTEvent id associated with this event type.
		 * @return the id
		 */
		public int getId() {
			return id;
		}
	}
	
	/**
	 * Triggers this event on the given {@link TabletListener}.
	 * 
	 * @param listener the listener to trigger on
	 */
	public void fireEvent(TabletListener listener) {
		switch (type) {
		case PRESSED:
			listener.cursorPressed(this);
			break;
		case RELEASED:
			listener.cursorReleased(this);
			break;
		case ENTERED:
			listener.cursorEntered(this);
			break;
		case EXITED:
			listener.cursorExited(this);
			break;
		case DRAGGED:
			listener.cursorDragged(this);
			break;
		case LEVEL_CHANGED:
			listener.levelChanged(this);
			break;
		case MOVED:
			listener.cursorMoved(this);
			break;
		case NEW_DEVICE:
			listener.newDevice(this);
			break;
		case SCROLLED:
			listener.cursorScrolled(this);
			break;
		case ZOOMED:
		case ROTATED:
		case SWIPED:
			listener.cursorGestured(this);
			break;
		}
	}
	
	/**
	 * Returns the fractional {@link Point2D} of the mouse/tablet cursor (if available).
	 * 
	 * @return the (possibly) fractional point
	 */
	public Point2D.Float getPoint2D() {
		return new Point2D.Float(x,y);
	}

	
	/**
	 * Returns the fractional x position of the mouse/tablet cursor (if available). 
	 * 
	 * @return the (possibly) fractional x coordinate
	 */
	public float getRealX() {
		return x;
	}
	/**
	 * Returns the fractional y position of the mouse/tablet cursor (if available).
	 * @return the (possibly) fractional y coordinate
	 */
	public float getRealY() {
		return y;
	}

	/**
	 * @return the pressure from 0 to 1
	 */
	public float getPressure() {
		return pressure;
	}

	/**
	 * Returns the tablet device associated with this event. You can determine capabilities available by inspecting the
	 * device object.
	 * 
	 * @see TabletDevice 
	 * @return the device
	 */
	public TabletDevice getDevice() {
		return device;
	}

	/**
	 * Returns the {@link Type} for this event. 
	 * @see Type
	 * @return the event type
	 */
	public Type getType() {
		return type;
	}


	/**
	 * Returns the current tablet side pressure. (E.g. the side wheel on a Wacom airbrush tool.) 
	 * @return side pressure from 0 to 1.
	 */
	public float getSidePressure() {
		return sidePressure;
	}


	/**
	 * @return tilt X in radians
	 */
	public float getTiltX() {
		return tiltX;
	}


	/**
	 * @return tilt Y in radians
	 */
	public float getTiltY() {
		return tiltY;
	}
	
	/**
	 * Returns either the rotational position of a tablet, or the rotational amount for a {@link Type#ROTATED} gesture 
	 * event.
	 * @return rotation in radians
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * Returns a copy of this {@linkplain TabletEvent} with the given component and coordinates.
	 * 
	 * @see #withPoint(float, float)
	 * @param component
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return a new {@linkplain TabletEvent} with the new component and coordinates
	 */
	public TabletEvent withPoint(Component component, float x, float y) {
		return new TabletEvent(component, type, getWhen(), getModifiersEx(), 
				device, x, y, pressure,
				tiltX, tiltY, sidePressure,
				rotation,this.scrollX,this.scrollY,zoomFactor,
				getButton());
	}

	/**
	 * Returns a copy of this {@linkplain TabletEvent} with the given coordinates.
	 * @see #withPoint(Component, float, float)
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return a new {@linkplain TabletEvent} with the new coordinates
	 */
	public TabletEvent withPoint(float x, float y) {
		return withPoint(getComponent(), x, y);
	}

	/**
	 * Returns a copy of this {@linkplain TabletEvent} with translated coordinates.
	 * 
	 * @see #translated(float, float)
	 * @param c component for translated event
	 * @param deltaX the amount to translate the x-coordinate
	 * @param deltaY the amount to translate the y-coordinate
	 * @return a new {@linkplain TabletEvent} with the new component and coordinates
	 */
	public TabletEvent translated(Component c, float deltaX, float deltaY) {
		return withPoint(c, x+deltaX, y+deltaY);
	}

	/**
	 * Returns a copy of this {@linkplain TabletEvent} with translated coordinates.
	 * @see #translated(Component, float, float)
	 * @param deltaX the amount to translate the x-coordinate
	 * @param deltaY the amount to translate the y-coordinate
	 * @return a new {@linkplain TabletEvent} with the new coordinates
	 */
	public TabletEvent translated(float deltaX, float deltaY) {
		return translated(getComponent(), deltaX, deltaY);
	}
	/**
	 * Returns a copy of this {@linkplain TabletEvent} with a new event {@link Type}.
	 * @param type the new event type
	 * @return a new {@linkplain TabletEvent} with the given type
	 */
	public TabletEvent withType(Type type) {
		return new TabletEvent((Component)source, type, getWhen(), getModifiersEx(), 
				device, x + scrollX, y + scrollY, pressure,
				tiltX, tiltY, sidePressure,
				rotation,this.scrollX,this.scrollY,zoomFactor,
				getButton());
	}


	/**
	 * Returns the amount scrolled in the x direction for a {@link Type#SCROLLED} gesture event.
	 * @return the amount scrolled in the x direction
	 */
	public float getScrollX() {
		return scrollX;
	}

	/**
	 * Returns the amount scrolled in the y direction for a {@link Type#SCROLLED} gesture event.
	 * @return the amount scrolled in the y direction
	 */
	public float getScrollY() {
		return scrollY;
	}

	/**
	 * Returns the magnification amount for a {@link Type#ZOOMED} gesture event. 
	 * @return the zoom
	 */
	public float getZoomFactor() {
		return zoomFactor;
	}
}