package com.cyberg.lexxicon.game;

import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.lancer.android.processing.traer.physics.Particle;
import com.lancer.android.processing.traer.physics.ParticleSystem;
import com.lancer.android.processing.traer.physics.Spring;

import processing.core.PApplet;

public class ObjectSlot {

	private PApplet mFather;
	private Object mObject;
	private ParticleSystem mPS;
	private Particle[] pArray;
	private Spring[] sArray;
	private float mOffsetY;
	private float mParticleX;
	private float mParticleY;
	private float mTOffX = 0;
	private float mTOffY = 0;
	private float mResizeTickX = -1;
	private float mResizeTickY = -1;
	private boolean mIsFull = true;

	public ObjectSlot(PApplet aFather, ParticleSystem aPS, Object anObject,
										int offsetY, int particleX, int particleY, boolean isFull) {
		mFather = aFather;
		mPS = aPS;
		mObject = anObject;
		mOffsetY = offsetY;
		mParticleX = particleX;
		mParticleY = particleY;
		mIsFull = isFull;
		mTOffX = CrossVariables.TOUCH_OFFSET_X / CrossVariables.RESIZE_FACTOR_X;
		mTOffY = CrossVariables.TOUCH_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y;
		mResizeTickX = (CrossVariables.IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X) / CrossVariables.OBJECTS_ANIMATION_FRAMES;
		mResizeTickY = (CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y) / CrossVariables.OBJECTS_ANIMATION_FRAMES;
		pArray = new Particle[4];
		sArray = new Spring[4];
		if (mIsFull) {
			buildParticles();
		}
	}

	public void setFull(boolean aValue) {
		mIsFull = aValue;
		/*
		if (mIsFull) {
			buildParticles();
		} 
		else {
			destroyParticles();
		}
		*/
	}

	private void buildParticles() {
		LetterStruct aLS = (LetterStruct) mObject;
		pArray[0] = mPS.makeParticle(CrossVariables.PARTICLE_MASS, mParticleX, mParticleY, 0f);
		pArray[1] = mPS.makeParticle(CrossVariables.PARTICLE_MASS, mParticleX + aLS.getImage().width, mParticleY, 0f);
		pArray[2] = mPS.makeParticle(CrossVariables.PARTICLE_MASS, mParticleX + aLS.getImage().width, mParticleY + aLS.getImage().height, 0f);
		pArray[3] = mPS.makeParticle(CrossVariables.PARTICLE_MASS, mParticleX, mParticleY + aLS.getImage().height, 0f);
		sArray[0] = mPS.makeSpring(pArray[0], pArray[1], CrossVariables.SPRING_STRENGTH, CrossVariables.SPRING_DAMPING,	aLS.getImage().width);
		sArray[1] = mPS.makeSpring(pArray[1], pArray[2], CrossVariables.SPRING_STRENGTH, CrossVariables.SPRING_DAMPING,	aLS.getImage().height);
		sArray[2] = mPS.makeSpring(pArray[2], pArray[3], CrossVariables.SPRING_STRENGTH, CrossVariables.SPRING_DAMPING,	aLS.getImage().width);
		sArray[3] = mPS.makeSpring(pArray[3], pArray[0], CrossVariables.SPRING_STRENGTH, CrossVariables.SPRING_DAMPING,	aLS.getImage().height);
	}

	public void destroyParticles() {
		for (int i = 0; i < 4; i++) {
			mPS.removeParticle(pArray[i]);
			mPS.removeSpring(sArray[i]);
		}
	}

	public boolean isFull() {
		return mIsFull;
	}

	public Object getObject() {
		return mObject;
	}
	
	public void updateBonus(int r, int c, float tX, float tY) throws Exception {
		for (int i = 0; i < pArray.length; i++) {
			if (pArray[i].position().y() >= (mFather.height - PApplet.round(r	* (CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y))) - mOffsetY) {
				float vX = pArray[i].velocity().x();
				float vY = pArray[i].velocity().y();
				pArray[i].position().set(pArray[i].position().x(), (mFather.height - PApplet.round(r * (CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y))) - mOffsetY, 0);
				pArray[i].velocity().set(vX, -vY, 0);
			}
		}
		LetterStruct aLS = (LetterStruct) mObject;
		if (overRect(aLS, pArray[0].position().x(), pArray[0].position().y(), tX, tY)) {
			aLS.setSelected(true);
			aLS.setDurationFrame(CrossVariables.OBJECTS_ANIMATION_FRAMES);
			if (!aLS.getMarked()) {
				aLS.setMarked(true);
			}
		}
		if (aLS.getSelected()) {
			mFather.tint(0, 153, 204);
			if (tX == -1f && tY == -1f) {
				int newW = PApplet.round(aLS.getImageW() - mResizeTickX);
				int newH = PApplet.round(aLS.getImageH() - mResizeTickY);
				if (aLS.getImageH() < 0 || aLS.getImageW() < 0) {
					setFull(false);
					CrossVariables.addSwapSlot(r, c);
				}
				else {
					aLS.setImageW(newW);
					aLS.setImageH(newH);
				}
			}
		}
		int offsetX = (PApplet.round(CrossVariables.IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X) - aLS.getImageW()) / 2;
		int offsetY = (PApplet.round(CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y) - aLS.getImageH()) / 2;
		mFather.image(aLS.getImage(), offsetX + pArray[0].position().x(), offsetY + pArray[0].position().y(), aLS.getImageW(), aLS.getImageH());
		mFather.noTint();
	}

	public void update(int r, int c, float tX, float tY) throws Exception {
		for (int i = 0; i < pArray.length; i++) {
			if (pArray[i].position().y() >= (mFather.height - PApplet.round(r	* (CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y))) - mOffsetY) {
				float vX = pArray[i].velocity().x();
				float vY = pArray[i].velocity().y();
				pArray[i].position().set(pArray[i].position().x(), (mFather.height - PApplet.round(r * (CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y))) - mOffsetY, 0);
				pArray[i].velocity().set(vX, -vY, 0);
			}
		}
		LetterStruct aLS = (LetterStruct) mObject;
		if (overRect(aLS, pArray[0].position().x(), pArray[0].position().y(), tX, tY)) {
			aLS.setSelected(true);
			aLS.setDurationFrame(CrossVariables.OBJECTS_ANIMATION_FRAMES);
			if (!aLS.getMarked()) {
				aLS.setMarked(true);
				CrossVariables.COMPOSED_WORD += aLS.getLetter();
			}
		}
		if (aLS.getSelected()) {
			mFather.tint(0, 153, 204);
			if (tX == -1f && tY == -1f) {
				int newW = PApplet.round(aLS.getImageW() - mResizeTickX);
				int newH = PApplet.round(aLS.getImageH() - mResizeTickY);
				if (aLS.getImageH() < 0 || aLS.getImageW() < 0) {
					setFull(false);
					CrossVariables.addSwapSlot(r, c);
				}
				else {
					aLS.setImageW(newW);
					aLS.setImageH(newH);
				}
			}
		}
		int offsetX = (PApplet.round(CrossVariables.IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X) - aLS.getImageW()) / 2;
		int offsetY = (PApplet.round(CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y) - aLS.getImageH()) / 2;
		mFather.image(aLS.getImage(), offsetX + pArray[0].position().x(), offsetY + pArray[0].position().y(), aLS.getImageW(), aLS.getImageH());
		mFather.noTint();
	}
	
	public float getCenterX() {
		return pArray[0].position().x() + (((LetterStruct)mObject).getImageW() / 2);
	}
	
	public float getCenterY() {
		return pArray[0].position().y() + (((LetterStruct)mObject).getImageH() / 2);
	}
	
	private boolean overRect(LetterStruct aLS, float lX, float lY, float tX, float tY) {
		if (tX > (lX + mTOffX) && tX < (lX + aLS.getImage().width - mTOffX) && 
				tY > (lY + mTOffY) && tY < (lY + aLS.getImage().height - mTOffY)) {
			return true;
		} 
		else {
			return false;
		}
	}	
}