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
package org.logicallycreative.mplw.widgets;

import org.logicallycreative.mplw.common.DefaultSettings;
import org.logicallycreative.mplw.common.SettingNames;
import org.logicallycreative.mplw.common.SettingRanges;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;

public class EchoSpacingSeekbarPreference extends SeekBarPreference {
	private final int minimum = SettingRanges.minimumEchoSpacing;
	private final int maximum = SettingRanges.maximumEchoSpacing;

	public EchoSpacingSeekbarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected int getMinimumValue() {
		return minimum;
	}

	@Override
	protected int getMaximumValue() {
		return maximum;
	}

	@Override
	protected int getPreferenceValue() {
		SharedPreferences preferences = super.getSharedPreferences();
		int preferenceValue = preferences.getInt(SettingNames.echoSpacing, DefaultSettings.echoSpacing);

		return preferenceValue;
	}

	@Override
	protected void savePreferenceValue(int value) {
		Editor editor = super.getEditor();
		editor.putInt(SettingNames.echoSpacing, value);

		editor.commit();
	}

}