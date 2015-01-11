package com.mygdx.game;

import ARCameraControler.MyCamera;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public volatile MyCamera cam;
	public ModelBatch modelBatch;
	public Environment environment;

	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public boolean loading;

	// Model tank;
	// ModelInstance instance;

	private int viewPortHeight;
	private int viewPortWidth;
	private ILoading mLoading;

	public MyGdxGame(int viewPortHeight, int viewPortWidth,ILoading loading) {
		this.viewPortHeight = viewPortHeight;
		this.viewPortWidth = viewPortWidth;
		this.mLoading = loading;
	}

	@Override
	public void create() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 2f, 2f, 2f, 1f));
		environment.add(new DirectionalLight().set(8f, 8f, 8f, -20f, -40f, -20f));

		modelBatch = new ModelBatch();
		
		cam = new MyCamera(67, viewPortWidth, viewPortHeight);
		cam.far = 10000.0f;
		cam.near = 0.1f;
		
		assets = new AssetManager();
		assets.load("data/cube.g3db", Model.class);
		loading = true;
	}

	private void doneLoading() {
		try{
		Model tank = assets.get("data/cube.g3db", Model.class);
		
		ModelInstance tankInstance = new ModelInstance(tank);
		instances.add(tankInstance);
		
		Gdx.input.setInputProcessor(new GestureDetector(new InputHandler(tankInstance)));
		
		
		loading = false;
		Scene.doneLoading = true;
		mLoading.onLoadingComplete();
		
//		BoundingBox box = new BoundingBox();
//		tankInstance.calculateBoundingBox(box);
//		box.getHeight();
//		box.getWidth();
//		box.getDepth();
		}catch (Exception e){
			e.getCause();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		if (loading && assets.update())
			doneLoading();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		if (!loading) {
			try {
				float[] transform = Scene.getTransform();
				if (transform != null) {
					cam.view.set(transform);
					cam.update();
					modelBatch.render(instances, environment);
				}
			} catch (Exception e) {
				e.getCause();
				e.printStackTrace();
			}
		}
		modelBatch.end();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		instances.clear();
		modelBatch.end();

	}
}
