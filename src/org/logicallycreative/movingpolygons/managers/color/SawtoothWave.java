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

public class SawtoothWave extends ColorManager {
	private int redIncrement = 1;
	private int greenIncrement = 1;
	private int blueIncrement = 1;

	public SawtoothWave() {
		super();
		super.setRandomColorValues(0, 255, 127);
	}

	@Override
	public void changeColors() {
		super.redChannel += redIncrement;
		if (super.redChannel >= 255) {
			super.redChannel = 255;
			redIncrement *= -1;
		} else if (super.redChannel <= 0) {
			super.redChannel = 0;
			redIncrement *= -1;
		}

		super.greenChannel += greenIncrement;
		if (super.greenChannel >= 255) {
			super.greenChannel = 255;
			greenIncrement *= -1;
		} else if (super.greenChannel <= 0) {
			super.greenChannel = 0;
			greenIncrement *= -1;
		}

		super.blueChannel += blueIncrement;
		if (super.blueChannel >= 255) {
			super.blueChannel = 255;
			blueIncrement *= -1;
		} else if (super.blueChannel <= 0) {
			super.blueChannel = 0;
			blueIncrement *= -1;
		}
	}
}
