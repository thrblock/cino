package com.thrblock.cino.b2dplugin;

import org.jbox2d.dynamics.Body;

import com.thrblock.cino.glanimate.IPureFragment;
import com.thrblock.cino.glshape.GLPolygonShape;

/**
 * Box2d 绑定帧逻辑<br />
 * 得到保证图形对象与Box2d刚体的数据一致的帧逻辑
 * @author thrblock
 *
 */
public abstract class B2dBinderFragment implements IPureFragment {
    private GLPolygonShape glShape;
    private Body bindBody;
    protected B2dBinderFragment(GLPolygonShape glShape,Body bindBody) {
        this.glShape = glShape;
        this.bindBody = bindBody;
    }
    public GLPolygonShape getGlShape() {
        return glShape;
    }
    public Body getBindBody() {
        return bindBody;
    }
}
