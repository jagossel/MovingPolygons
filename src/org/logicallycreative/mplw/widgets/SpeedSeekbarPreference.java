package org.logicallycreative.mplw.widgets;

import org.logicallycreative.mplw.common.DefaultSettings;
import org.logicallycreative.mplw.common.SettingNames;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;

public class SpeedSeekbarPreference extends SeekBarPreference {
	private final int minimum = 1;
	private final int maximum = 10;

	public SpeedSeekbarPreference(Context context, AttributeSet attrs) {
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
		SharedPreferences sharedPreferences = super.getSharedPreferences();
		int preferenceValue = sharedPreferences.getInt(
				SettingNames.polygonSpeed, DefaultSettings.polygonSpeed);

		return preferenceValue;
	}

	@Override
	protected void savePreferenceValue(int value) {
		Editor editor = super.getEditor();
		editor.putInt(SettingNames.polygonSpeed, value);

		editor.commit();
	}

}
