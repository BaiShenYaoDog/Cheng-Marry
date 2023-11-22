package cn.ChengZhiYa.Marry.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

import static cn.ChengZhiYa.Marry.Utils.Util.*;

public final class Marry extends BukkitRunnable {
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String PlayerName = player.getName();
            if (getStringHashmap().get(PlayerName + "_Marry") != null && getIntHashmap().get(PlayerName + "_MarryTime") != null) {
                int MarryTime = getIntHashmap().get(PlayerName + "_MarryTime");
                getIntHashmap().put(PlayerName + "_MarryTime",MarryTime-1);
                if (Bukkit.getPlayer(getStringHashmap().get(PlayerName + "_Marry")) == null) {
                    player.sendMessage(ChatColor("&6对方离线了，已自动取消!"));
                    getStringHashmap().remove(PlayerName + "_Marry");
                    getIntHashmap().remove(PlayerName + "_MarryTime");
                }
                if (MarryTime <= 0) {
                    Objects.requireNonNull(Bukkit.getPlayer(getStringHashmap().get(PlayerName + "_Marry"))).sendMessage(ChatColor("&660秒你没有接受或拒绝" + player.getName() + "的求婚，已自动取消!"));
                    player.sendMessage(ChatColor("&6对方60秒没有接受或拒绝求婚，已自动取消!"));
                    getStringHashmap().remove(PlayerName + "_Marry");
                    getIntHashmap().remove(PlayerName + "_MarryTime");
                }
            }
        }
    }
}
