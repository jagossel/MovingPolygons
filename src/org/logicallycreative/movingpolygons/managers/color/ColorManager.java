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

public abstract class ColorManager implements ColorManagable {
	protected final Paint linePaint = new Paint();
	
	protected int redChannel;
	protected int greenChannel;
	protected int blueChannel;
	
	public ColorManager() {
		redChannel = pickRandomColorValue();
		greenChannel = pickRandomColorValue();
		blueChannel = pickRandomColorValue();

		linePaint.setAntiAlias(true);
		linePaint.setStrokeCap(Paint.Cap.SQUARE);
		linePaint.setStrokeWidth(1.5f);
	}

	private int pickRandomColorValue() {
		return RandomNumberUtility.getRandomInteger(0, 255, 255);
	}

	public abstract void changeColors();

	public final Paint getLinePaint() {
		linePaint.setARGB(255, redChannel, greenChannel, blueChannel);

		return linePaint;
	}
}
