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

import java.util.List;

import org.logicallycreative.movingpolygons.data.engine.EngineData;
import org.logicallycreative.movingpolygons.data.shape.DeltaPoint;
import org.logicallycreative.movingpolygons.data.shape.Shape;
import org.logicallycreative.movingpolygons.data.shape.ShapeColor;
import org.logicallycreative.movingpolygons.managers.color.Colorable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Polygon implements Shapable {
	private final Shape polygon = new Shape();
	private final Paint linePaint = new Paint();
	private final int alpha;

	public Polygon(int alphaValue) {
		alpha = alphaValue;
		
		linePaint.setAntiAlias(true);
		linePaint.setStrokeCap(Paint.Cap.SQUARE);
		linePaint.setStrokeWidth(1.5f);
	}

	@Override
	public void addPoints(List<DeltaPoint> points) {
		for (DeltaPoint point : points)
			polygon.addPoint(point);
	}

	@Override
	public void applyColorChange() {
		ShapeColor color = EngineData.colorManager.getColor();
		linePaint.setColor(Color.argb(alpha, color.red, color.green, color.blue));
	}

	@Override
	public void movePoints() {
		List<DeltaPoint> points = polygon.getPoints();
		for (DeltaPoint point : points) {
			int currentX = point.getXCoordinate();
			int currentY = point.getYCoordinate();
			int deltaX = point.getDeltaX();
			int deltaY = point.getDeltaY();

			int newX = currentX + deltaX;
			if (newX > EngineData.screenWidth) {
				newX = EngineData.screenWidth;
				point.changeDeltaXDirection();
			} else if (newX < 0) {
				newX = 0;
				point.changeDeltaXDirection();
			}

			int newY = currentY + deltaY;
			if (newY > EngineData.screenHeight) {
				newY = EngineData.screenHeight;
				point.changeDeltaYDirection();
			} else if (newY < 0) {
				newY = 0;
				point.changeDeltaYDirection();
			}

			point.setCoordinates(newX, newY);
		}
	}

	@Override
	public void drawPoints(Canvas canvas) {
		List<DeltaPoint> points = polygon.getPoints();
		int pointsCount = points.size();

		for (int i = 0; i < pointsCount; i++) {
			int startIndex = i;
			DeltaPoint startingPoint = points.get(startIndex);
			int startX = startingPoint.getXCoordinate();
			int startY = startingPoint.getYCoordinate();

			int endIndex = (i + 1) % pointsCount;
			DeltaPoint endingPoint = points.get(endIndex);
			int endX = endingPoint.getXCoordinate();
			int endY = endingPoint.getYCoordinate();

			canvas.drawLine(startX, startY, endX, endY, linePaint);
		}
	}
}
