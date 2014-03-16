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

import org.logicallycreative.movingpolygons.R;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public abstract class ScrollableTextPreference extends DialogPreference {
	public ScrollableTextPreference(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		
		setPersistent(false);
		setDialogLayoutResource(R.layout.scrollable_text_preference_layout);
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
		builder.setNegativeButton(null, null);
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		
		setupScrollableView(view);
	}
	
	protected abstract void setDialogText(TextView textView);
	
	private void setupScrollableView(View view) {
		TextView textView = (TextView)view.findViewById(R.id.scrollTextView);
		setDialogText(textView);
	}
}
