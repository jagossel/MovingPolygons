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
package org.logicallycreative.movingpolygons.loaders;

import java.util.ArrayList;
import java.util.List;

import org.logicallycreative.movingpolygons.common.ColoringMethods;
import org.logicallycreative.movingpolygons.data.engine.EngineData;
import org.logicallycreative.movingpolygons.data.engine.SettingsData;
import org.logicallycreative.movingpolygons.data.shape.DeltaPoint;
import org.logicallycreative.movingpolygons.managers.color.Colorable;
import org.logicallycreative.movingpolygons.managers.color.SawtoothWave;
import org.logicallycreative.movingpolygons.managers.color.SineWave;
import org.logicallycreative.movingpolygons.managers.color.StaticColor;
import org.logicallycreative.movingpolygons.managers.drawing.Echoes;
import org.logicallycreative.movingpolygons.managers.drawing.Polygon;
import org.logicallycreative.movingpolygons.managers.drawing.Shapable;
import org.logicallycreative.movingpolygons.util.RandomNumberUtility;

public class EngineLoader {
	private final SettingsData settings;

	public EngineLoader() {
		settings = EngineData.settings;
	}

	public Colorable getColorManager() {
		String coloringMethod = settings.getColoringMethod();

		if (coloringMethod == ColoringMethods.Sawtooth) {
			return new SawtoothWave();
		} else if (coloringMethod == ColoringMethods.Static) {
			return new StaticColor();
		} else {
			return new SineWave();
		}
	}

	public Shapable getShapeManager() {
		List<DeltaPoint> startingPoints = createStartingPoints();

		Shapable shapeManager = null;
		if (settings.getAddEchoes()) {
			shapeManager = getEchoesShapeManager();
		} else {
			shapeManager = getPolygonManager();
		}

		shapeManager.addPoints(startingPoints);

		return shapeManager;
	}

	private List<DeltaPoint> createStartingPoints() {
		List<DeltaPoint> startingPoints = new ArrayList<DeltaPoint>();

		int numberOfSides = settings.getPointCount();
		for (int i = 0; i < numberOfSides; i++) {
			int xCoordinate = RandomNumberUtility.getRandomInteger(0,
					EngineData.screenWidth);
			int yCoordinate = RandomNumberUtility.getRandomInteger(0,
					EngineData.screenHeight);

			startingPoints.add(new DeltaPoint(xCoordinate, yCoordinate, 1, 1));
		}

		return startingPoints;
	}

	private Shapable getEchoesShapeManager() {
		int numberOfEchoes = settings.getEchoCount();
		int spacing = settings.getEchoSpacing();

		return new Echoes(numberOfEchoes, spacing);
	}

	private Shapable getPolygonManager() {
		return new Polygon();
	}
}
