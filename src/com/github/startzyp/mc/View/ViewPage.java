package com.github.startzyp.mc.View;

import com.github.startzyp.mc.entity.Honorentity;
import com.github.startzyp.mc.entity.PlayerModel;
import com.github.startzyp.mc.honorTop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPage {

    public static void OpenInventoryHoronList(Player openPlayer,Player player, int Pagenum){
        PlayerModel playerModel = honorTop.PlayerInfo.get(player.getName());
        List<String> honorid = playerModel.getHonorid();
        Inventory inventoryList = Bukkit.createInventory(null,9*6,"§b荣誉：§a"+playerModel.getTotalHonorValue()+" §b徽章：§a"+honorid.size());

        //顶部牌子
        ItemStack Sign =new ItemStack(Material.ITEM_FRAME,1);
        ItemMeta itemMeta = Sign.getItemMeta();
        itemMeta.setDisplayName("§d"+player.getName()+"§4的成就");
        Sign.setItemMeta(itemMeta);
        inventoryList.setItem(4,Sign);
        //玻璃
        ItemStack Glass = new ItemStack(Material.THIN_GLASS,1);
        itemMeta.setDisplayName("§r");
        Glass.setItemMeta(itemMeta);
        for (int a=9;a<=17;a++){
            inventoryList.setItem(a,Glass);
        }
        if (!(honorid.size()==0)){
            List<String> pagelast = page(honorid, 26, Pagenum);
            int maxPagenum = (honorid.size()/26)+1;
            System.out.println(maxPagenum+"最大页数-当前页数"+Pagenum+"分页数据:"+pagelast.size());
            if (Pagenum>=maxPagenum){
                for (int i = 18;i<18+pagelast.size();i++){
                    Honorentity honorentity = honorTop.MapHonor.get(pagelast.get(i-18));
                    ItemStack item = new ItemStack(Integer.parseInt(honorentity.getItemId()));
                    itemMeta.setDisplayName(honorentity.getDisPlayName());
                    itemMeta.setLore(Arrays.asList("§r","§9获取方式：",honorentity.getExplain(),"§9荣誉值：",String.valueOf(honorentity.getHonorValue())));
                    item.setItemMeta(itemMeta);
                    inventoryList.setItem(i,item);
                }
            }else {
                ItemStack Page = new ItemStack(Material.PAPER,1);
                itemMeta.setDisplayName("§7点击翻页"+(Pagenum+1));
                Page.setItemMeta(itemMeta);
                inventoryList.setItem(53,Page);
                for (int i = 18;i<18+pagelast.size();i++){
                    Honorentity honorentity = honorTop.MapHonor.get(pagelast.get(i-18));
                    ItemStack item = new ItemStack(Integer.parseInt(honorentity.getItemId()));
                    itemMeta.setDisplayName(honorentity.getDisPlayName());
                    itemMeta.setLore(Arrays.asList("§r","§9获取方式：",honorentity.getExplain(),"§9荣誉值：",String.valueOf(honorentity.getHonorValue())));
                    item.setItemMeta(itemMeta);
                    inventoryList.setItem(i,item);
                }
            }
        }
        openPlayer.openInventory(inventoryList);

    }


    private static List<String> page(List<String> dataList, int pageSize,int currentPage) {
        List<String> currentPageList = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            int currIdx = (currentPage > 1 ? (currentPage - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < dataList.size() - currIdx; i++) {
                String data = dataList.get(currIdx + i);
                currentPageList.add(data);
            }
        }
        return currentPageList;
    }


}
