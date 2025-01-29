package ru.kelcuprum.pplhelper.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.kelcuprum.alinlib.gui.toast.ToastBuilder;
import ru.kelcuprum.pplhelper.PepelandHelper;

import static ru.kelcuprum.alinlib.gui.Colors.CPM_BLUE;
import static ru.kelcuprum.alinlib.gui.Colors.SEADRIVE;
import static ru.kelcuprum.pplhelper.PepelandHelper.Icons.WHITE_PEPE;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {
    @Unique
    public boolean isGlobalChat = PepelandHelper.playerInPPL() && PepelandHelper.config.getBoolean("CHAT.GLOBAL", false) && PepelandHelper.config.getBoolean("CHAT.GLOBAL.TOGGLE", true);
    @Shadow public abstract String normalizeChatMessage(String message);
    @Shadow protected EditBox input;

    protected ChatScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void keyPressed(int i, int j, int k, CallbackInfoReturnable<Boolean> cir){
        if(i == GLFW.GLFW_KEY_G && k == GLFW.GLFW_MOD_CONTROL && PepelandHelper.playerInPPL()){
            if(PepelandHelper.playerInPPL()) changeGlobalChat();
            cir.setReturnValue(true);
        }
    }

    @Unique
    private void changeGlobalChat(){
        if(PepelandHelper.playerInPPL()) {
            isGlobalChat = !isGlobalChat;
            new ToastBuilder().setTitle(Component.translatable("pplhelper")).setMessage(Component.translatable("pplhelper.chat.global", isGlobalChat ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF)).setIcon(WHITE_PEPE).buildAndShow();
            PepelandHelper.config.setBoolean("CHAT.GLOBAL", isGlobalChat);
        }
    }
    @Inject(method = "render", at=@At("RETURN"))
    public void render(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci){
        assert minecraft != null;
        if(isGlobalChat && PepelandHelper.playerInPPL()) {
            guiGraphics.drawString(minecraft.font, Component.translatable("pplhelper.chat.global.on"), 5, input.getY()-5-minecraft.font.lineHeight, -1);
            guiGraphics.fill(2, height-2, width-2, height-1, SEADRIVE);
        }
    }

    @Inject(method="handleChatInput", at=@At("HEAD"), cancellable = true)
    public void handleChatInput(String string, boolean bl, CallbackInfo ci){
        if(!PepelandHelper.playerInPPL() && !isGlobalChat && PepelandHelper.config.getBoolean("CHAT.GLOBAL.TOGGLE", true)) return;
        string = this.normalizeChatMessage(string);
        if(!string.startsWith("/") && isGlobalChat) string = "/g "+string;
        if (!string.isEmpty()) {
            if (bl) this.minecraft.gui.getChat().addRecentChat(string);
            if (string.startsWith("/")) this.minecraft.player.connection.sendCommand(string.substring(1));
            else this.minecraft.player.connection.sendChat(string);
        }
        ci.cancel();
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void init(CallbackInfo ci){

    }
}
