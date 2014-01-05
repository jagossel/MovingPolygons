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

import org.logicallycreative.movingpolygons.data.engine.EngineData;

public class SawtoothWave extends ColorManager {
	private int redIncrement = 1;
	private int greenIncrement = 1;
	private int blueIncrement = 1;

	@Override
	public void changeColors() {
		EngineData.redColorValue += redIncrement;
		if (EngineData.redColorValue >= super.maximumColorValue) {
			EngineData.redColorValue = super.maximumColorValue;
			redIncrement *= -1;
		} else if (EngineData.redColorValue <= super.minimumColorValue) {
			EngineData.redColorValue = super.minimumColorValue;
			redIncrement *= -1;
		}

		EngineData.greenColorValue += greenIncrement;
		if (EngineData.greenColorValue >= super.maximumColorValue) {
			EngineData.greenColorValue = super.maximumColorValue;
			greenIncrement *= -1;
		} else if (EngineData.greenColorValue <= super.minimumColorValue) {
			EngineData.greenColorValue = super.minimumColorValue;
			greenIncrement *= -1;
		}

		EngineData.blueColorValue += blueIncrement;
		if (EngineData.blueColorValue >= super.maximumColorValue) {
			EngineData.blueColorValue = super.maximumColorValue;
			blueIncrement *= -1;
		} else if (EngineData.blueColorValue <= super.minimumColorValue) {
			EngineData.blueColorValue = super.minimumColorValue;
			blueIncrement *= -1;
		}

		super.setLinePaintColor();
	}
}
