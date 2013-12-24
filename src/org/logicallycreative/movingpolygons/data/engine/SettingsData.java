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
import org.logicallycreative.movingpolygons.managers.color.SineWave;
import org.logicallycreative.movingpolygons.managers.drawing.Echoes;
import org.logicallycreative.movingpolygons.managers.drawing.Polygon;
import org.logicallycreative.movingpolygons.managers.drawing.Shapable;
import org.logicallycreative.movingpolygons.util.RandomNumberUtility;

public class SettingsData {
	public static Colorable getColorManager() {
		return new SineWave();
	}

	public static Shapable getShapeManager() {
		boolean addEchoes = addEchoes();

		List<DeltaPoint> startingPoints = createStartingPoints();
		Shapable shapeManager = addEchoes ? getEchoesShapeManager()
				: getPolygonManager();

		shapeManager.addPoints(startingPoints);

		return shapeManager;
	}

	private static Shapable getEchoesShapeManager() {
		int numberOfEchoes = getEchoCount();
		int spacing = getSpacing();

		return new Echoes(numberOfEchoes, spacing);
	}

	private static Shapable getPolygonManager() {
		return new Polygon();
	}

	private static List<DeltaPoint> createStartingPoints() {
		List<DeltaPoint> startingPoints = new ArrayList<DeltaPoint>();

		int numberOfSides = SettingsData.getNumberOfPoints();
		for (int i = 0; i < numberOfSides; i++) {
			int xCoordinate = RandomNumberUtility.getRandomInteger(0,
					EngineData.screenWidth);
			int yCoordinate = RandomNumberUtility.getRandomInteger(0,
					EngineData.screenHeight);

			startingPoints.add(new DeltaPoint(xCoordinate, yCoordinate, 1, 1));
		}

		return startingPoints;
	}

	private static boolean addEchoes() {
		return true;
	}

	private static int getEchoCount() {
		return RandomNumberUtility.getRandomInteger(3, 10);
	}

	private static int getSpacing() {
		return RandomNumberUtility.getRandomInteger(5, 10);
	}

	private static int getNumberOfPoints() {
		return RandomNumberUtility.getRandomInteger(3, 8);
	}

	public static int getRedValue() {
		return getRandomColorValue();
	}

	public static int getGreeValue() {
		return getRandomColorValue();
	}

	public static int getBlueValue() {
		return getRandomColorValue();
	}

	private static int getRandomColorValue() {
		int minimum = getMinimumColorValue();
		int maximum = getMaximumColorValue();

		return RandomNumberUtility.getRandomInteger(minimum, maximum);
	}

	public static int getMinimumColorValue() {
		return 63;
	}

	public static int getMaximumColorValue() {
		return 255;
	}
}
