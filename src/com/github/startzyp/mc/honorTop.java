package com.github.startzyp.mc;

import com.github.startzyp.mc.Dao.DaoTool;
import com.github.startzyp.mc.View.ViewPage;
import com.github.startzyp.mc.entity.Honorentity;
import com.github.startzyp.mc.entity.PlayerModel;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class honorTop extends JavaPlugin implements Listener {

    public static HashMap<String,Honorentity> MapHonor = new HashMap<>();
    public static HashMap<String, PlayerModel> PlayerInfo = new HashMap<>();

    @Override
    public void onEnable(){
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        ReloadConfig();
        new DaoTool(getDataFolder()+File.separator+"Honor");
        super.onEnable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (sender instanceof Player){
            if (label.equalsIgnoreCase("cjiu")){
                if (args.length==1){
                    if (args[0].equalsIgnoreCase("top")){
                        String s = DaoTool.GetPlayerTop(10);
                        String[] split = s.split(",");
                        for (String temp:split){
                            sender.sendMessage(temp);
                        }
                    }
                }else if (args.length==2){
                    if (args[0].equalsIgnoreCase("list")){
                        OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(args[1]);
                        if (player.isOnline()){
                            ViewPage.OpenInventoryHoronList((Player) sender,player.getPlayer(),1);
                        }else {
                            sender.sendMessage("§e§l该玩家不在线");
                        }
                    }
                }
            }
        }
        return super.onCommand(sender, command, label, args);
    }


    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event){
        String name = event.getPlayer().getName();
        PlayerInfo.remove(name);
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){
        if (event.getInventory().getTitle().contains("荣誉：")){
            event.setCancelled(true);
            int slot = event.getSlot();
            if (slot==53){
                ItemStack currentItem = event.getCurrentItem();
                if (currentItem!=null){
                    String displayName = currentItem.getItemMeta().getDisplayName();
                    if (displayName.contains("§7点击翻页")){
                        displayName = displayName.replace("§7点击翻页","");
                        String name = getSubString(event.getInventory().getItem(4).getItemMeta().getDisplayName(), "§d", "§4的成就");
                        OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(name);
                        if (player.isOnline()){
                            ViewPage.OpenInventoryHoronList((Player) event.getWhoClicked(),player.getPlayer(),Integer.parseInt(displayName));
                        }else {
                            ((Player)event.getWhoClicked()).sendMessage("§6§l对方离线了");
                        }
                    }
                }
            }
        }
    }
    public static String getSubString(String text, String left, String right){
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        }else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            }else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }



    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        String Playername = event.getPlayer().getName();
        Set<String> HonorId = MapHonor.keySet();
        List<String> honors = new ArrayList<>();
        int TotalValue  = 0;
        for (String id:HonorId){
            Honorentity honorentity = MapHonor.get(id);
            if (event.getPlayer().hasPermission(honorentity.getPermission())){
                honors.add(id);
                TotalValue += honorentity.getHonorValue();
            }
        }
        if (!event.getPlayer().isOp()){
            int i = DaoTool.GetPlayerDay(Playername);
            if (i==-1){
                DaoTool.AddData(Playername,String.valueOf(TotalValue),String.valueOf(honors.size()));
            }else {
                DaoTool.Updata(Playername,String.valueOf(TotalValue),String.valueOf(honors.size()));
            }
        }
        PlayerInfo.put(Playername,new PlayerModel(honors,TotalValue));
    }


    private void ReloadConfig(){
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(),"config.yml");
        if (!(file.exists())){
            saveDefaultConfig();
        }
        Set<String> mines = getConfig().getConfigurationSection("Honor").getKeys(false);
        System.out.println(mines.size());
        for (String temp:mines){
            String DisPlayer = getConfig().getString("Honor."+temp+".DisPlayer");
            System.out.println(DisPlayer);
            String explain = getConfig().getString("Honor."+temp+".explain");
            String permission = getConfig().getString("Honor."+temp+".permission");
            int HonorValue = getConfig().getInt("Honor."+temp+".HonorValue");
            String id = getConfig().getString("Honor."+temp+".Id");
            MapHonor.put(temp,new Honorentity(DisPlayer,explain,permission,HonorValue,id));
        }
    }
}
