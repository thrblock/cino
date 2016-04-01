package com.thrblock.cino.glshape.templete;

public class TpFloat {
	@FunctionalInterface
	public static interface FloatGetter{
		public float getFloat();
	}
	@FunctionalInterface
	public static interface FloatSetter{
		public void setFloat(float num);
	}
	public static boolean linearAdd(FloatGetter getter,FloatSetter setter,float aim,float step) {
		float added = getter.getFloat() + step;
		if(added >= aim) {
			setter.setFloat(aim);
			return true;
		} else {
			setter.setFloat(added);
			return false;
		}
	}
	public static boolean linearReduce(FloatGetter getter,FloatSetter setter,float aim,float step) {
		float reduced = getter.getFloat() - step;
		if(reduced <= aim) {
			setter.setFloat(aim);
			return true;
		} else {
			setter.setFloat(reduced);
			return false;
		}
	}
}
