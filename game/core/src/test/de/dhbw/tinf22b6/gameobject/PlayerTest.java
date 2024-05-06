package de.dhbw.tinf22b6.gameobject;

public class PlayerTest {

    // private static final float VIEWPORT_WIDTH = 500;
    // private static final float VIEWPORT_HEIGHT = 500;
    // private Player player;
    // private Weapon weapon;

    // @BeforeEach
    // public void setup() {
    //     // Create a mock weapon
    //     // weapon = mock(Weapon.class);

    //     World world = new World(new Vector2(0, 0), false);

    //     OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    //     // Create a player with mock dependencies
    //     player = new Player(world, new Vector2(0, 0), camera);
    //     // player.setWeapon(weapon);
    // }

    // @Test
    // public void testTick_UpdateRemainingCooldown() {
    //     // Set up
    //     float delta = 0.1f;

    //     // Execute
    //     player.tick(delta);

    //     // Verify
    //     verify(weapon).updateRemainingCoolDown(delta);
    // }

    // @Test
    // public void testTick_ApplyForce() {
    //     // Set up
    //     float delta = 0.1f;

    //     // Execute
    //     player.tick(delta);

    //     // Verify
    //     verify(player).applyForce(any(Vector2.class));
    // }

    // @Test
    // public void testTick_SetPlayerState_Idle() {
    //     // Set up
    //     player.getMotionVector().set(0, 0);

    //     // Execute
    //     player.tick(0.1f);

    //     // Verify
    //     assertEquals(PlayerState.IDLE, player.getState());
    // }

    // @Test
    // public void testTick_SetPlayerState_Walking() {
    //     // Set up
    //     player.getMotionVector().set(1, 0); // non-zero motion vector

    //     // Execute
    //     player.tick(0.1f);

    //     // Verify
    //     assertEquals(PlayerState.WALKING, player.getState());
    // }

    // @Test
    // public void testTick_SetPlayerDirection() {
    //     // Set up
    //     player.getMotionVector().set(1, 0); // Non-zero motion vector

    //     // Execute
    //     player.tick(0.1f);

    //     // Verify
    //     assertEquals(Direction.RIGHT, player.getDirection());
    // }

    // // Similarly, write tests for updating the dodge state time and position

    // @Test
    // public void testTick_UpdateDodgeStateTime() {
    //     // Set up
    //     float initialDodgeStateTime = player.getDodgeStateTime();
    //     float delta = 0.1f;

    //     // Execute
    //     player.tick(delta);

    //     // Verify
    //     assertEquals(initialDodgeStateTime + delta, player.getDodgeStateTime());
    // }

    // @Test
    // public void testTick_UpdatePosition_NotDodging() {
    //     // Set up
    //     float initialX = player.getPos().x;
    //     float initialY = player.getPos().y;

    //     // Execute
    //     player.tick(0.1f);

    //     // Verify
    //     assertNotEquals(initialX, player.getPos().x);
    //     assertNotEquals(initialY, player.getPos().y);
    // }
}
