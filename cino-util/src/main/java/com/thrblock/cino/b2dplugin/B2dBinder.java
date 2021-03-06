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
import com.thrblock.cino.concept.Polygon;
import com.thrblock.cino.concept.Rect;
import com.thrblock.cino.glanimate.GLFragmentManager;

/**
 * Box2d 图形绑定工具
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class B2dBinder {
    @Autowired
    private CinoFrameConfig config;
    /**
     * 定义 Box2d 的1meter 相当于cino渲染窗格的多少像素
     */
    private float meterToPixel = 1f;
    private float friction = 0.3f;
    private float restitution = 0.3f;
    private float density = 1.0f;
    private World world;
    private GLFragmentManager fragmentManager;

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
    
    public GLFragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(GLFragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
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
     * @param rect
     *            矩形
     * @param dynamci
     *            是否是动态图形
     * @return B2dBinderFragment 绘制片段逻辑
     */
    public B2dBinderFragment bindRectToCircleAsFrag(Rect rect, boolean dynamci) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = dynamci ? BodyType.DYNAMIC : BodyType.STATIC;
        bodyDef.position.set(rect.getCentralX() / meterToPixel, rect.getCentralY() / meterToPixel);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        float radius = rect.getWidth() > rect.getHeight() ? rect.getHeight() / 2 : rect.getWidth() / 2;
        shape.setRadius(radius / meterToPixel);

        Fixture fixture = body.createFixture(shape, density);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);
        
        return generate(rect, body);
    }
    
    /**
     * 将GLRect绑定为圆形刚体 圆形为矩形内切
     * 
     * @param rect
     *            gl矩形
     * @param dynamci
     *            是否是动态图形
     * @param enable
     *            是否自动启动片段
     * @return Body 与指定GLRect相关的b2d body
     */
    public Body bindRectToCircle(Rect rect, boolean dynamci, boolean enable) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = dynamci ? BodyType.DYNAMIC : BodyType.STATIC;
        bodyDef.position.set(rect.getCentralX() / meterToPixel, rect.getCentralY() / meterToPixel);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        float radius = rect.getWidth() > rect.getHeight() ? rect.getHeight() / 2 : rect.getWidth() / 2;
        shape.setRadius(radius / meterToPixel);

        Fixture fixture = body.createFixture(shape, density);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);
        
        if(enable) {
            this.fragmentManager.addFragment(generate(rect, body));
        }
        return body;
    }
    
    /**
     * 将GLRect绑定为圆形刚体 圆形为矩形内切
     * 
     * @param rect
     *            矩形
     * @param dynamci
     *            是否是动态图形
     * @return Body 与指定GLRect相关的b2d body
     */
    public Body bindRectToCircle(Rect rect,boolean dynamci) {
        return bindRectToCircle(rect,dynamci,true);
    }

    /**
     * 将GLRect绑定为矩形刚体
     * 
     * @param rect
     *            gl矩形
     * @param dynamci
     *            是否是动态图形
     * @param enable
     *            是否自动启动
     * @return B2dBinderFragment 绘制片段逻辑
     */
    public B2dBinderFragment bindRectToRectAsFrag(Rect rect, boolean dynamci, boolean enable) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = dynamci ? BodyType.DYNAMIC : BodyType.STATIC;
        bodyDef.position.set(rect.getCentralX() / meterToPixel, rect.getCentralY() / meterToPixel);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((rect.getWidth() / 2) / meterToPixel, (rect.getHeight() / 2) / meterToPixel);

        Fixture fixture = body.createFixture(shape, density);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);
        B2dBinderFragment frag = generate(rect, body);
        if(enable) {
            this.fragmentManager.addFragment(frag);
        }
        return frag;
    }
    
    /**
     * 将GLRect绑定为矩形刚体
     * 
     * @param rect
     *            矩形
     * @param dynamci
     *            是否是动态图形
     * @param enable
     *            是否自动启动
     * @return Body 与指定GLRect相关的b2d body
     */
    public Body bindRectToRect(Rect rect, boolean dynamci, boolean enable) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = dynamci ? BodyType.DYNAMIC : BodyType.STATIC;
        bodyDef.position.set(rect.getCentralX() / meterToPixel, rect.getCentralY() / meterToPixel);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((rect.getWidth() / 2) / meterToPixel, (rect.getHeight() / 2) / meterToPixel);

        Fixture fixture = body.createFixture(shape, density);
        fixture.setFriction(friction);
        fixture.setRestitution(restitution);

        B2dBinderFragment frag = generate(rect, body);
        if(enable) {
            this.fragmentManager.addFragment(frag);
        }
        return body;
    }
    
    /**
     * 将GLRect绑定为矩形刚体
     * 
     * @param rect
     *            gl矩形
     * @param dynamci
     *            是否是动态图形
     * @return Body 与指定GLRect相关的b2d body
     */
    public Body bindRectToRect(Rect rect, boolean dynamci) {
        return bindRectToRect(rect,dynamci,true);
    }

    private B2dBinderFragment generate(Polygon shape, Body body) {
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
}
