package org.logicallycreative.movingpolygons.widgets;

import org.logicallycreative.movingpolygons.common.DefaultSettings;
import org.logicallycreative.movingpolygons.common.SettingNames;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;

public class SpeedSeekbarPreference extends SeekBarPreference {

	public SpeedSeekbarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected int getMinimumValue() {
		return 1;
	}

	@Override
	protected int getMaximumValue() {
		return 10;
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
