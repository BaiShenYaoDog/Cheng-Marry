package cn.ChengZhiYa.Marry.Listeners;

import cn.ChengZhiYa.Marry.main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class PlayerQuit implements Listener {
    @EventHandler
    public void On_Event(PlayerQuitEvent event){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        main.main.getConfig().set(event.getPlayer().getName() + "_QuitDate",formatter.format(date));
    }
}
