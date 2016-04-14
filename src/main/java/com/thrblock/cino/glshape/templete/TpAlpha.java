package com.thrblock.cino.glshape.templete;

import com.thrblock.cino.glshape.GLMutiPointShape;
import com.thrblock.cino.glshape.GLPoint;

public class TpAlpha {
	public static boolean tearOut(GLPoint point, float step) {
		float calc = point.getAlpha() - step;
		if(calc > 0) {
			point.setAlpha(calc);
			return false;
		} else {
			point.setAlpha(0);
			return true;
		}
	}
	public static boolean tearIn(GLPoint point, float step) {
		float calc = point.getAlpha() + step;
		if(calc < 1f) {
			point.setAlpha(calc);
			return false;
		} else {
			point.setAlpha(1f);
			return true;
		}
	}
	public static boolean tearOut(GLMutiPointShape mShape, float step) {
		float calc = mShape.getPointAlpha(0) - step;
		if(calc > 0) {
			mShape.setAlpha(calc);
			return false;
		} else {
			mShape.setAlpha(0);
			return true;
		}
	}
	public static boolean tearIn(GLMutiPointShape mShape, float step) {
		float calc = mShape.getPointAlpha(0) + step;
		if(calc < 1f) {
			mShape.setAlpha(calc);
			return false;
		} else {
			mShape.setAlpha(1f);
			return true;
		}
	}
}
