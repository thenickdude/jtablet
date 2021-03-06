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

/**
 * A convenience class that implements {@link TabletListener} as a funnel to a single abstract method you can implement
 * to handle all events. Override and implement the {@link #handleEvent(TabletEvent)} method to receive all events in 
 * one place.
 * 
 * <p>Example:
 * <pre>{@link TabletListener} listener = new {@link TabletFunneler}() {
 *     protected void {@link #handleEvent(TabletEvent) handleEvent}({@link TabletEvent} ev) {
 *         System.out.println(ev.toString());
 *     }		
 * }
 * </pre>
 * 
 * 
 * @author marcello
 */

public abstract class TabletFunneler implements TabletListener {

	public void cursorDragged(TabletEvent ev) {
		handleEvent(ev);
	}
	public void cursorEntered(TabletEvent ev) {
		handleEvent(ev);
	}
	public void cursorExited(TabletEvent ev) {
		handleEvent(ev);
	}
	public void cursorGestured(TabletEvent ev) {
		handleEvent(ev);
	}
	public void cursorMoved(TabletEvent ev) {
		handleEvent(ev);
	}
	public void cursorPressed(TabletEvent ev) {
		handleEvent(ev);
	}
	public void cursorReleased(TabletEvent ev) {
		handleEvent(ev);
	}
	public void cursorScrolled(TabletEvent ev) {
		handleEvent(ev);
	}
	public void levelChanged(TabletEvent ev) {
		handleEvent(ev);
	}
	/**
	 * Override this method to receive all possible {@link TabletEvent}s that would go to this listener.
	 * @param ev
	 */
	protected abstract void handleEvent(TabletEvent ev);
}
