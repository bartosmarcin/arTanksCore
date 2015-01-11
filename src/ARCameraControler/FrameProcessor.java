package ARCameraControler;

import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point;
import org.opencv.core.Point3;

import com.mygdx.game.Scene;


public class FrameProcessor implements CameraFrameListener{
	private MarkerDetector detector;
	private Marker marker;

	private Mat tvec, rvec;
	
	public FrameProcessor(){
		rvec = new Mat();
		tvec = new Mat();
		detector = new MarkerDetector();
		trackedPoints = new MatOfPoint2f();
		initMarkerPosition(10);
		initCameraIntristics();		
	}

	@Override
	public Mat onFrameCaptured(Mat frame) {
		marker = detector.detect(frame);
		if (marker == null) {
			Scene.setCameraPos(null, null);
			return frame;
		}
		
		estimatePose(marker, rvec, tvec);
		return frame;
	}

	
	MatOfPoint2f trackedPoints;
	public void estimatePose(Marker marker, Mat rvec, Mat tvec) {
		List<Point> newPoints = marker.getSortedPoints();
		trackedPoints.fromList(newPoints);
		Calib3d.solvePnP(oldPoints3f, trackedPoints, cameraMat, distCooef, rvec, tvec);
		Scene.setCameraPos(tvec, rvec);
	}

//	private void drawAxes(float size, Mat rvec, Mat tvec) {
//		Point3[] points = { new Point3(0, 0, 0), new Point3(0, size, 0), new Point3(size, 0, 0),
//				new Point3(0, 0, size), };
//		MatOfPoint2f projPoints = new MatOfPoint2f();
//		MatOfPoint3f pointsMat = new MatOfPoint3f();
//		pointsMat.fromArray(points);
//
//		Mat rot = new Mat();
//		Calib3d.Rodrigues(rvec, rot);
//		rot.dump();
//		rvec.dump();
//		tvec.dump();
//		Calib3d.projectPoints(pointsMat, rvec, tvec, cameraMat, distCooef, projPoints);
//
//		Point[] ptsToDraw = projPoints.toArray();
//		Core.line(rgba, ptsToDraw[0], ptsToDraw[1], new Scalar(255, 0, 0), 3);
//		Core.line(rgba, ptsToDraw[0], ptsToDraw[2], new Scalar(0, 255, 0), 3);
//		Core.line(rgba, ptsToDraw[0], ptsToDraw[3], new Scalar(0, 0, 255), 3);
//	}

	MatOfPoint3f oldPoints3f;

	public void initMarkerPosition(float size) {
		size = size / 2;
		Point3[] points = { new Point3(0, 0, 0), new Point3(0, size, 0), new Point3(size, size, 0), new Point3(size, -size, 0),
				new Point3(-size, -size, 0), new Point3(-size, 0, 0)
		};
		oldPoints3f = new MatOfPoint3f();
		oldPoints3f.fromArray(points);
	}

	Mat cameraMat;
	MatOfDouble distCooef;

	/*
	 * Some default values to avoid calibration may be changed in the future
	 */
	private void initCameraIntristics() {

		float f = 804.29931f;
		cameraMat = Mat.zeros(3, 3, CvType.CV_32FC1);
		cameraMat.put(0, 0, f);
		cameraMat.put(1, 1, f);

		cameraMat.put(2, 2, 1f);

		cameraMat.put(0, 2, 399.5f);
		cameraMat.put(1, 2, 239.5f);

		distCooef = new MatOfDouble();

	}
}
