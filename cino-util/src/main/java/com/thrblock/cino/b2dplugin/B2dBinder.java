package com.thrblock.cino.b2dplugin;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.thrblock.cino.CinoFrameConfig;
import com.thrblock.cino.glanimate.GLAnimateFactory;
import com.thrblock.cino.glshape.GLPolygonShape;
import com.thrblock.cino.glshape.GLRect;

/**
 * Box2d 图形绑定工具
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class B2dBinder {
    @Autowired
    private CinoFrameConfig config;
    @Autowired
    protected GLAnimateFactory animateFactory;
    /**
     * 定义 Box2d 的1meter 相当于cino渲染窗格的多少像素
     */
    private float meterToPixel = 1f;
    private float friction = 0.3f;
    private float restitution = 0.3f;
    private float density = 1.0f;
    private World world;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    /**
     * 根据渲染窗格宽度定义box2d坐标与cino像素的转换关系
     * 
     * @param b2dScreenWidth
     *            屏幕宽度对应的box2d meter
     */
    public void mappingScreenWidth(float b2dScreenWidth) {
        meterToPixel = config.getScreenWidth() / b2dScreenWidth;
    }

    /**
     * 根据渲染窗格高度定义box2d坐标与cino像素的转换关系
     * 
     * @param b2dScreenHeight
     *            屏幕宽度对应的box2d meter
     */
    public void mappingScreenHeight(float b2dScreenHeight) {
        meterToPixel = config.getScreenHeight() / b2dScreenHeight;
    }

    public float getMeterToPixel() {
        return meterToPixel;
    }

    /**
     * 定义 Box2d 的1meter 相当于cino渲染窗格的多少像素
     * 
     * @param meterToPixel
     *            Box2d 的1meter 相当于cino渲染窗格的多少像素
     */
    public void setMeterToPixel(float meterToPixel) {
        this.meterToPixel = meterToPixel;
    }

    /**
     * 将GLRect绑定为圆形刚体 圆形为矩形内切
     * 
     * @param glRect
     *            gl矩形
     * @param dynamci
     *            是否是动态图形
     * @return B2dBinderFragment 绘制片段逻辑
     */
    public B2dBinderFragment bindRectToCircleAsFrag(GLRect glRect, boolean dynamci) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = dynamci ? BodyType.DYNAMIC : BodyType.STATIC;
        bodyDef.position.set(glRect.getCentralX() / meterToPixel, glRect.getCentralY() / meterToPixel);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        float radius = glRect.getWidth() > glRect.getHeight() ? glRect.getHeight() / 2 : glRect.getWidth() / 2;
        shape.setRadius(radius / meterToPixel);

        Fixture fixture = body.createFixture(shape, density);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);
        
        return generate(glRect, body);
    }
    
    /**
     * 将GLRect绑定为圆形刚体 圆形为矩形内切
     * 
     * @param glRect
     *            gl矩形
     * @param dynamci
     *            是否是动态图形
     * @param enable
     *            是否自动启动片段
     * @return Body 与指定GLRect相关的b2d body
     */
    public Body bindRectToCircle(GLRect glRect, boolean dynamci, boolean enable) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = dynamci ? BodyType.DYNAMIC : BodyType.STATIC;
        bodyDef.position.set(glRect.getCentralX() / meterToPixel, glRect.getCentralY() / meterToPixel);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        float radius = glRect.getWidth() > glRect.getHeight() ? glRect.getHeight() / 2 : glRect.getWidth() / 2;
        shape.setRadius(radius / meterToPixel);

        Fixture fixture = body.createFixture(shape, density);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);
        
        if(enable) {
            B2dBinderFragment frag = generate(glRect, body);
            animateFactory.build(frag).enable();
        }
        return body;
    }
    
    /**
     * 将GLRect绑定为圆形刚体 圆形为矩形内切
     * 
     * @param glRect
     *            gl矩形
     * @param dynamci
     *            是否是动态图形
     * @return Body 与指定GLRect相关的b2d body
     */
    public Body bindRectToCircle(GLRect glRect,boolean dynamci) {
        return bindRectToCircle(glRect,dynamci,true);
    }

    /**
     * 将GLRect绑定为矩形刚体
     * 
     * @param glRect
     *            gl矩形
     * @param dynamci
     *            是否是动态图形
     * @param enable
     *            是否自动启动
     * @return B2dBinderFragment 绘制片段逻辑
     */
    public B2dBinderFragment bindRectToRectAsFrag(GLRect glRect, boolean dynamci, boolean enable) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = dynamci ? BodyType.DYNAMIC : BodyType.STATIC;
        bodyDef.position.set(glRect.getCentralX() / meterToPixel, glRect.getCentralY() / meterToPixel);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((glRect.getWidth() / 2) / meterToPixel, (glRect.getHeight() / 2) / meterToPixel);

        Fixture fixture = body.createFixture(shape, density);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);
        B2dBinderFragment frag = generate(glRect, body);
        if(enable) {
            animateFactory.build(frag).enable();
        }
        return frag;
    }
    
    /**
     * 将GLRect绑定为矩形刚体
     * 
     * @param glRect
     *            gl矩形
     * @param dynamci
     *            是否是动态图形
     * @param enable
     *            是否自动启动
     * @return Body 与指定GLRect相关的b2d body
     */
    public Body bindRectToRect(GLRect glRect, boolean dynamci, boolean enable) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = dynamci ? BodyType.DYNAMIC : BodyType.STATIC;
        bodyDef.position.set(glRect.getCentralX() / meterToPixel, glRect.getCentralY() / meterToPixel);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((glRect.getWidth() / 2) / meterToPixel, (glRect.getHeight() / 2) / meterToPixel);

        Fixture fixture = body.createFixture(shape, density);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);

        B2dBinderFragment frag = generate(glRect, body);
        if(enable) {
            animateFactory.build(frag).enable();
        }
        return body;
    }
    
    /**
     * 将GLRect绑定为矩形刚体
     * 
     * @param glRect
     *            gl矩形
     * @param dynamci
     *            是否是动态图形
     * @return Body 与指定GLRect相关的b2d body
     */
    public Body bindRectToRect(GLRect glRect, boolean dynamci) {
        return bindRectToRect(glRect,dynamci,true);
    }

    private B2dBinderFragment generate(GLPolygonShape shape, Body body) {
        return new B2dBinderFragment(shape, body) {
            @Override
            public void fragment() {
                shape.setRadian(body.getAngle());
                Vec2 vec = body.getPosition();
                shape.setCentralX(vec.x * meterToPixel);
                shape.setCentralY(vec.y * meterToPixel);
            }
        };
    }

    public GLAnimateFactory getAnimateFactory() {
        return animateFactory;
    }

    public void setAnimateFactory(GLAnimateFactory animateFactory) {
        this.animateFactory = animateFactory;
    }
}
