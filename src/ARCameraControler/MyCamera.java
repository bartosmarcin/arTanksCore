package ARCameraControler;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Mat;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;

public class MyCamera extends PerspectiveCamera{
	
	public MyCamera(float fieldOfViewY, float viewportWidth, float viewportHeight) {
		super(fieldOfViewY, viewportWidth, viewportHeight);
	}
	
	private volatile static Mat cameraRvec;
	private volatile static Mat cameraTvec;
		
	public static boolean doneLoading = false;
		

	private static Mat rot;
	private static float[] mat = new float[16];
	
	public static float[] getTransform(){		
		if(cameraRvec == null || cameraTvec == null)
			return null;
		
		Calib3d.Rodrigues(cameraRvec, rot);	
		mat[0] = (float)rot.get(0, 0)[0]; mat[1] = -(float)rot.get(1,0)[0]; mat[2] = -(float)rot.get(2, 0)[0]; mat[3] = 0f;
		mat[4] = (float)rot.get(0, 1)[0]; mat[5] = -(float)rot.get(1,1)[0]; mat[6] = -(float)rot.get(2, 1)[0]; mat[7] = 0f;
		mat[8] = (float)rot.get(0, 2)[0]; mat[9] = -(float)rot.get(1,2)[0]; mat[10] = -(float)rot.get(2, 2)[0]; mat[11] = 0f;
		mat[12] = (float)cameraTvec.get(0, 0)[0]*2.3f; mat[13] = -(float)cameraTvec.get(1, 0)[0]*2.3f; mat[14] = -(float)cameraTvec.get(2, 0)[0]; mat[15] = 1f;

		return mat;
	}
		
	public static void setCameraPos(Mat tvec, Mat rvec){
		cameraTvec = tvec;
		cameraRvec = rvec;
		if(rot == null)
			rot = new Mat();
	}

	@Override
	public void update (boolean updateFrustum) {
		float aspect = viewportWidth / viewportHeight;
		projection.setToProjection(Math.abs(near), Math.abs(far), fieldOfView, aspect);
		combined.set(projection);
		Matrix4.mul(combined.val, view.val);

		if (updateFrustum) {
			invProjectionView.set(combined);
			Matrix4.inv(invProjectionView.val);
			frustum.update(invProjectionView);
		}
	}
}
