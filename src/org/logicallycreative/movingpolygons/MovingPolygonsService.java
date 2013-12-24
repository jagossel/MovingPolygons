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

import java.util.ArrayList;
import java.util.List;

import org.logicallycreative.movingpolygons.data.engine.EngineData;
import org.logicallycreative.movingpolygons.data.shape.DeltaPoint;
import org.logicallycreative.movingpolygons.managers.color.SineWave;
import org.logicallycreative.movingpolygons.managers.drawing.Echoes;
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

		private boolean screenVisible;

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

			// TODO: Add in logic to keep the polygon, instead creating a new
			// one.
			// For now, just create a new polygon when changing the surface.
			createPolygonManager();
		}

		private void drawFrame() {
			SurfaceHolder surface = getSurfaceHolder();
			Canvas canvas = null;

			try {
				canvas = surface.lockCanvas();
				canvas.save();
				canvas.drawColor(Color.argb(255, 0, 0, 0));

				EngineData.colorManager.changeColors();
				EngineData.drawingManager.movePoints();
				EngineData.drawingManager.drawPoints(canvas);

				canvas.restore();
			} finally {
				if (canvas != null)
					surface.unlockCanvasAndPost(canvas);
			}

			handler.removeCallbacks(this.serviceRunner);
			if (!screenVisible)
				return;

			handler.postDelayed(serviceRunner, 20);
		}

		private void createPolygonManager() {
			DisplayMetrics metrics = new DisplayMetrics();
			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
					.getDefaultDisplay();

			display.getMetrics(metrics);
			EngineData.screenWidth = metrics.widthPixels;
			EngineData.screenHeight = metrics.heightPixels;

			List<DeltaPoint> startingPoints = createStartingPoints();
			int numberOfEchoes = RandomNumberUtility.getRandomInteger(3, 10);
			int spacing = RandomNumberUtility.getRandomInteger(5, 10);

			EngineData.colorManager = new SineWave();
			EngineData.drawingManager = new Echoes(numberOfEchoes, spacing);
			EngineData.drawingManager.addPoints(startingPoints);
		}

		private List<DeltaPoint> createStartingPoints() {
			List<DeltaPoint> startingPoints = new ArrayList<DeltaPoint>();

			int numberOfSides = RandomNumberUtility.getRandomInteger(3, 8);
			for (int i = 0; i < numberOfSides; i++) {
				int xCoordinate = RandomNumberUtility.getRandomInteger(0,
						EngineData.screenWidth);
				int yCoordinate = RandomNumberUtility.getRandomInteger(0,
						EngineData.screenHeight);

				startingPoints.add(new DeltaPoint(xCoordinate, yCoordinate, 1,
						1));
			}

			return startingPoints;
		}
	}
}
