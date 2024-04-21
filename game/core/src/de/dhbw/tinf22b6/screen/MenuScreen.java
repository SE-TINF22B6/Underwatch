package de.dhbw.tinf22b6.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.dhbw.tinf22b6.ui.menu.StageManager;
import de.dhbw.tinf22b6.world.WorldType;

import static de.dhbw.tinf22b6.util.Constants.VIEWPORT_HEIGHT;
import static de.dhbw.tinf22b6.util.Constants.VIEWPORT_WIDTH;

public class MenuScreen extends AbstractGameScreen {
    private static final float MAX_BLUR = 4f;
    private final Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/main_menu.mp3"));
    private final TiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final Batch spriteBatch;
    private final ShaderProgram blurShader;
    private final ShaderProgram defaultShader;
    private FrameBuffer fbo;
    private FrameBuffer blurTargetA;
    private FrameBuffer blurTargetB;
    private StageManager stageManager;
    private float circlePosition;

    public MenuScreen(Game game) {
        super(game);
        renderer = new OrthogonalTiledMapRenderer(WorldType.MAIN_MENU.getMap());
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        spriteBatch = new SpriteBatch();
        defaultShader = spriteBatch.getShader();
        blurShader = createBlurShader();
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int) VIEWPORT_WIDTH, (int) VIEWPORT_HEIGHT, false);
    }

    private ShaderProgram createBlurShader() {
        ShaderProgram program = new ShaderProgram(Gdx.files.internal("shaders/blurShader.vert"), Gdx.files.internal("shaders/blurShader.frag"));
        if (!program.isCompiled()) {
            throw new GdxRuntimeException(program.getLog());
        }
        return program;
    }

    private void buildFBO(int width, int height) {
        if (width == 0 || height == 0) return;

        if (fbo != null) fbo.dispose();
        if (blurTargetA != null) blurTargetA.dispose();
        if (blurTargetB != null) blurTargetB.dispose();

        GLFrameBuffer.FrameBufferBuilder frameBufferBuilder = new GLFrameBuffer.FrameBufferBuilder(width, height);
        frameBufferBuilder.addBasicColorTextureAttachment(Pixmap.Format.RGBA8888);
        fbo = frameBufferBuilder.build();

        float blurScale = 1f;
        blurTargetA = new FrameBuffer(Pixmap.Format.RGBA8888, (int) (width * blurScale), (int) (height * blurScale), false);
        blurTargetB = new FrameBuffer(Pixmap.Format.RGBA8888, (int) (width * blurScale), (int) (height * blurScale), false);
    }

    private Texture blurTexture(Texture fboTex) {
        spriteBatch.setShader(blurShader);
        int pingPongCount = 4;
        for (int i = 0; i < pingPongCount; i++) {
            // Horizontal blur pass
            blurTargetA.begin();
            spriteBatch.begin();
            blurShader.setUniformf("dir", .5f, 0);
            blurShader.setUniformf("radius", MAX_BLUR);
            blurShader.setUniformf("resolution", Gdx.graphics.getWidth());
            spriteBatch.draw(i == 0 ? fboTex : blurTargetB.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1, 1);
            spriteBatch.end();
            blurTargetA.end();

            // Verticle blur pass
            blurTargetB.begin();
            spriteBatch.begin();
            blurShader.setUniformf("dir", 0, .5f);
            blurShader.setUniformf("radius", MAX_BLUR);
            blurShader.setUniformf("resolution", Gdx.graphics.getHeight());
            spriteBatch.draw(blurTargetA.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1, 1);
            spriteBatch.end();
            blurTargetB.end();
        }
        spriteBatch.setShader(defaultShader);

        return blurTargetB.getColorBufferTexture();
    }

    @Override
    public void render(float deltaTime) {
        fbo.begin();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        fbo.end();

        // Get the color texture from the fbo
        Texture fboTex = fbo.getColorBufferTexture();

        Texture blurredResult = blurTexture(fboTex);

        spriteBatch.begin();
        spriteBatch.draw(blurredResult, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1, 1);
        spriteBatch.end();
        stageManager.drawAndAct();
        moveCamera(deltaTime);
    }

    private void moveCamera(float delta) {
        circlePosition += delta;
        circlePosition %= (float) (2 * Math.PI);
        camera.translate((float) Math.sin(circlePosition), (float) Math.cos(circlePosition));
        camera.update();
    }

    @Override
    public void show() {
        menuMusic.setLooping(true);
        menuMusic.setVolume(Gdx.app.getPreferences("Controls").getBoolean("muteMusic") ? 0 : Gdx.app.getPreferences("Controls").getFloat("music"));
        menuMusic.play();
        stageManager = new StageManager(game, menuMusic);
        camera.position.set(150, 300, 0);
        camera.update();
    }

    @Override
    public void hide() {
        menuMusic.stop();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {
        spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        buildFBO(width, height);
        stageManager.resize(width, height);
    }

    public void dispose() {
        stageManager.dispose();
    }

}
