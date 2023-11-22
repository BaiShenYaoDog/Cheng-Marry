package cn.ChengZhiYa.Marry.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static cn.ChengZhiYa.Marry.main.main;

public final class Util {
    static HashMap<String, Integer> IntHashmap = new HashMap<>();
    static HashMap<String, String> StringHashmap = new HashMap<>();


    public static void ColorLog(String Message) {
        CommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage(ChatColor(Message));
    }

    public static String ChatColor(String Message) {
        return ChatColor.translateAlternateColorCodes('&',Message);
    }

    public static HashMap<String, Integer> getIntHashmap() {
        return IntHashmap;
    }

    public static HashMap<String, String> getStringHashmap() {
        return StringHashmap;
    }

    public static String getCouples(Player player) {
        if (main.getConfig().getString(player.getName() + "_Couples") != null) {
            return main.getConfig().getString(player.getName() + "_Couples");
        }
        return "无";
    }

    public static boolean ifIsMarry(Player player) {
        return main.getConfig().getString(player.getName() + "_Couples") != null;
    }

    public static Integer getMarryID(Player player) {
        return main.getConfig().getInt(player.getName() + ".MarryID");
    }
}
