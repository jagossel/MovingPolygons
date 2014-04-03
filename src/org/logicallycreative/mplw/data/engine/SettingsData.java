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
package org.logicallycreative.mplw.data.engine;

import org.logicallycreative.mplw.common.DefaultSettings;
import org.logicallycreative.mplw.common.SettingNames;
import org.logicallycreative.mplw.util.RandomNumberUtility;

import android.content.SharedPreferences;

public class SettingsData {
	private final boolean addEchoes;
	private final boolean setPointCount;
	private final boolean setEchoCount;
	private final boolean setEchoSpacing;
	private final int pointCount;
	private final int echoCount;
	private final int echoSpacing;
	private final String coloringMethod;

	public SettingsData(SharedPreferences settings) {
		addEchoes = settings.getBoolean(SettingNames.addEchoes,
				DefaultSettings.addEchoes);
		setPointCount = settings.getBoolean(SettingNames.setPointCount,
				DefaultSettings.setPointCount);
		setEchoCount = settings.getBoolean(SettingNames.setEchoCount,
				DefaultSettings.setEchoCount);
		setEchoSpacing = settings.getBoolean(SettingNames.setEchoSpacing,
				DefaultSettings.setEchoSpacing);

		if (setPointCount) {
			pointCount = settings.getInt(SettingNames.pointCount,
					DefaultSettings.pointCount);
		} else {
			pointCount = 0;
		}

		if (addEchoes && setEchoCount) {
			echoCount = settings.getInt(SettingNames.echoCount,
					DefaultSettings.echoCount);
		} else {
			echoCount = 0;
		}

		if (addEchoes && setEchoSpacing) {
			echoSpacing = settings.getInt(SettingNames.echoSpacing,
					DefaultSettings.echoSpacing);
		} else {
			echoSpacing = 0;
		}

		coloringMethod = settings.getString(SettingNames.coloringMethod,
				DefaultSettings.coloringMethod);
	}

	public boolean getAddEchoes() {
		return addEchoes;
	}

	public int getEchoCount() {
		if (addEchoes && setEchoCount) {
			return echoCount;
		} else if (addEchoes) {
			return RandomNumberUtility.getRandomInteger(4, 11);
		} else {
			return 0;
		}
	}

	public int getEchoSpacing() {
		if (addEchoes && setEchoSpacing) {
			return echoSpacing;
		} else if (addEchoes) {
			return RandomNumberUtility.getRandomInteger(5, 10);
		} else {
			return 0;
		}
	}

	public int getPointCount() {
		if (setPointCount) {
			return pointCount;
		} else {
			return RandomNumberUtility.getRandomInteger(3, 8);
		}
	}

	public String getColoringMethod() {
		return coloringMethod;
	}

	public int getMinimumColorValue() {
		return 63;
	}

	public int getMaximumColorValue() {
		return 255;
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
}
