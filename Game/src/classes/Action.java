package classes;

import java.awt.Point;
import java.util.List;

import ui.UIControl;

public interface Action {

	public default void onMouseClick(UIControl uiButton, int mouseButton) {
		this.onMouseClick(uiButton);
	}

	public default void onMouseReleased(UIControl uiButton, int mouseButton) {
		this.onMouseReleased(uiButton);
	}

	public default void onMouseDown(UIControl uiButton, int mouseButton) {
		this.onMouseDown(uiButton);
	}

	public default void onMouseDrag(UIControl uiButton, Point mouseOffset) {
		this.onMouseDrag(uiButton);
	}

	public default void onMouseDrag(UIControl uiButton, int mouseButton) {
		this.onMouseDrag(uiButton);
	}

	public default void onMouseDrag(UIControl uiButton) {
	}

	public default void onMouseClick(UIControl uiButton) {
	}
	public default void onMouseClickOut(UIControl uiButton) {
	}

	public default void onMouseReleased(UIControl uiButton) {
	}

	public default void onMouseDown(UIControl uiButton) {
	}

	public default void onMouseEnter(UIControl uiButton) {
	}

	public default void onMouseHover(UIControl uiButton) {
	}

	public default void onMouseExit(UIControl uiButton) {
	}

	public default void onMouseWheelUp(UIControl uiButton) {
	}

	public default void onMouseWheelDown(UIControl uiButton) {
	}

	public default void onSelect(UIControl self, int index) {

	}

	public default void onSelect(UIControl self) {

	}

	public default void onKeyPress(UIControl self, int keycode, boolean shift) {

	}
}
