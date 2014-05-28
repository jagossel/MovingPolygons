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
package org.logicallycreative.mplw.factories;

import org.logicallycreative.mplw.common.DefaultSettings;
import org.logicallycreative.mplw.common.SettingNames;
import org.logicallycreative.mplw.common.SettingRanges;
import org.logicallycreative.mplw.data.engine.SettingsData;
import org.logicallycreative.mplw.util.RandomNumberUtility;

import android.content.SharedPreferences;

public class SettingsDataFactory {
	public static SettingsData getSettingsDataFromSharedPreferences(SharedPreferences preferences) {
		SettingsData settings = new SettingsData();
		setPointCount(settings, preferences);
		setEchoCount(settings, preferences);
		setEchoSpacing(settings, preferences);
		setColoringMethod(settings, preferences);

		return settings;
	}

	private static void setPointCount(SettingsData settings, SharedPreferences preferences) {
		boolean setPointCount = preferences.getBoolean(SettingNames.setPointCount, DefaultSettings.setEchoCount);

		int pointCount = DefaultSettings.pointCount;
		if (setPointCount) {
			pointCount = preferences.getInt(SettingNames.pointCount, DefaultSettings.pointCount);
		} else {
			pointCount = RandomNumberUtility.getRandomInteger(SettingRanges.minimumPointCount,
				SettingRanges.maximumPointCount);
		}

		settings.pointCount = pointCount;
	}

	private static void setEchoCount(SettingsData settings, SharedPreferences preferences) {
		boolean addEchoes = preferences.getBoolean(SettingNames.addEchoes, DefaultSettings.addEchoes);
		boolean setEchoCount = preferences.getBoolean(SettingNames.setEchoCount, DefaultSettings.setEchoCount);

		int echoCount = 0;
		if (addEchoes && setEchoCount) {
			echoCount = preferences.getInt(SettingNames.echoCount, DefaultSettings.echoCount);
		} else if (addEchoes && !setEchoCount) {
			echoCount = RandomNumberUtility.getRandomInteger(SettingRanges.minimumEchoCount,
				SettingRanges.maximumEchoCount);
		}

		settings.echoCount = echoCount;
	}

	private static void setEchoSpacing(SettingsData settings, SharedPreferences preferences) {
		boolean addEchoes = preferences.getBoolean(SettingNames.addEchoes, DefaultSettings.addEchoes);
		boolean setEchoSpacing = preferences.getBoolean(SettingNames.setEchoSpacing, DefaultSettings.setEchoSpacing);

		int echoSpacing = 0;
		if (addEchoes && setEchoSpacing) {
			echoSpacing = preferences.getInt(SettingNames.echoSpacing, DefaultSettings.echoSpacing);
		} else if (addEchoes && !setEchoSpacing) {
			echoSpacing = RandomNumberUtility.getRandomInteger(SettingRanges.minimumEchoSpacing,
				SettingRanges.maximumEchoSpacing);
		}

		settings.echoSpacing = echoSpacing;
	}

	private static void setColoringMethod(SettingsData settings, SharedPreferences preferences) {
		String coloringMethod = preferences.getString(SettingNames.coloringMethod, DefaultSettings.coloringMethod);
		settings.coloringMethod = coloringMethod;
	}
}