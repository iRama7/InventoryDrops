package rama.id;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class MineListener implements Listener {

    Plugin plugin;

    MineListener(Plugin plugin) {
        this.plugin = plugin;
    }

    private Boolean running = false;

    @EventHandler
    public void blockDropEvent(BlockDropItemEvent e){
        Player p = e.getPlayer();
        List<Item> items = e.getItems();
        if(e.isCancelled()){
            return;
        }
        if(p.getInventory().firstEmpty() > 0){
            for(Item item : items){
                p.getInventory().addItem(item.getItemStack());
            }
            e.setCancelled(true);
        }else{
            for(Item item : items){
                for(ItemStack playerItem : p.getInventory().getContents()){
                    if(playerItem != null && item.getItemStack().getType() == playerItem.getType()){
                        if((item.getItemStack().getAmount() + playerItem.getAmount()) <= 64){
                            p.getInventory().addItem(item.getItemStack());
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
        if(!e.isCancelled() && !items.isEmpty()){
            sendFullAction(p);
        }
    }

    public void sendFullAction(Player p){
        if(!running) {
            FileConfiguration configuration = plugin.getConfig();
            String title = ChatColor.translateAlternateColorCodes('&', configuration.getString("config.title"));
            String subtitle = ChatColor.translateAlternateColorCodes('&', configuration.getString("config.subtitle"));
            p.sendTitle(title, subtitle, 10, 20, 10);
            running = true;
            Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
                running = false;
            }, 40L);
        }
    }

}
