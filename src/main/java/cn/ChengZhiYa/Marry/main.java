package cn.ChengZhiYa.Marry;

import cn.ChengZhiYa.Marry.Listeners.PlayerQuit;
import cn.ChengZhiYa.Marry.Tasks.Marry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static cn.ChengZhiYa.Marry.Utils.Util.ColorLog;

public final class main extends JavaPlugin {

    public static main main;
    @Override
    public void onEnable() {
        // Plugin startup logic
        main = this;
        ColorLog("&6=========&b橙式-结婚系统&6=========");
        saveDefaultConfig();
        reloadConfig();
        Objects.requireNonNull(getCommand("Marry")).setExecutor(new cn.ChengZhiYa.Marry.Commands.Marry());
        new Marry().runTaskTimerAsynchronously(this, 0L, 20);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        ColorLog("&a插件已启动！");
        ColorLog("&6=========&b橙式-结婚系统&6=========");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        main = null;
        ColorLog("&6=========&b橙式-结婚系统&6=========");
        ColorLog("&a插件已卸载！");
        ColorLog("&6=========&b橙式-结婚系统&6=========");
    }
}
