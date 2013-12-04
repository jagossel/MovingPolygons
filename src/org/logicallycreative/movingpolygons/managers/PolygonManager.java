// Moving Polygons Live Wallpaper
// Copyright (C) 2013  LogicallyCreative.org
// 
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
package org.logicallycreative.movingpolygons.managers;

import java.util.List;

import org.logicallycreative.movingpolygons.data.DeltaPoint;
import org.logicallycreative.movingpolygons.data.Polygon;
import org.logicallycreative.movingpolygons.exceptions.MinimumIntegerLargerThanMaximumIntegerException;
import org.logicallycreative.movingpolygons.util.RandomNumberUtility;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.util.Log;

public class PolygonManager {
	private final Polygon polygon = new Polygon();
	private final Paint tempPaint = new Paint();

	private final int screenWidth;
	private final int screenHeight;

	public PolygonManager(int numerOfSides, int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;

		for (int i = 0; i < numerOfSides; i++) {
			try {
				int randomXCoordinate = RandomNumberUtility.getRandomInteger(0,
						this.screenWidth);
				int randomYCoordinate = RandomNumberUtility.getRandomInteger(0,
						this.screenHeight);

				DeltaPoint newPoint = new DeltaPoint(randomXCoordinate,
						randomYCoordinate, 1, 1);

				this.polygon.addPoint(newPoint);
			} catch (MinimumIntegerLargerThanMaximumIntegerException rangeEx) {
				Log.e("LogicallyCreativeOrg_MovingPolygons",
						rangeEx.getMessage());
			}
		}

		// TODO: For now, use a simple white line.
		this.tempPaint.setARGB(255, 255, 255, 255);
		this.tempPaint.setAntiAlias(true);
		this.tempPaint.setStrokeCap(Cap.SQUARE);
		this.tempPaint.setStrokeWidth(1);
	}

	public void movePoints() {
		List<DeltaPoint> points = this.polygon.getPoints();
		for (int i = 0; i < points.size(); i++) {
			DeltaPoint point = points.get(i);

			int currentX = point.getXCoordinate();
			int currentY = point.getYCoordinate();
			int deltaX = point.getDeltaX();
			int deltaY = point.getDeltaY();

			int newX = currentX + deltaX;
			if (newX > this.screenWidth) {
				newX = this.screenWidth;
				point.changeDeltaXDirection();
			} else if (newX < 0) {
				newX = 0;
				point.changeDeltaXDirection();
			}

			int newY = currentY + deltaY;
			if (newY > this.screenHeight) {
				newY = this.screenHeight;
				point.changeDeltaYDirection();
			} else if (newY < 0) {
				newY = 0;
				point.changeDeltaYDirection();
			}

			point.setCoordinates(newX, newY);
		}
	}

	public void drawPolygon(Canvas canvas) {
		List<DeltaPoint> points = this.polygon.getPoints();
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

			canvas.drawLine(startX, startY, endX, endY, this.tempPaint);
		}
	}
}