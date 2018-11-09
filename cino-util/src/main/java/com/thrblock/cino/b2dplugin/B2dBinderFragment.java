package com.thrblock.cino.b2dplugin;

import org.jbox2d.dynamics.Body;

import com.thrblock.cino.concept.Polygon;
import com.thrblock.cino.glanimate.IPureFragment;

/**
 * Box2d 绑定帧逻辑<br />
 * 得到保证图形对象与Box2d刚体的数据一致的帧逻辑
 * @author thrblock
 *
 */
public abstract class B2dBinderFragment implements IPureFragment {
    private Polygon shape;
    private Body bindBody;
    protected B2dBinderFragment(Polygon shape,Body bindBody) {
        this.shape = shape;
        this.bindBody = bindBody;
    }
    public Polygon getShape() {
        return shape;
    }
    public Body getBindBody() {
        return bindBody;
    }
}
