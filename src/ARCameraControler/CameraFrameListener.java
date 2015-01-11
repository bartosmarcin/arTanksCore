package ARCameraControler;

import org.opencv.core.Mat;

public interface CameraFrameListener {

	public Mat onFrameCaptured(Mat frame);
}
