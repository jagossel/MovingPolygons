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
package org.logicallycreative.movingpolygons.managers.drawing;

import java.util.ArrayList;
import java.util.List;

import org.logicallycreative.movingpolygons.data.engine.EngineData;
import org.logicallycreative.movingpolygons.data.shape.DeltaPoint;

import android.graphics.Canvas;

public class Echoes implements Shapable {
	private final List<Polygon> polygons = new ArrayList<Polygon>();

	private final int echoSpacing;

	public Echoes(int numberOfEchoes, int echoSpace) {
		echoSpacing = echoSpace;

		for (int i = 0; i < numberOfEchoes; i++)
			polygons.add(new Polygon());
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

			offsetPoints.add(new DeltaPoint(offsetXCoordinate,
					offsetYCoordinate, 1, 1));
		}

		polygon.addPoints(offsetPoints);
	}

	@Override
	public void movePoints() {
		for (Polygon polygon : polygons)
			polygon.movePoints();
	}

	@Override
	public void drawPoints(Canvas canvas) {
		int echoCount = polygons.size();
		int scale = 127 / echoCount;

		for (int i = 0; i < echoCount; i++) {
			Polygon polygon = polygons.get(i);
			int alpha = ((echoCount - i) * scale) + 127;

			EngineData.colorManager.setAlpha(alpha);
			polygon.drawPoints(canvas);
		}
	}
}
