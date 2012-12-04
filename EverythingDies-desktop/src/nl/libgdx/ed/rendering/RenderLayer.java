package nl.libgdx.ed.rendering;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderLayer {
	
	public static final int TOPDOWN = 0;
	public static final int BOTTOMUP = 1;
	
	int layerID;
	boolean bCameraOffset;
	ArrayList<Sprite> sprites;
	
	public RenderLayer(int id, boolean bCameraOffset) {
		this.bCameraOffset = bCameraOffset;
		sprites = new ArrayList<Sprite>();
	}
	
	public void addSprite(Sprite sprite) {
		float spriteHeight = sprite.getY() + sprite.getHeight();
		for(int i = 0; i < sprites.size(); i++) {
			Sprite curSprite = sprites.get(i);
			float curSpriteHeight = curSprite.getY() + curSprite.getHeight();
			
			if(curSpriteHeight < spriteHeight) {
				sprites.add(i, sprite);
				return;
			}
		}
		sprites.add(sprite);
	}
	
	public void renderLayer(SpriteBatch batch, int renderOrder) {
		if(renderOrder == TOPDOWN)
			renderTopDown(batch);
		if(renderOrder == BOTTOMUP)
			renderBottomUp(batch);
	}
	
	private void renderTopDown(SpriteBatch batch) {
		for(int i = 0; i < sprites.size(); i++) {
			renderSprite(batch, sprites.get(i));
		}
	}
	
	private void renderBottomUp(SpriteBatch batch) {
		for(int i = sprites.size(); i > 0; i--) {
			renderSprite(batch, sprites.get(i));
		}
	}
	
	private void renderSprite(SpriteBatch batch, Sprite sprite) {
		sprite.translate(-sprite.getOriginX(), -sprite.getOriginY());
		sprite.draw(batch);
		sprite.translate(sprite.getOriginX(), sprite.getOriginY());
	}
	
	public void clearLayer() {
		sprites.clear();
	}
	
	public int gLayerID() {
		return layerID;
	}
	
	public boolean usesCameraOffset() {
		return bCameraOffset;
	}
}
