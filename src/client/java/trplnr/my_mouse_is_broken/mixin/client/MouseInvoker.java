package trplnr.my_mouse_is_broken.mixin.client;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Mouse.class)
public interface MouseInvoker {
    @Invoker("onCursorPos")
    public void invokeOnCursorPos(long window, double x, double y);
}
