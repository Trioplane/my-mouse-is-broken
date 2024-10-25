package trplnr.my_mouse_is_broken;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import trplnr.my_mouse_is_broken.mixin.client.MouseInvoker;

public class FakeMouse {
    MinecraftClient client;
    public FakeMouse(MinecraftClient client) {
        this.client = client;
    }

    void moveMouse(int[] pressedButtons, float delta) {
        MouseInvoker mouseInvoker = ((MouseInvoker) client.mouse);
        Window window = client.getWindow();

        long windowHandle = window.getHandle();

        double mouseSensitivity = client.options.getMouseSensitivity().getValue();
        double windowWidth = window.getWidth();
        double windowHeight = window.getHeight();

        double mouseX = client.mouse.getX();
        double mouseY = client.mouse.getY();

        double stepSize = mouseSensitivity * ((windowWidth + windowHeight) / 2) * delta;

        double desiredX = mouseX + ((-pressedButtons[2] + pressedButtons[3]) * stepSize);
        double desiredY = mouseY + ((-pressedButtons[0] + pressedButtons[1]) * stepSize);

        mouseInvoker.invokeOnCursorPos(windowHandle, desiredX, desiredY);
    }
}
