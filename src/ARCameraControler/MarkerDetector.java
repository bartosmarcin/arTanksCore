package ARCameraControler;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MarkerDetector {
	private MatOfPoint2f tmpContour;
	private MatOfPoint2f outerCurve;
	private MatOfPoint2f innerCurve;
	private Mat hierarchy;
	private Mat thrs;

	private List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

	private boolean scaleDown = true;
	private double scaleDownRatio=0.5;
	
	public MarkerDetector() {
		this(1);
	}

	public MarkerDetector(double scaleDown) {
		tmpContour = new MatOfPoint2f();
		outerCurve = new MatOfPoint2f();
		innerCurve = new MatOfPoint2f();
		thrs = new Mat();
		dummyScale = new Size();
		this.hierarchy = new Mat();
	}

	//for some reason needed for resizing image even by factor
	private Size dummyScale;
	
	public Marker detect(Mat rgba) {
		Marker marker = null;


		Imgproc.cvtColor(rgba, thrs, Imgproc.COLOR_RGB2GRAY);
		
		 if(scaleDown)
			 Imgproc.resize(thrs, thrs, dummyScale, scaleDownRatio, scaleDownRatio, Imgproc.INTER_LINEAR);

		Imgproc.blur(thrs, thrs, new Size(3, 3));
		// Imgproc.medianBlur(thrs, thrs, 17);

		Imgproc.threshold(thrs, thrs, 50, 255, Imgproc.THRESH_OTSU);
		// Imgproc.adaptiveThreshold(thrs, thrs,255,
		// Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11, 2);
		// Imgproc.Canny(thrs, thrs, 150, 400, 3, true);

		Imgproc.findContours(thrs, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);

		int child = -1;
		for (int i = 0; i < contours.size(); i++) {
			child = (int) hierarchy.get(0, i)[2];
			if (child < 0)
				continue;

			contours.get(i).convertTo(tmpContour, CvType.CV_32FC2);
			Imgproc.approxPolyDP(tmpContour, outerCurve, 0.01 * tmpContour.height(), true);
			if (outerCurve.height() != 4)
				continue;

			contours.get(child).convertTo(tmpContour, CvType.CV_32FC2);
			Imgproc.approxPolyDP(tmpContour, innerCurve, 0.01 * tmpContour.height(), true);
			if (innerCurve.height() != 6)
				continue;
			
			if(scaleDown)
					marker = new Marker(innerCurve.toList(), outerCurve.toList(), 1d/scaleDownRatio);
			else		
				marker = new Marker(innerCurve.toList(), outerCurve.toList());
			
				
			break;
		}

		contours.clear();
		return marker;
	}
}
