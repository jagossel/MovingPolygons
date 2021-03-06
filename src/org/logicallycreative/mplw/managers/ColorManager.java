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

import org.logicallycreative.mplw.data.shape.ShapeColor;
import org.logicallycreative.mplw.util.RandomNumberUtility;

public abstract class ColorManager {
	protected final int minimumColorValue;
	protected final int maximumColorValue;
	protected final ShapeColor currentColor = new ShapeColor();

	public ColorManager(int minimumColorValue, int maximumColorValue) {
		this.minimumColorValue = minimumColorValue;
		this.maximumColorValue = maximumColorValue;
	}

	public abstract void changeColors();

	public ShapeColor getColor() {
		return currentColor;
	}

	protected void setRandomColorValues() {
		currentColor.red = RandomNumberUtility.getRandomInteger(minimumColorValue, maximumColorValue);
		currentColor.green = RandomNumberUtility.getRandomInteger(minimumColorValue, maximumColorValue);
		currentColor.blue = RandomNumberUtility.getRandomInteger(minimumColorValue, maximumColorValue);
	}
}