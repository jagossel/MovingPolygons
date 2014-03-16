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
package org.logicallycreative.mplw.util;

import java.util.Random;

public class RandomNumberUtility {
	private static final Random randomNumberGenerator = new Random();

	public static int getRandomInteger(int minimumInteger, int maximumInteger) {
		if (minimumInteger > maximumInteger) {
			int temporaryHolder = maximumInteger;
			maximumInteger = minimumInteger;
			minimumInteger = temporaryHolder;
		}

		int range = maximumInteger - minimumInteger;
		int randomInteger = randomNumberGenerator.nextInt(range);
		int randomNumber = randomInteger + minimumInteger;

		return randomNumber;
	}

	public static float getRandomFloat(float minimumFloat, float maximumFloat,
			float precision) {
		if (minimumFloat > maximumFloat) {
			float temporaryHolder = maximumFloat;
			maximumFloat = minimumFloat;
			minimumFloat = temporaryHolder;
		}

		float range = maximumFloat - minimumFloat;
		float randomFloat = randomNumberGenerator.nextFloat() * range;
		float randomNumber = randomFloat + minimumFloat;

		float decimalShiftRight = randomNumber / precision;
		float roundedFloat = Math.round(decimalShiftRight);
		float decimalShiftLeft = roundedFloat * precision;

		return decimalShiftLeft;
	}
}
