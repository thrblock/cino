package com.thrblock.game.demo;

import java.awt.Color;
import java.awt.Font;

import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.cino.frame.AWTFrameFactory;
import com.thrblock.cino.glshape.GLCharArea;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.factory.GLShapeFactory;
import com.thrblock.cino.gltexture.GLFont;
import com.thrblock.cino.terrain2d.TerrainCalculator;
import com.thrblock.cino.terrain2d.TerrainUnit;
import com.thrblock.cino.util.math.CRand;
import com.thrblock.springcontext.CinoInitor;

public class TerrainDemo {
	private DemoUnit[][] map = new DemoUnit[15][15];
	private int movement = 5;
	private int x = 5;
	private int y = 5;
	private TerrainCalculator<DemoUnit> calc;
	private Displayer disp;
	static class DemoUnit extends TerrainUnit {
		private int cost;
		public DemoUnit(int i, int j, int cost) {
			super(i, j, 0);
			this.cost = cost;
		}
		@Override
		public int getCost(int moveType) {
			return cost;
		}
		public int getX() {
			return i;
		}
		public int getY() {
			return j;
		}
	}
	
	static class Displayer {
		private GLRect[][] rects;
		private GLCharArea[][] chars;
		public Displayer(GLShapeFactory builder,DemoUnit[][] map) {
			rects = new GLRect[map.length][map[0].length];
			chars = new GLCharArea[map.length][map[0].length];
			for(int i = 0;i < rects.length;i++) {
				for(int j = 0;j < rects[i].length;j++) {
					rects[i][j] = builder.buildGLRect(i * 30f, j * 30f, 30f,30f);
					rects[i][j].setFill(true);
					rects[i][j].setAlpha(0.5f);
					chars[i][j] = builder.buildGLCharArea(
							new GLFont(new Font("",Font.PLAIN,20)),
							i * 30,j * 30,
							30,30,
							String.valueOf(map[i][j].cost));
					chars[i][j].setVerAlia(GLCharArea.VER_CENTRAL);
					chars[i][j].setHorAlia(GLCharArea.HOR_CENTRAL);
				}
			}
		}
		
		public void show() {
			for(int i = 0;i < rects.length;i++) {
				for(int j = 0;j < rects[i].length;j++) {
					rects[i][j].show();
					chars[i][j].show();
				}
			}
		}
		public void hide() {
			for(int i = 0;i < rects.length;i++) {
				for(int j = 0;j < rects[i].length;j++) {
					rects[i][j].hide();
					chars[i][j].hide();
				}
			}
		}
		
		public void setColor(int i,int j,Color c) {
			rects[i][j].setAllPointColor(c);
		}
	}
	
	public TerrainDemo(GLShapeFactory builder){
		for(int i = 0;i < map.length;i++) {
			for(int j = 0;j < map[i].length;j++) {
				map[i][j] = new DemoUnit(i,j,CRand.getRandomNum(1, 2));
			}
		}
		calc = new TerrainCalculator<>(map);
		disp = new Displayer(builder, map);
	}
	
	private void test() {
		disp.show();
		for(DemoUnit unit:calc.calc(x, y, movement, 0)) {
			disp.setColor(unit.getX(),unit.getY(),Color.GREEN);
		}
		disp.setColor(x,y,Color.RED);
	}
	
	public static void main(String[] args) {
		AbstractApplicationContext context = CinoInitor.getContextByXml();
        AWTFrameFactory config = context.getBean(AWTFrameFactory.class);
		config.buildFrame();
        
        GLShapeFactory builder = context.getBean(GLShapeFactory.class);
		TerrainDemo demo = new TerrainDemo(builder);
		demo.test();
	}
}
