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
package org.logicallycreative.mplw.factories;

import org.logicallycreative.mplw.common.ColoringMethods;
import org.logicallycreative.mplw.managers.ColorManager;
import org.logicallycreative.mplw.managers.SawtoothWave;
import org.logicallycreative.mplw.managers.SineWave;
import org.logicallycreative.mplw.managers.StaticColor;

public class ColorManagerFactory {
	public static ColorManager getColorManager(String coloringMethod, int minimumColorValue, int maximumColorValue) {
		ColorManager colorManager = new StaticColor(minimumColorValue, maximumColorValue);
		if (coloringMethod == ColoringMethods.Sine) {
			colorManager = new SineWave(minimumColorValue, maximumColorValue);
		} else if (coloringMethod == ColoringMethods.Sawtooth) {
			colorManager = new SawtoothWave(minimumColorValue, maximumColorValue);
		}

		return colorManager;
	}
}