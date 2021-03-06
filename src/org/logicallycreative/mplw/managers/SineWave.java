// Moving Polygons Live Wallpaper
// Copyright (C) 2013 LogicallyCreative.org
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
package org.logicallycreative.mplw.managers;

import org.logicallycreative.mplw.util.RandomNumberUtility;

public class SineWave extends ColorManager {
	private final float precisionIncrement = 0.01f;
	private final float sinePositionStart;
	private final float sinePositionEnd;
	private final float sineRange;
	private final float sineCenter;

	private float redSinePosition;
	private float greenSinePosition;
	private float blueSinePosition;

	public SineWave(int minimumColorValue, int maximumColorValue) {
		super(minimumColorValue, maximumColorValue);

		sinePositionStart = (float) (Math.PI * -1f);
		sinePositionEnd = (float) Math.PI;

		sineRange = (super.maximumColorValue - super.minimumColorValue) / 2;
		sineCenter = sineRange + super.minimumColorValue;

		redSinePosition = RandomNumberUtility.getRandomFloat(sinePositionStart, sinePositionEnd, precisionIncrement);
		greenSinePosition = RandomNumberUtility.getRandomFloat(sinePositionStart, sinePositionEnd, precisionIncrement);
		blueSinePosition = RandomNumberUtility.getRandomFloat(sinePositionStart, sinePositionEnd, precisionIncrement);
	}

	@Override
	public void changeColors() {
		redSinePosition += precisionIncrement;
		if (redSinePosition > sinePositionEnd) {
			redSinePosition = sinePositionStart;
		}

		greenSinePosition += precisionIncrement;
		if (greenSinePosition > sinePositionEnd) {
			greenSinePosition = sinePositionStart;
		}

		blueSinePosition += precisionIncrement;
		if (blueSinePosition > sinePositionEnd) {
			blueSinePosition = sinePositionStart;
		}

		super.currentColor.red = calculateColorValue(redSinePosition);
		super.currentColor.green = calculateColorValue(greenSinePosition);
		super.currentColor.blue = calculateColorValue(blueSinePosition);
	}

	private int calculateColorValue(float sinePosition) {
		return (int) ((sineRange * Math.sin(sinePosition)) + sineCenter);
	}
}