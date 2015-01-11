package com.mygdx.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements GestureListener{
	ModelInstance tank;
	Node turret;
	Node gun;
	
	public InputHandler(ModelInstance model) {
		tank = model;
		turret = tank.getNode("Turret",true);
		gun = tank.getNode("Gun",true);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		float degrees = deltaX/5f;
		turret.localTransform.rotate(Vector3.Z, degrees);
		turret.calculateWorldTransform();
		
//		Vector3 v1 = turret.translation.cpy();
//		Vector3 v2 = gun.translation.cpy();
//		v1.sub(gun.translation);
//		v2.sub(turret.translation);
//		gun.localTransform.translate(v1);
//		gun.localTransform.rotate(0, 0, 1, degrees);
//		gun.calculateWorldTransform();
//		gun.localTransform.translate(v2);
		
//		gun.calculateWorldTransform();
//		gun.translation.add(v);
		
		degrees = deltaY/5f;
//		gun.localTransform.set(turret.localTransform.cpy());
		gun.localTransform.rotate(Vector3.X, degrees);
//		gun.calculateLocalTransform();
		gun.calculateWorldTransform();
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}


}
