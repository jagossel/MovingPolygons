package org.logicallycreative.movingpolygons.managers.color;

public class StaticColor extends ColorManager {

	public StaticColor() {
		super();
		super.setRandomColorValues();
	}

	@Override
	public void changeColors() {
		// Static color, do not change the colors.
	}

}
