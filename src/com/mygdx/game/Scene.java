package com.mygdx.game;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import com.badlogic.gdx.graphics.Camera;

public class Scene {
	private volatile static Mat cameraRvec;
	private volatile static Mat cameraTvec;
	private static Camera cam;
	
	
	public static boolean doneLoading = false;
	
	private static double[] tmp = new double[3];
	
	public static void setCamera(Camera c){
		cam = c;
	}
	
	public static float[] getTransform(){
		
		
		
		if(cameraRvec == null || cameraTvec == null)
			return null;

		double[] tmpR = new double[3];
		double[] tmpT = new double[3];
		
		cameraRvec.get(0, 0, tmpR);
		cameraTvec.get(0, 0, tmpT);
//		
//		float[] lol = new float[6];
//		for(int i=0; i<3; i++){
//			lol[i] = (float)tmpR[i];
//			lol[i+3] = (float)tmpT[i];
//		}
//		
//		
		
		
		
		
		
		
		
		
		
		Mat rot = new Mat();
		Calib3d.Rodrigues(cameraRvec, rot);	
//		Mat Ct = new Mat();
//		Mat Cr = rot.t();
//		try{
//			
////			Core.gemm(rot.t().inv().t(), cameraTvec, -1, new Mat(), 0, Ct, 0);
//			Core.gemm(rot.t(), cameraTvec, 1, new Mat(), 0, Ct, 0);
//		}
//		catch(Exception e){
//			e.getCause();
//		}
//		rot = rot.t();

//		float[] mat = {
//		(float)rot.get(0, 0)[0], (float)rot.get(0,1)[0], (float)rot.get(0, 2)[0], (float)cameraTvec.get(0, 0)[0],
//		(float)rot.get(1, 0)[0], (float)rot.get(1,1)[0], (float)rot.get(1, 2)[0], (float)cameraTvec.get(1, 0)[0],
//		(float)rot.get(2, 0)[0], (float)rot.get(2,1)[0], (float)rot.get(2, 2)[0], (float)cameraTvec.get(2, 0)[0],
//		0,				  0,			   0,				 1
//};		

//		transponowana		
//		float[] mat = {
//				(float)rot.get(0, 0)[0], 		(float)rot.get(1,0)[0],			(float)rot.get(2, 0)[0], 		0,
//				(float)rot.get(0, 1)[0], 		(float)rot.get(1,1)[0], 		(float)rot.get(2, 1)[0], 		0,
//				(float)rot.get(0, 2)[0], 		(float)rot.get(1,2)[0], 		(float)rot.get(2, 2)[0], 		0,
//				(float)cameraTvec.get(0, 0)[0], (float)cameraTvec.get(1, 0)[0],	(float)cameraTvec.get(2, 0)[0],	1
//		};
//		
//		WIERD SHIET
//		float[] mat = {
//		(float)rot.get(0, 0)[0], 		(float)rot.get(2,0)[0],			(float)rot.get(1, 0)[0], 		0,
//		(float)rot.get(0, 1)[0], 		(float)rot.get(2,1)[0], 		(float)rot.get(1, 1)[0], 		0,
//		(float)rot.get(0, 2)[0], 		(float)rot.get(2,2)[0], 		(float)rot.get(1, 2)[0], 		0,
//		(float)cameraTvec.get(0, 0)[0], (float)cameraTvec.get(2, 0)[0],	-(float)cameraTvec.get(1, 0)[0],	1
//};		//		
		//TRANS ONLY
//		float[] mat = {
//				(float)rot.get(0, 0)[0], 		(float)rot.get(0,1)[0],			(float)rot.get(0, 2)[0], 		0,
//				-(float)rot.get(1, 0)[0], 		-(float)rot.get(1,1)[0], 		-(float)rot.get(1, 2)[0], 		0,
//				-(float)rot.get(2, 0)[0], 		-(float)rot.get(2,1)[0], 		-(float)rot.get(2, 2)[0], 		0,
//				(float)cameraTvec.get(0, 0)[0], (float)cameraTvec.get(2, 0)[0],	(float)cameraTvec.get(1, 0)[0] ,	1
//		};		cp 
//
		float[] mat = {
				(float)rot.get(0, 0)[0], 		-(float)rot.get(1,0)[0],			-(float)rot.get(2, 0)[0], 		0,
				(float)rot.get(0, 1)[0], 		-(float)rot.get(1,1)[0], 			-(float)rot.get(2, 1)[0], 		0,
				(float)rot.get(0, 2)[0], 		-(float)rot.get(1,2)[0], 			-(float)rot.get(2, 2)[0], 		0,
				(float)cameraTvec.get(0, 0)[0]*2.3f , -(float)cameraTvec.get(1, 0)[0]*2.3f,	-(float)cameraTvec.get(2, 0)[0] ,	1
		};		
//		float[] mat = {
//				(float)rot.get(0, 0)[0], 		-(float)rot.get(1,0)[0],			-(float)rot.get(2, 0)[0], 		0,
//				(float)rot.get(0, 1)[0], 		-(float)rot.get(1,1)[0], 			-(float)rot.get(2, 1)[0], 		0,
//				(float)rot.get(0, 2)[0], 		-(float)rot.get(1,2)[0], 			-(float)rot.get(2, 2)[0], 		0,
//				(float)Ct.get(0, 0)[0], -(float)Ct.get(1, 0)[0],	-(float)Ct.get(2, 0)[0] - 20 ,	1
//		};		

//		float[] mat = {
//				(float)Cr.get(0, 0)[0], -(float)Cr.get(1, 0)[0], -(float)Cr.get(2, 0)[0], 		0,
//				(float)Cr.get(0, 1)[0], -(float)Cr.get(1, 1)[0], -(float)Cr.get(2, 1)[0], 		0,
//				(float)Cr.get(0, 2)[0], -(float)Cr.get(1, 2)[0], -(float)Cr.get(2, 2)[0], 		0,
//				(float)Ct.get(0, 0)[0], -(float)Ct.get(1, 0)[0], -(float)Ct.get(2, 0)[0] - 20 ,	1
//		};		
//		


		
		return mat;
	}
	
	public void initOpencv(){
		
	}
	
	
	
	
	
	public static void setCameraPos(Mat tvec, Mat rvec){
		cameraTvec = tvec;
		cameraRvec = rvec;
		
//		
//		Mat camMat = new Mat(4,4,CvType.CV_32FC1);
//		for(int i=0; i<3; i++){
//			for(int j=0; j<3; j++)
//				camMat.put(i, j, rot.get(i, j));
//		}
//		for(int i=0; i<3; i++)
//			camMat.put(i, 3, tvec.get(0, i));
//		camMat.inv();
		
//		cameraTvec = tvec;
//		cameraTvec.get(0, 0, tmp);
//		//cam.projection.setToTranslation((float)tmp[0], (float)tmp[1], (float)tmp[2]);
//		cam.
//		cam.update();
//		cameraRvec = rvec;
	}


}
