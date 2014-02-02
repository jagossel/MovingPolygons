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
package org.logicallycreative.movingpolygons;

import org.logicallycreative.movingpolygons.data.engine.EngineData;
import org.logicallycreative.movingpolygons.data.engine.SettingsData;
import org.logicallycreative.movingpolygons.loaders.EngineLoader;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
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
		SharedPreferences preferences = super.getSharedPreferences(
				SETTINGS_NAME, 0);

		Engine wallpaperEngine = new MovingPolygonsEngine(preferences);
		SharedPreferences.OnSharedPreferenceChangeListener wallpaperEngineListener = (SharedPreferences.OnSharedPreferenceChangeListener) wallpaperEngine;

		preferences
				.registerOnSharedPreferenceChangeListener(wallpaperEngineListener);

		return wallpaperEngine;
	}

	private class MovingPolygonsEngine extends Engine implements
			SharedPreferences.OnSharedPreferenceChangeListener {
		private static final int delayPostingInMilliseconds = 20;

		private final Handler handler = new Handler();
		private final Runnable serviceRunner = new Runnable() {
			@Override
			public void run() {
				drawFrame();
			}
		};

		private boolean screenVisible;

		public MovingPolygonsEngine(SharedPreferences preferences) {
			initializeEngine(preferences);
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
			createPolygonManager();
			drawFrame();
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder surface) {
			super.onSurfaceDestroyed(surface);
			screenVisible = false;
			handler.removeCallbacks(serviceRunner);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder surface, int format,
				int width, int height) {
			super.onSurfaceChanged(surface, format, width, height);

			// TODO: Add in logic to keep the polygon...
			// For now, just create a new polygon when changing the surface.
			createPolygonManager();
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences preferences,
				String key) {
			initializeEngine(preferences);
			createPolygonManager();
		}

		private void initializeEngine(SharedPreferences preferences) {
			EngineData.settings = new SettingsData(preferences);
			EngineData.engineLoader = new EngineLoader();
			EngineData.colorManager = EngineData.engineLoader.getColorManager();
		}

		private void drawFrame() {
			SurfaceHolder surface = getSurfaceHolder();
			Canvas canvas = null;

			EngineData.colorManager.changeColors();
			EngineData.drawingManager.applyColorChange();
			EngineData.drawingManager.movePoints();

			try {
				canvas = surface.lockCanvas();
				canvas.save();

				canvas.drawColor(Color.argb(255, 0, 0, 0));
				EngineData.drawingManager.drawPoints(canvas);

				canvas.restore();
			} finally {
				if (canvas != null)
					surface.unlockCanvasAndPost(canvas);
			}

			handler.removeCallbacks(this.serviceRunner);
			if (!screenVisible)
				return;

			handler.postDelayed(serviceRunner, delayPostingInMilliseconds);
		}

		private void createPolygonManager() {
			DisplayMetrics metrics = new DisplayMetrics();
			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
					.getDefaultDisplay();

			display.getMetrics(metrics);

			EngineData.screenWidth = metrics.widthPixels;
			EngineData.screenHeight = metrics.heightPixels;
			EngineData.drawingManager = EngineData.engineLoader
					.getShapeManager();
		}
	}
}
