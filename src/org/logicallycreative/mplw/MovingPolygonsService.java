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
package org.logicallycreative.mplw;

import java.util.List;

import org.logicallycreative.mplw.data.engine.SettingsData;
import org.logicallycreative.mplw.data.shape.DeltaPoint;
import org.logicallycreative.mplw.data.shape.ShapeColor;
import org.logicallycreative.mplw.factories.SettingsDataFactory;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class MovingPolygonsService extends WallpaperService {
	public static final String SETTINGS_NAME = "MovingPolygonsSettings";

	@Override
	public Engine onCreateEngine() {
		SharedPreferences preferences = super.getSharedPreferences(SETTINGS_NAME, 0);

		Engine wallpaperEngine = new LiveWallpaperEngine(preferences);
		SharedPreferences.OnSharedPreferenceChangeListener wallpaperEngineListener = (SharedPreferences.OnSharedPreferenceChangeListener) wallpaperEngine;
		preferences.registerOnSharedPreferenceChangeListener(wallpaperEngineListener);

		return wallpaperEngine;
	}

	private class LiveWallpaperEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener {
		private static final int delayPostingInMilliseconds = 20;

		private final Handler handler = new Handler();
		private final Runnable serviceRunner = new Runnable() {
			@Override
			public void run() {
				drawFrame();
			}
		};

		private boolean screenVisible;
		private final MovingPolygonsEngine engine;

		public LiveWallpaperEngine(SharedPreferences preferences) {
			engine = MovingPolygonsEngine.getInstance();
			reinitializeEngine(preferences);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			screenVisible = visible;
			if (screenVisible) {
				handler.post(serviceRunner);
			} else {
				handler.removeCallbacks(serviceRunner);
			}
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder surface) {
			resetDimensions();
			drawFrame();
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder surface) {
			super.onSurfaceDestroyed(surface);
			screenVisible = false;
			handler.removeCallbacks(serviceRunner);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder surface, int format, int width, int height) {
			super.onSurfaceChanged(surface, format, width, height);
			resetDimensions();
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
			reinitializeEngine(preferences);
		}

		private void drawFrame() {
			SurfaceHolder surface = getSurfaceHolder();
			Canvas canvas = null;

			try {
				engine.calculateNextFrameData();

				canvas = surface.lockCanvas();
				canvas.save();

				canvas.drawColor(Color.argb(255, 0, 0, 0));
				drawFrameOnCanvas(canvas);

				canvas.restore();
			} finally {
				if (canvas != null) {
					surface.unlockCanvasAndPost(canvas);
				}
			}

			handler.removeCallbacks(this.serviceRunner);
			if (!screenVisible)
				return;

			handler.postDelayed(serviceRunner, delayPostingInMilliseconds);
		}

		private void drawFrameOnCanvas(Canvas canvas) {
			List<List<DeltaPoint>> drawingData = engine.getDrawingData();
			List<Integer> alphaValues = engine.getAlphaValues();
			ShapeColor shapeColor = engine.getCurrentShapeColor();

			int polygonCount = drawingData.size();
			for (int i = 0; i < polygonCount; i++) {
				List<DeltaPoint> points = drawingData.get(i);
				int alphaValueIndex = (polygonCount - 1) - i;

				int pointCount = points.size();
				for (int j = 0; j < pointCount; j++) {
					int startIndex = j;
					int endIndex = (j + 1) % pointCount;

					DeltaPoint startingPoint = points.get(startIndex);
					DeltaPoint endingPoint = points.get(endIndex);

					int startingX = startingPoint.getXCoordinate();
					int startingY = startingPoint.getYCoordinate();

					int endingX = endingPoint.getXCoordinate();
					int endingY = endingPoint.getYCoordinate();

					Paint linePaint = new Paint();
					linePaint.setStrokeCap(Paint.Cap.SQUARE);
					linePaint.setAntiAlias(true);
					linePaint.setStrokeWidth(1.5f);

					int alpha = alphaValues.get(alphaValueIndex);
					int red = shapeColor.red;
					int green = shapeColor.green;
					int blue = shapeColor.blue;

					linePaint.setARGB(alpha, red, green, blue);

					canvas.drawLine(startingX, startingY, endingX, endingY, linePaint);
				}
			}
		}

		private void resetDimensions() {
			if (engine == null)
				return;

			DisplayMetrics metrics = new DisplayMetrics();
			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
			display.getMetrics(metrics);

			engine.setDimensions(metrics.widthPixels, metrics.heightPixels);
		}

		private void reinitializeEngine(SharedPreferences preferences) {
			SettingsData settings = SettingsDataFactory.getSettingsDataFromSharedPreferences(preferences);
			engine.initializeEngine(settings);
		}
	}
}