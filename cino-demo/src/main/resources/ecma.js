var Color = Java.type('java.awt.Color');
var KeyEvent = Java.type('java.awt.event.KeyEvent');
var File = Java.type('java.io.File');
var GL2ES2 = Java.type('com.jogamp.opengl.GL2ES2');
var GLShader = Java.type('com.thrblock.cino.shader.GLShader');
var GLProgram = Java.type('com.thrblock.cino.shader.GLProgram');
var GLCommonUniform = Java.type('com.thrblock.cino.shader.data.GLCommonUniform');
var GLFrameBufferObject = Java.type('com.thrblock.cino.gllayer.GLFrameBufferObject');

var vex = new GLShader(GL2ES2.GL_VERTEX_SHADER, new File("./shadersV2/demo/Vertex.txt"));
var frg = new GLShader(GL2ES2.GL_FRAGMENT_SHADER, new File("./shadersV2/demo/Frag_49899.0"));
var program = new GLProgram(vex, frg);
$.auto(commonsUniform.setCommonUniform(program));
var fbo = fboManager.generateLayerFBO(0);
$.onActivited(function() {fbo.setGLProgram(program);});
$.onDeactivited(function() {fbo.setGLProgram(null);});
pannel.activited();
logger.info("ecma run complete.");