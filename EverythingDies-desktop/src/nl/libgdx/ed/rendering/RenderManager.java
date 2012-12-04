package nl.libgdx.ed.rendering;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class RenderManager {
	
	SpriteBatch spritebatch;
	ArrayList<RenderLayer> layers;
	
	private final static Matrix4 matrixZero = new Matrix4();
	
	public RenderManager() {
		spritebatch = new SpriteBatch();
		layers = new ArrayList<RenderLayer>();
	}
	
	public void newRenderLayer(int id, boolean bCameraOffset) {
		RenderLayer layer = new RenderLayer(id, bCameraOffset);
		
		for(int i = 0; i < layers.size(); i++) {
			if(layers.get(i).gLayerID() < id)
				layers.add(i, layer);
		}
	}
	
	public void addSprite(int layer, Sprite sprite) {
		for(int i = 0; i < layers.size(); i++) {
			if(layers.get(i).gLayerID() == layer) {
				layers.get(i).addSprite(sprite);
				return;
			}
		}
		newRenderLayer(layer, false);
		this.addSprite(layer, sprite);
	}
	
	public void render(Camera camera) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		for(int i = 0; i < layers.size(); i++) {
			if(layers.get(i).usesCameraOffset())
				spritebatch.setProjectionMatrix(camera.combined);
			else
				spritebatch.setProjectionMatrix(matrixZero);
			
			spritebatch.begin();
			layers.get(i).renderLayer(spritebatch, RenderLayer.TOPDOWN);
			spritebatch.end();
		}
	}

}
