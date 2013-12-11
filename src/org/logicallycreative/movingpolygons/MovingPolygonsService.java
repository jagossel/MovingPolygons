// Moving Polygons Live Wallpaper
// Copyright (C) 2013  LogicallyCreative.org
// 
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
package org.logicallycreative.movingpolygons;

import org.logicallycreative.movingpolygons.managers.drawing.DrawingManagable;
import org.logicallycreative.movingpolygons.managers.drawing.PolygonManager;
import org.logicallycreative.movingpolygons.util.RandomNumberUtility;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class MovingPolygonsService extends WallpaperService {
	@Override
	public Engine onCreateEngine() {
		return new MovingPolygonsEngine();
	}

	private class MovingPolygonsEngine extends Engine {
		private final Handler handler = new Handler();
		private final Runnable serviceRunner = new Runnable() {
			@Override
			public void run() {
				drawFrame();
			}
		};

		private int screenWidth;
		private int screenHeight;
		private boolean screenVisible;
		private DrawingManagable drawingManager;

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
			createPolygonManager();
		}

		private void drawFrame() {
			SurfaceHolder surface = getSurfaceHolder();
			Canvas canvas = null;

			try {
				canvas = surface.lockCanvas();
				canvas.save();
				canvas.drawColor(Color.argb(255, 0, 0, 0));

				drawingManager.movePoints();
				drawingManager.drawPoints(canvas);

				canvas.restore();
			} finally {
				if (canvas != null)
					surface.unlockCanvasAndPost(canvas);
			}

			handler.removeCallbacks(this.serviceRunner);
			if (!screenVisible)
				return;

			handler.postDelayed(serviceRunner, 10);
		}

		private void createPolygonManager() {
			DisplayMetrics metrics = new DisplayMetrics();
			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
					.getDefaultDisplay();

			display.getMetrics(metrics);
			screenWidth = metrics.widthPixels;
			screenHeight = metrics.heightPixels;

			if (drawingManager != null)
				return;

			int numberOfSides = RandomNumberUtility.getRandomInteger(3, 8, 5);

			// TODO: Create the EchoesManager class, and replace the PolygonManager with it.
			drawingManager = new PolygonManager(numberOfSides, screenWidth, screenHeight);
		}
	}
}
