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
package org.logicallycreative.mplw;

import java.util.ArrayList;
import java.util.List;

import org.logicallycreative.mplw.data.engine.SettingsData;
import org.logicallycreative.mplw.data.shape.DeltaPoint;
import org.logicallycreative.mplw.data.shape.Shape;
import org.logicallycreative.mplw.data.shape.ShapeColor;
import org.logicallycreative.mplw.factories.ColorManagerFactory;
import org.logicallycreative.mplw.managers.ColorManager;
import org.logicallycreative.mplw.util.RandomNumberUtility;

public class MovingPolygonsEngine {
	private static MovingPolygonsEngine instance;

	public static MovingPolygonsEngine getInstance() {
		if (instance == null) {
			instance = new MovingPolygonsEngine();
		}

		return instance;
	}

	private final List<Integer> calculatedAlphaValues = new ArrayList<Integer>();
	private final List<Shape> polygons = new ArrayList<Shape>();
	private ColorManager colorManager;
	private SettingsData settings;
	private int screenWidth;
	private int screenHeight;

	private MovingPolygonsEngine() {}

	public void setDimensions(int width, int height) {
		screenWidth = width;
		screenHeight = height;
	}

	public void initializeEngine(SettingsData settingsData) {
		settings = settingsData;

		initializeShapes();
		initializeColorManager();
		calculateAlphaValues();
	}

	public void calculateNextFrameData() {
		movePolygons();
		changeColor();
	}

	public List<List<DeltaPoint>> getDrawingData() {
		List<List<DeltaPoint>> drawingData = new ArrayList<List<DeltaPoint>>();

		for (Shape polygon : polygons) {
			List<DeltaPoint> points = polygon.getPoints();
			drawingData.add(points);
		}

		return drawingData;
	}

	public List<Integer> getAlphaValues() {
		return calculatedAlphaValues;
	}

	public ShapeColor getCurrentShapeColor() {
		return colorManager.getColor();
	}

	private void initializeShapes() {
		Shape basePolygon = initializeBaseShape();

		polygons.clear();
		polygons.add(basePolygon);
		addEchoes(basePolygon);
	}

	private Shape initializeBaseShape() {
		int pointCount = settings.pointCount;
		Shape basePolygon = new Shape();

		int echoSpacing = settings.echoSpacing;
		int echoCount = settings.echoCount;
		int offset = echoSpacing * echoCount;

		int minimumXOffset = offset;
		int maximumXOffset = screenWidth - offset;
		int minimumYOffset = offset;
		int maximumYOffset = screenHeight - offset;

		for (int i = 0; i < pointCount; i++) {
			int x = RandomNumberUtility.getRandomInteger(minimumXOffset, maximumXOffset);
			int y = RandomNumberUtility.getRandomInteger(minimumYOffset, maximumYOffset);

			DeltaPoint point = new DeltaPoint(x, y, 1, 1);
			basePolygon.addPoint(point);
		}

		return basePolygon;
	}

	private void addEchoes(Shape basePolygon) {
		int echoCount = settings.echoCount;
		int echoSpacing = settings.echoSpacing;

		for (int i = 0; i < echoCount; i++) {
			int coordinateOffset = i * echoSpacing;
			Shape echoedShape = createEchoedShape(basePolygon, coordinateOffset);
			polygons.add(echoedShape);
		}
	}

	private Shape createEchoedShape(Shape baseShape, int coordinateOffset) {
		List<DeltaPoint> basePoints = baseShape.getPoints();
		int pointCount = basePoints.size();
		Shape echoedShape = new Shape();

		for (int i = 0; i < pointCount; i++) {
			DeltaPoint basePoint = basePoints.get(i);
			int baseX = basePoint.getXCoordinate();
			int baseY = basePoint.getYCoordinate();
			int x = baseX + coordinateOffset;
			int y = baseY + coordinateOffset;

			DeltaPoint point = new DeltaPoint(x, y, 1, 1);
			echoedShape.addPoint(point);
		}

		return echoedShape;
	}

	private void initializeColorManager() {
		String coloringMethod = settings.coloringMethod;
		int minimumColorValue = settings.minimumColorValue;
		int maximumColorValue = settings.maximumColorValue;

		colorManager = ColorManagerFactory.getColorManager(coloringMethod, minimumColorValue, maximumColorValue);
	}

	private void calculateAlphaValues() {
		int echoCount = settings.echoCount;
		int minimumColorValue = settings.minimumColorValue;
		int maximumColorValue = settings.maximumColorValue;
		int alphaValueCount = echoCount + 1;

		int alphaRange = maximumColorValue - minimumColorValue;
		int valueIncrement = alphaRange / alphaValueCount;

		calculatedAlphaValues.clear();
		for (int i = 0; i < alphaValueCount; i++) {
			int alphaValue = maximumColorValue - (valueIncrement * i);
			calculatedAlphaValues.add(alphaValue);
		}
	}

	private void movePolygons() {
		for (Shape polygon : polygons) {
			movePolygon(polygon);
		}
	}

	private void changeColor() {
		colorManager.changeColors();
	}

	private void movePolygon(Shape polygon) {
		List<DeltaPoint> points = polygon.getPoints();
		for (DeltaPoint point : points) {
			movePoint(point);
		}
	}

	private void movePoint(DeltaPoint point) {
		int currentX = point.getXCoordinate();
		int currentY = point.getYCoordinate();
		int deltaX = point.getDeltaX();
		int deltaY = point.getDeltaY();

		int newX = currentX + deltaX;
		if (newX < 0) {
			newX = 0;
			point.changeDeltaXDirection();
		} else if (newX > screenWidth) {
			newX = screenWidth;
			point.changeDeltaXDirection();
		}

		int newY = currentY + deltaY;
		if (newY < 0) {
			newY = 0;
			point.changeDeltaYDirection();
		} else if (newY > screenHeight) {
			newY = screenHeight;
			point.changeDeltaYDirection();
		}

		point.setCoordinates(newX, newY);
	}
}