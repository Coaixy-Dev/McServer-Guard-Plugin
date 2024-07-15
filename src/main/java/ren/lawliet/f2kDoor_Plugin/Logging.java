package ren.lawliet.f2kDoor_Plugin;

import org.bukkit.Bukkit;

/**
 * @author Coaixy
 * @createTime 2024-07-15
 * @packageName ren.lawliet.f2kDoor_Plugin
 */

public class Logging {
    public static void info(String msg) {
        Bukkit.getServer().getLogger().info(msg);
    }
}
