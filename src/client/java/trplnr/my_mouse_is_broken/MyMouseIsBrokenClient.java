package trplnr.my_mouse_is_broken;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MyMouseIsBrokenClient implements ClientModInitializer {
	public static final String MOD_ID = "my-mouse-is-broken";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static KeyBinding keybindLookUp;
	public static KeyBinding keybindLookDown;
	public static KeyBinding keybindLookLeft;
	public static KeyBinding keybindLookRight;
	public static FakeMouse fakeMouse;
	private long previousFrameTimeMs;

	public int[] getPressedButtons() {
		int pressingUp = keybindLookUp.isPressed() ? 1 : 0;
		int pressingDown = keybindLookDown.isPressed() ? 1 : 0;
		int pressingLeft = keybindLookLeft.isPressed() ? 1 : 0;
		int pressingRight = keybindLookRight.isPressed() ? 1 : 0;

		return new int[] {pressingUp, pressingDown, pressingLeft, pressingRight};
	}

	@Override
	public void onInitializeClient() {
		fakeMouse = new FakeMouse(MinecraftClient.getInstance());
		previousFrameTimeMs = Util.getMeasuringTimeMs();

		keybindLookUp = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.my_mouse_is_broken.lookUp",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_UP,
				"category.my_mouse_is_broken.keybindings"
		));
		keybindLookDown = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.my_mouse_is_broken.lookDown",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_DOWN,
				"category.my_mouse_is_broken.keybindings"
		));
		keybindLookLeft = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.my_mouse_is_broken.lookLeft",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_LEFT,
				"category.my_mouse_is_broken.keybindings"
		));
		keybindLookRight = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.my_mouse_is_broken.lookRight",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_RIGHT,
				"category.my_mouse_is_broken.keybindings"
		));

		WorldRenderEvents.END.register(listener -> {
			int[] pressedButtons = getPressedButtons();

			long currentFrameTimeMs = Util.getMeasuringTimeMs();
			float delta = (currentFrameTimeMs - previousFrameTimeMs) / 1000.0f;
			previousFrameTimeMs = currentFrameTimeMs;

			if (Arrays.equals(pressedButtons, new int[]{0,0,0,0})) return;
			if (Arrays.equals(pressedButtons, new int[]{1,1,0,0})) return;
			if (Arrays.equals(pressedButtons, new int[]{0,0,1,1})) return;
			if (Arrays.equals(pressedButtons, new int[]{1,1,1,1})) return;

			fakeMouse.moveMouse(pressedButtons, delta);
		});

		LOGGER.info("My Mouse is Broken is ready to use.");
	}
}