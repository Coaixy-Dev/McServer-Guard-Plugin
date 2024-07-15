package ren.lawliet.f2kDoor_Plugin;

import org.bukkit.plugin.Plugin;

/**
 * @author Coaixy
 * @createTime 2024-07-15
 * @packageName ren.lawliet.f2kDoor_Plugin
 */

public class ReflectHelper {
    private final Plugin targetPlugin;

    public ReflectHelper(Plugin targetPlugin) {
        this.targetPlugin = targetPlugin;
    }


    public Plugin getTargetPlugin() {
        return targetPlugin;
    }
}
