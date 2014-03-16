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

import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class AboutPreference extends ScrollableTextPreference {
	public AboutPreference(Context context, AttributeSet attrSet) {
		super(context, attrSet);		
	}
	
	@Override
	protected void setDialogText(TextView textView) {
		final String spanTextFormat = "%s\n%s\n\n%s\n\n%s %s";
		Context context = super.getContext();
		
		String appName = context.getString(R.string.app_name);
		int appNameLength = appName.length();
		int appNameStart = 0;
		int appNameStop = appNameStart + appNameLength;
		
		String copyright = context.getString(R.string.copyright_line);
		int copyrightLength = copyright.length();
		int copyrightStart = appNameStop + 1;
		int copyrightStop = copyrightStart + copyrightLength;
		
		String licenseText = context.getString(R.string.brief_legal_text);
		int licenseTextLength = licenseText.length();
		int licenseTextStart = copyrightStop + 2;
		int licenseTextStop = licenseTextStart + licenseTextLength;
		
		String licenseLinkLabel = context.getString(R.string.license_link_label);
		int licenseLinkLabelLength = licenseLinkLabel.length();
		int licenseLinkLabelStart = licenseTextStop + 2;
		int licenseLinkLabelStop = licenseLinkLabelStart + licenseLinkLabelLength;
		
		String licenseLink = context.getString(R.string.license_link);
		int licenseLinkLength = licenseLink.length();
		int licenseLinkStart = licenseLinkLabelStop + 1;
		int licenseLinkStop = licenseLinkStart + licenseLinkLength;
		
		String spanText = String.format(spanTextFormat,
			appName, copyright, licenseText, licenseLinkLabel, licenseLink);
			
		SpannableString spannableText = new SpannableString(spanText);
		spannableText.setSpan(new RelativeSizeSpan(1.25f), appNameStart, appNameStop, 0);
		spannableText.setSpan(new RelativeSizeSpan(1.25f), copyrightStart, copyrightStop, 0);
		spannableText.setSpan(new RelativeSizeSpan(1f), licenseTextStart, licenseTextStop, 0);
		spannableText.setSpan(new RelativeSizeSpan(1f), licenseLinkLabelStart, licenseLinkLabelStop, 0);
		spannableText.setSpan(new URLSpan(licenseLink), licenseLinkStart, licenseLinkStop, 0);
		
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		textView.setText(spannableText, BufferType.SPANNABLE);
	}
}
