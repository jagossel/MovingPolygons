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
package org.logicallycreative.movingpolygons.data;

public class DeltaPoint {
	private int xCoordinate;
	private int yCoordinate;
	private int deltaX;
	private int deltaY;

	public DeltaPoint(int startingXCoordinate, int startingYCoordinate,
			int startingDeltaX, int startingDeltaY) {
		xCoordinate = startingXCoordinate;
		yCoordinate = startingYCoordinate;
		deltaX = startingDeltaX;
		deltaY = startingDeltaY;
	}

	public int getXCoordinate() {
		return xCoordinate;
	}

	public int getYCoordinate() {
		return yCoordinate;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public void setCoordinates(int newXCoordinate, int newYCoordinate) {
		xCoordinate = newXCoordinate;
		yCoordinate = newYCoordinate;
	}

	public void changeDeltaXDirection() {
		deltaX *= -1;
	}

	public void changeDeltaYDirection() {
		deltaY *= -1;
	}
}
