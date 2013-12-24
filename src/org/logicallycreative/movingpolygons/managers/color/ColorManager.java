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

import org.logicallycreative.movingpolygons.data.engine.SettingsData;

import android.graphics.Paint;

public abstract class ColorManager implements Colorable {
	protected final int minimumColorValue;
	protected final int maximumColorValue;
	protected final Paint linePaint = new Paint();

	protected int alphaChannel;
	protected int redChannel;
	protected int greenChannel;
	protected int blueChannel;

	public ColorManager() {
		minimumColorValue = SettingsData.getMinimumColorValue();
		maximumColorValue = SettingsData.getMaximumColorValue();

		linePaint.setAntiAlias(true);
		linePaint.setStrokeCap(Paint.Cap.SQUARE);
		linePaint.setStrokeWidth(1f);
	}

	protected void setRandomColorValues() {
		redChannel = SettingsData.getRedValue();
		greenChannel = SettingsData.getGreeValue();
		blueChannel = SettingsData.getBlueValue();
	}

	@Override
	public abstract void changeColors();

	@Override
	public void setAlpha(int alpha) {
		alphaChannel = alpha;
	}

	@Override
	public final Paint getLinePaint() {
		linePaint.setARGB(alphaChannel, redChannel, greenChannel, blueChannel);

		return linePaint;
	}
}
