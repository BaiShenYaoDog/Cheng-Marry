package cn.ChengZhiYa.Marry.Commands;

import cn.ChengZhiYa.Marry.Utils.Util;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.ChengZhiYa.Marry.Utils.Util.*;
import static cn.ChengZhiYa.Marry.main.*;

public final class Marry implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                Help(player);
                return false;
            }

            if (args.length == 1) {
                if (args[0].equals("help")) {
                    Help(player);
                }
                if (Util.ifIsMarry(player)) {
                    if (args[0].equals("tp")) {
                        if (Bukkit.getPlayer(Objects.requireNonNull(Util.getCouples(player))) == null) {
                            player.sendMessage(ChatColor("&c你的侣伴不在线,无法传送!"));
                            return false;
                        }
                        Player CouplesPlayer = Bukkit.getPlayer(Objects.requireNonNull(Util.getCouples(player)));
                        player.teleport(Objects.requireNonNull(CouplesPlayer).getLocation());
                        player.sendMessage(ChatColor("&a传送成功!"));
                        return false;
                    }
                    if (args[0].equals("divorce")) {
                        if (Bukkit.getPlayer(Objects.requireNonNull(Util.getCouples(player))) == null) {
                            player.sendMessage(ChatColor("&c你的伴侣不在线!无法离婚!"));
                            return false;
                        }
                        Objects.requireNonNull(Bukkit.getPlayer(Objects.requireNonNull(Util.getCouples(player)))).sendMessage(ChatColor("&a" + player.getName() + "和你离婚了!"));
                        player.sendMessage(ChatColor("&a离婚完成!"));
                        main.getConfig().set(Util.getCouples(player) + "_MarryID",null);
                        main.getConfig().set(Util.getCouples(player) + "_Couples",null);
                        main.getConfig().set(Util.getMarryID(player) + "_Home.World",null);
                        main.getConfig().set(Util.getMarryID(player) + "_Home.X",null);
                        main.getConfig().set(Util.getMarryID(player) + "_Home.Y",null);
                        main.getConfig().set(Util.getMarryID(player) + "_Home.Z",null);
                        main.getConfig().set(Util.getMarryID(player) + "_Home",null);
                        main.getConfig().set(player.getName() + "_MarryID",null);
                        main.getConfig().set(player.getName() + "_Couples",null);
                        main.saveConfig();
                        main.reloadConfig();
                        return false;
                    }
                    if (args[0].equals("online")) {
                        String Online = "&c离线";
                        String QuitTime = "&c无";
                        if (main.getConfig().getString(Util.getCouples(player) + "_QuitDate") != null) {
                            QuitTime = main.getConfig().getString(Util.getCouples(player) + "_QuitDate");
                        }
                        if (Bukkit.getPlayer(Util.getCouples(player)) != null) {
                            Online = "&a在线";
                        }
                        if (Online.equals("&a在线")) {
                            player.sendMessage(ChatColor(
                                    "&6你的侣伴&7: &e" + Util.getCouples(player) + "\n" +
                                    "&6在线状态&7: " + Online + "\n"));
                        }else {
                            player.sendMessage(ChatColor(
                                    "&6你的侣伴&7: &e" + Util.getCouples(player) + "\n" +
                                    "&6在线状态&7: " + Online + "\n" +
                                    "&6上次上线时间&7: &e" + QuitTime));
                        }
                    }
                }
                if (player.isOp()) {
                    if (args[0].equals("reload")) {
                        main.reloadConfig();
                        player.sendMessage(ChatColor("&a重载完成!"));
                        return false;
                    }
                }
                if (!Util.ifIsMarry(player)) {
                    if (Bukkit.getPlayer(args[0]) == null) {
                        player.sendMessage(ChatColor("&c这个玩家不在线!"));
                        return false;
                    }
                    if (Objects.requireNonNull(player.getAddress()).getHostName().equals(Objects.requireNonNull(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getAddress()).getHostName())) {
                        player.sendMessage(ChatColor("&c请不要自己和自己结婚"));
                        return false;
                    }
                    if (player.getName().equals(args[0])) {
                        player.sendMessage(ChatColor("&c请不要自己和自己结婚"));
                        return false;
                    }
                    if (Util.ifIsMarry(player)) {
                        player.sendMessage(ChatColor("&c你已经结婚了!"));
                        return false;
                    }
                    if (Util.ifIsMarry(Objects.requireNonNull(Bukkit.getPlayer(args[0])))) {
                        player.sendMessage(ChatColor("&c他已经结婚了!"));
                        return false;
                    }
                    if (main.getConfig().getInt( Objects.requireNonNull(Bukkit.getPlayer(args[0])) + "_MarryID") != 0) {
                        player.sendMessage(ChatColor("&c他已经结婚了!"));
                        return false;
                    }
                    if (main.getConfig().getInt(player.getName() + "_MarryID") != 0) {
                        player.sendMessage(ChatColor("&c你已经结婚了!"));
                        return false;
                    }
                    if (getIntHashmap().get(player.getName() + "_MarryTime") != null) {
                        player.sendMessage(ChatColor("&c你已经发送过一次求婚了!请等待" + getIntHashmap().get(player.getName() + "_MarryTime") + "后重试"));
                        return false;
                    }
                    TextComponent Message = new TextComponent();
                    TextComponent AccentMessage = new TextComponent(ChatColor("&7[&a同意&7]"));
                    AccentMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor("&a接受" + player.getName() + "的求婚"))));
                    AccentMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/m accept "+ player.getName()));
                    TextComponent RefuseMessage = new TextComponent(ChatColor("&7[&c拒绝&7]"));
                    RefuseMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor("&a拒绝" + player.getName() + "的求婚"))));
                    RefuseMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/m defuse "+ player.getName()));
                    TextComponent Null = new TextComponent("     ");
                    Message.addExtra(AccentMessage);
                    Message.addExtra(Null);
                    Message.addExtra(RefuseMessage);
                    Player TaggerPlayer = Bukkit.getPlayer(args[0]);
                    assert TaggerPlayer != null;
                    TaggerPlayer.sendMessage(ChatColor("&7======= &6求婚 &7======="));
                    TaggerPlayer.sendMessage(ChatColor("&6" + player.getName() + "向你求婚"));
                    TaggerPlayer.spigot().sendMessage(Message);
                    TaggerPlayer.sendMessage(ChatColor("&7======= &6求婚 &7======="));
                    player.sendMessage(ChatColor("&a你已向" + TaggerPlayer.getName() + "求婚!"));
                    getIntHashmap().put(player.getName() + "_MarryTime", 60);
                    getStringHashmap().put(player.getName() + "_Marry",TaggerPlayer.getName());
                }
                return false;
            }

            if (args.length == 2) {
                String TaggerPlayerName = args[1];
                if (args[0].equals("accept")) {
                    if (player.getName().equals(args[1])) {
                        player.sendMessage(ChatColor("&c请不要自己和自己结婚"));
                        return false;
                    }
                    if (Util.ifIsMarry(player)) {
                        player.sendMessage(ChatColor("&c你已经结婚了!"));
                        return false;
                    }
                    if (Util.ifIsMarry(Objects.requireNonNull(Bukkit.getPlayer(args[1])))) {
                        player.sendMessage(ChatColor("&c他已经结婚了!"));
                        return false;
                    }
                    if (main.getConfig().getInt( Objects.requireNonNull(Bukkit.getPlayer(args[1])) + "_MarryID") != 0) {
                        player.sendMessage(ChatColor("&c他已经结婚了!"));
                        return false;
                    }
                    if (main.getConfig().getInt(player.getName() + "_MarryID") != 0) {
                        player.sendMessage(ChatColor("&c你已经结婚了!"));
                        return false;
                    }
                    if (getIntHashmap().get(player.getName() + "_MarryTime") != null) {
                        player.sendMessage(ChatColor("&c你已经发送过一次求婚了!请等待" + getIntHashmap().get(player.getName() + "_MarryTime") + "后重试"));
                        return false;
                    }
                    if (Bukkit.getPlayer(args[1]) == null) {
                        player.sendMessage(ChatColor("&cTa下线了,接受失败!"));
                        return false;
                    }
                    getStringHashmap().remove(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getName() + "_Marry");
                    getIntHashmap().remove(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getName() + "_MarryTime");
                    main.getConfig().set(player.getName() + "_MarryID",main.getConfig().getInt("NewMarryID"));
                    main.getConfig().set(TaggerPlayerName + "_MarryID",main.getConfig().getInt("NewMarryID"));
                    main.getConfig().set(player.getName() + "_Couples", TaggerPlayerName);
                    main.getConfig().set(TaggerPlayerName + "_Couples", player.getName());
                    main.getConfig().set("NewMarryID",main.getConfig().getInt("NewMarryID")+1);
                    for (Player OnlinePlayer : Bukkit.getOnlinePlayers()) {
                        OnlinePlayer.sendMessage(ChatColor("&6喜报！！！" + player.getName() + "和" + TaggerPlayerName + "结婚了！！！"));
                    }
                    main.saveConfig();
                    main.reloadConfig();
                }
                if (args[0].equals("defuse")) {
                    if (Bukkit.getPlayer(args[1]) == null) {
                        player.sendMessage(ChatColor("&cTa下线了,拒绝失败!"));
                        return false;
                    }
                    getStringHashmap().remove(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getName() + "_Marry");
                    getIntHashmap().remove(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getName() + "_MarryTime");
                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).sendMessage(ChatColor(player.getName() + "&6拒绝了你的求婚!"));
                    player.sendMessage(ChatColor("&6你拒绝了" + Objects.requireNonNull(Bukkit.getPlayer(args[1])).getName() + "的求婚"));
                }
                return false;
            }
            player.sendMessage(ChatColor("&c命令无效,请使用/m help查询帮助"));
        }else {
            sender.sendMessage(ChatColor("&c这个命令只能在游戏内执行!"));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                List<String> TabList = new ArrayList<>();
                TabList.add("help");
                Player player = (Player) sender;
                if (player.isOp()) {
                    TabList.add("reload");
                }
                if (Util.ifIsMarry(player)) {
                    TabList.add("tp");
                    TabList.add("divorce");
                    TabList.add("online");
                }else {
                    for (Player OnlinePlayer : Bukkit.getOnlinePlayers()) {
                        TabList.add(OnlinePlayer.getName());
                    }
                }
                return TabList;
            }
        }
        return null;
    }

    public void Help(Player player) {
        if (Util.ifIsMarry(player)) {
            if (player.isOp()) {
                player.sendMessage(ChatColor("&7========= &6结婚系统 &7=========\n" +
                        "&a/m reload &7- &6重载配置文件\n" +
                        "&a/m tp &7- &6传送至侣伴旁边\n" +
                        "&a/m divorce &7- &6离婚\n" +
                        "&a/m online &7- &6查询伴侣在线状态\n" +
                        "\n" +
                        "&6你的侣伴: " + Util.getCouples(player) + "\n" +
                        "&7========= &6结婚系统 &7========="));
            }else {
                player.sendMessage(ChatColor("&7========= &6结婚系统 &7=========\n" +
                        "&a/m tp &7- &6传送至侣伴旁边\n" +
                        "&a/m divorce &7- &6离婚\n" +
                        "&a/m online &7- &6查询伴侣在线状态\n" +
                        "\n" +
                        "&6你的侣伴: " + Util.getCouples(player) + "\n" +
                        "&7========= &6结婚系统 &7========="));
            }
        }else {
            if (player.isOp()) {
                player.sendMessage(ChatColor("&7========= &6结婚系统 &7=========\n" +
                        "&a/m reload &7- &6重载配置文件\n\n" +
                        "&a/m <玩家ID> &7- &6向指定的玩家求婚\n" +
                        "\n" +
                        "&6你的侣伴: " + Util.getCouples(player) + "\n" +
                        "&7========= &6结婚系统 &7========="));
            }else {
                player.sendMessage(ChatColor("&7========= &6结婚系统 &7=========\n" +
                        "&a/m <玩家ID> &7- &6向指定的玩家求婚\n" +
                        "\n" +
                        "&6你的侣伴: " + Util.getCouples(player) + "\n" +
                        "&7========= &6结婚系统 &7========="));
            }
        }
    }
}
