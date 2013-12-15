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

import org.logicallycreative.movingpolygons.util.RandomNumberUtility;

import android.graphics.Paint;

public class SawtoothColorManager implements ColorManagable {
	private final Paint linePaint = new Paint();

	private int redChannel;
	private int greenChannel;
	private int blueChannel;
	
	private int redIncrement = 1;
	private int greenIncrement = 1;
	private int blueIncrement = 1;

	public SawtoothColorManager() {
		redChannel = pickRandomColorValue();
		greenChannel = pickRandomColorValue();
		blueChannel = pickRandomColorValue();

		linePaint.setAntiAlias(true);
		linePaint.setStrokeCap(Paint.Cap.SQUARE);
		linePaint.setStrokeWidth(1);
	}

	private int pickRandomColorValue() {
		return RandomNumberUtility.getRandomInteger(0, 255, 255);
	}

	public void changeColors() {
		redChannel += redIncrement;
		if (redChannel >= 255) {
			redChannel = 255;
			redIncrement *= -1;
		} else if (redChannel <= 0) {
			redChannel = 0;
			redIncrement *= -1;
		}
		
		greenChannel += greenIncrement;
		if (greenChannel >= 255) {
			greenChannel = 255;
			greenIncrement *= -1;
		} else if (greenChannel <= 0) {
			greenChannel = 0;
			greenIncrement *= -1;
		}
		
		blueChannel += blueIncrement;
		if (blueChannel >= 255) {
			blueChannel = 255;
			blueIncrement *= -1;
		} else if (blueChannel <= 0) {
			blueChannel = 0;
			blueIncrement *= -1;
		}

		linePaint.setARGB(255, redChannel, greenChannel, blueChannel);
	}

	public Paint getLinePaint() {
		return linePaint;
	}
}
