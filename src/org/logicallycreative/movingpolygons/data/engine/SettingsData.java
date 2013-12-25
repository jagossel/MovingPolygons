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
package org.logicallycreative.movingpolygons.data.engine;

import java.util.ArrayList;
import java.util.List;

import org.logicallycreative.movingpolygons.data.shape.DeltaPoint;
import org.logicallycreative.movingpolygons.managers.color.Colorable;
import org.logicallycreative.movingpolygons.managers.color.SawtoothWave;
import org.logicallycreative.movingpolygons.managers.color.SineWave;
import org.logicallycreative.movingpolygons.managers.color.StaticColor;
import org.logicallycreative.movingpolygons.managers.drawing.Echoes;
import org.logicallycreative.movingpolygons.managers.drawing.Polygon;
import org.logicallycreative.movingpolygons.managers.drawing.Shapable;
import org.logicallycreative.movingpolygons.util.RandomNumberUtility;

import android.content.SharedPreferences;

public class SettingsData {
	private final String echoCountSettingName = "echoes_count";
	private final String echoSpacingSettingName = "echo_spacing";
	private final String pointCountSettingName = "polygon_point_count";
	private final String colorMethodSettingName = "color_change_method";

	private final String coloringMethodSineWave = "SineWave";
	private final String coloringMethodSawtoothWave = "SawtoothWave";
	private final String coloringMethodStatic = "Static";

	private final boolean addEchoes;
	private final int echoCount;
	private final int echoSpacing;
	private final int pointCount;
	private final String coloringMethodName;

	public SettingsData(SharedPreferences sharedPreferences) {
		String echoCountSetting = sharedPreferences.getString(
				echoCountSettingName, "-1");
		String echoSpacingSetting = sharedPreferences.getString(
				echoSpacingSettingName, "-1");
		String pointCountSetting = sharedPreferences.getString(
				pointCountSettingName, "-1");
		coloringMethodName = sharedPreferences.getString(
				colorMethodSettingName, coloringMethodSineWave);

		echoCount = Integer.parseInt(echoCountSetting);
		addEchoes = echoCount != -2;

		echoSpacing = Integer.parseInt(echoSpacingSetting);
		pointCount = Integer.parseInt(pointCountSetting);
	}

	public Colorable getColorManager() {
		if (coloringMethodName == coloringMethodSawtoothWave) {
			return new SawtoothWave();
		} else if (coloringMethodName == coloringMethodStatic) {
			return new StaticColor();
		} else {
			return new SineWave();
		}
	}

	public Shapable getShapeManager() {
		List<DeltaPoint> startingPoints = createStartingPoints();

		Shapable shapeManager = null;
		if (addEchoes) {
			shapeManager = getEchoesShapeManager();
		} else {
			shapeManager = getPolygonManager();
		}

		shapeManager.addPoints(startingPoints);

		return shapeManager;
	}

	private Shapable getEchoesShapeManager() {
		int numberOfEchoes = getEchoCount();
		int spacing = getSpacing();

		return new Echoes(numberOfEchoes, spacing);
	}

	private Shapable getPolygonManager() {
		return new Polygon();
	}

	private List<DeltaPoint> createStartingPoints() {
		List<DeltaPoint> startingPoints = new ArrayList<DeltaPoint>();

		int numberOfSides = getNumberOfPoints();
		for (int i = 0; i < numberOfSides; i++) {
			int xCoordinate = RandomNumberUtility.getRandomInteger(0,
					EngineData.screenWidth);
			int yCoordinate = RandomNumberUtility.getRandomInteger(0,
					EngineData.screenHeight);

			startingPoints.add(new DeltaPoint(xCoordinate, yCoordinate, 1, 1));
		}

		return startingPoints;
	}

	private int getEchoCount() {
		if (echoCount == -2) {
			return 1;
		} else if (echoCount == -1) {
			return RandomNumberUtility.getRandomInteger(4, 11);
		} else {
			return echoCount;
		}
	}

	private int getSpacing() {
		if (echoSpacing == -1) {
			return RandomNumberUtility.getRandomInteger(5, 10);
		} else {
			return echoSpacing;
		}
	}

	private int getNumberOfPoints() {
		if (pointCount == -1) {
			return RandomNumberUtility.getRandomInteger(3, 8);
		} else {
			return pointCount;
		}
	}

	public int getRedValue() {
		return getRandomColorValue();
	}

	public int getGreeValue() {
		return getRandomColorValue();
	}

	public int getBlueValue() {
		return getRandomColorValue();
	}

	private int getRandomColorValue() {
		int minimum = getMinimumColorValue();
		int maximum = getMaximumColorValue();

		return RandomNumberUtility.getRandomInteger(minimum, maximum);
	}

	public int getMinimumColorValue() {
		return 63;
	}

	public int getMaximumColorValue() {
		return 255;
	}
}
