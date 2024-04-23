package de.dhbw.tinf22b6;

import com.badlogic.gdx.math.Vector2;
import de.dhbw.tinf22b6.util.CameraHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CameraTests {
    private CameraHelper cameraHelper;

    @BeforeEach
    void setUpTest() {
        this.cameraHelper = new CameraHelper();
    }

    @Test
    @DisplayName("Camera Initializes at the right position")
    void cameraInitHasRightPosition() {
        assertEquals(new Vector2(0, 0), this.cameraHelper.getPosition());
    }

    @ParameterizedTest(name = "translate to ({0},{1})")
    @CsvSource({
            "0,    1",
            "1,    2",
            "-49,  51",
            "1,  -100"
    })
    void cameraTranslationWorksCorrectly(int x, int y) {
        this.cameraHelper.setPosition(x, y);
        assertEquals(new Vector2(x, y), this.cameraHelper.getPosition());
    }
}