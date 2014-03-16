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
package org.logicallycreative.mplw.widgets;

import org.logicallycreative.mplw.R;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

// TODO: Find a better way to make this class re-usable.
// I am not a fan of having sub-classes based off of this.  If there is a better way of
// doing this without having to have sub-classes for each settings, then by all means,
// do it.
public abstract class SeekBarPreference extends DialogPreference {
	private SeekBar preferenceSeekbar;
	private TextView valueTextView;
	private int preferenceValue = 0;
	private int minimum = 0;
	private int maximum = 100;

	public SeekBarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		setPersistent(false);
		setDialogLayoutResource(R.layout.seekbar_preference_layout);
	}

	@Override
	public void onBindDialogView(View view) {
		super.onBindDialogView(view);

		preferenceSeekbar = (SeekBar) view.findViewById(R.id.seekbarWidget);
		valueTextView = (TextView) view.findViewById(R.id.seekbarValue);
		setupSeekbar();
	}

	@Override
	public void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);

		if (!positiveResult)
			return;

		savePreferenceValue(preferenceValue);
	}

	protected abstract int getMinimumValue();

	protected abstract int getMaximumValue();

	protected abstract int getPreferenceValue();

	protected abstract void savePreferenceValue(int value);

	private void setupSeekbar() {
		minimum = getMinimumValue();
		maximum = getMaximumValue();

		setupSeekbarRange();
		setupSeekbarValue();
		setupSeekbarListeners();
	}

	private void setupSeekbarRange() {
		final int seekbarRange = maximum - minimum;
		preferenceSeekbar.setMax(seekbarRange);
	}

	private void setupSeekbarValue() {
		preferenceValue = getPreferenceValue();
		final int seekBarProgress = preferenceValue - minimum;

		valueTextView.setText(String.valueOf(preferenceValue));
		preferenceSeekbar.setProgress(seekBarProgress);
	}

	private void setupSeekbarListeners() {
		SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				int currentValue = progress + minimum;

				preferenceValue = currentValue;
				valueTextView.setText(String.valueOf(currentValue));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		};

		preferenceSeekbar.setOnSeekBarChangeListener(listener);
	}
}
