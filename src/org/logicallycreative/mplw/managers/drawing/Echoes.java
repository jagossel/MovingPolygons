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
package org.logicallycreative.mplw.managers.drawing;

import java.util.ArrayList;
import java.util.List;

import org.logicallycreative.mplw.data.engine.EngineData;
import org.logicallycreative.mplw.data.shape.DeltaPoint;

import android.graphics.Canvas;

public class Echoes implements Shapable {
	private final List<Polygon> polygons = new ArrayList<Polygon>();
	private final int echoSpacing;

	public Echoes(int numberOfEchoes, int echoSpace) {
		echoSpacing = echoSpace;

		int minimumColorValue = EngineData.settings.getMinimumColorValue();
		int maximumColorValue = EngineData.settings.getMaximumColorValue();
		int alphaRange = maximumColorValue - minimumColorValue;
		int alphaScalingFactor = alphaRange / numberOfEchoes;

		for (int i = 0; i < numberOfEchoes; i++) {
			int alphaColorValue = ((numberOfEchoes - i) * alphaScalingFactor)
					+ minimumColorValue;

			polygons.add(new Polygon(alphaColorValue));
		}
	}

	@Override
	public void addPoints(List<DeltaPoint> points) {
		int factor = 0;
		for (Polygon polygon : polygons) {
			addEchoedPoints(points, polygon, factor);
			factor++;
		}
	}

	private void addEchoedPoints(List<DeltaPoint> basePoints, Polygon polygon,
			int factor) {
		int coordinateOffset = -1 * factor * echoSpacing;

		List<DeltaPoint> offsetPoints = new ArrayList<DeltaPoint>();
		for (DeltaPoint basePoint : basePoints) {
			int baseXCoordinate = basePoint.getXCoordinate();
			int baseYCoordinate = basePoint.getYCoordinate();

			int offsetXCoordinate = baseXCoordinate + coordinateOffset;
			int offsetYCoordinate = baseYCoordinate + coordinateOffset;

			int polygonSpeed = EngineData.settings.getPolygonSpeed();

			offsetPoints.add(new DeltaPoint(offsetXCoordinate,
					offsetYCoordinate, polygonSpeed, polygonSpeed));
		}

		polygon.addPoints(offsetPoints);
	}

	@Override
	public void applyColorChange() {
		for (Polygon polygon : polygons)
			polygon.applyColorChange();
	}

	@Override
	public void movePoints() {
		for (Polygon polygon : polygons)
			polygon.movePoints();
	}

	@Override
	public void drawPoints(Canvas canvas) {
		for (Polygon polygon : polygons)
			polygon.drawPoints(canvas);
	}
}
