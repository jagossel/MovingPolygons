//Moving Polygons Live Wallpaper
//Copyright (C) 2013  LogicallyCreative.org
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
package org.logicallycreative.movingpolygons.managers.color;
import org.logicallycreative.movingpolygons.util.*;

public class SineColorManager extends ColorManager {
	private final float sinePositionStart = (float) (Math.PI * -1);
	private final float sinePositionEnd = (float) (Math.PI);
	private final float precisionIncrement = 0.01f;
	
	private float redSinePosition;
	private float greenSinePosition;
	private float blueSinePosition;
	
	public SineColorManager() {
		redSinePosition = RandomNumberUtility.getRandomFloat(sinePositionStart, sinePositionEnd, 0f, precisionIncrement);
		greenSinePosition = RandomNumberUtility.getRandomFloat(sinePositionStart, sinePositionEnd, 0f, precisionIncrement);
		blueSinePosition = RandomNumberUtility.getRandomFloat(sinePositionStart, sinePositionEnd, 0f, precisionIncrement);
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
		
		super.redChannel = calculateColorValue(redSinePosition);
		super.greenChannel = calculateColorValue(greenSinePosition);
		super.blueChannel = calculateColorValue(blueSinePosition);
	}
	
	private int calculateColorValue(float sinePosition) {
		return (int) ((127 * Math.sin(sinePosition)) + 127);
	}
}
