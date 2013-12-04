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

import org.logicallycreative.movingpolygons.managers.PolygonManager;

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
		private boolean screenVisible = true;
		private PolygonManager polygonManager;

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.screenVisible = visible;
			if (this.screenVisible) {
				this.handler.post(this.serviceRunner);
			} else {
				this.handler.removeCallbacks(this.serviceRunner);
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
			this.screenVisible = false;
			this.handler.removeCallbacks(this.serviceRunner);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder surface, int format,
				int width, int height) {
			super.onSurfaceChanged(surface, format, width, height);
			createPolygonManager();
		}

		private void drawFrame() {
			SurfaceHolder surface = this.getSurfaceHolder();
			Canvas canvas = null;

			try {
				canvas = surface.lockCanvas();
				canvas.save();
				canvas.drawColor(Color.argb(255, 0, 0, 0));

				this.polygonManager.movePoints();
				this.polygonManager.drawPolygon(canvas);

				canvas.restore();
			} finally {
				if (canvas != null)
					surface.unlockCanvasAndPost(canvas);
			}

			this.handler.removeCallbacks(this.serviceRunner);
			if (!this.screenVisible)
				return;

			handler.postDelayed(this.serviceRunner, 50);
		}

		private void createPolygonManager() {
			DisplayMetrics metrics = new DisplayMetrics();
			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
					.getDefaultDisplay();

			display.getMetrics(metrics);
			this.screenWidth = metrics.widthPixels;
			this.screenHeight = metrics.heightPixels;

			if (this.polygonManager != null)
				return;

			// TODO: For now, just use a triangle.
			polygonManager = new PolygonManager(3, this.screenWidth,
					this.screenHeight);
		}
	}
}