var Color = Java.type('java.awt.Color');
var KeyEvent = Java.type('java.awt.event.KeyEvent');
var File = Java.type('java.io.File');
var GL2ES2 = Java.type('com.jogamp.opengl.GL2ES2');
var GLShader = Java.type('com.thrblock.cino.shader.GLShader');
var GLProgram = Java.type('com.thrblock.cino.shader.GLProgram');
var GLCommonUniform = Java.type('com.thrblock.cino.shader.data.GLCommonUniform');
var GLFrameBufferObject = Java.type('com.thrblock.cino.gllayer.GLFrameBufferObject');

$.autoKeyPushPop();
var vex = new GLShader(GL2ES2.GL_VERTEX_SHADER, new File("./shadersV2/demo/Vertex.txt"));
var frg = new GLShader(GL2ES2.GL_FRAGMENT_SHADER, new File("./shadersV2/demo/Frag_Mandelbrot"));
var program = new GLProgram(vex, frg);
$.auto(commonsUniform.setCommonUniform(program));
var fbo = fboManager.generateLayerFBO(0);
$.onDestroy(() => fboManager.removeFBO(fbo));
$.onActivited(() => fbo.setGLProgram(program));
$.onDeactivited(() => fbo.setGLProgram(null));
pannel.activited();
logger.info("ecma run complete.");