package rama.id;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class InventoryDrops extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    public void registerEvents(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&3Inventory Drops&7] &aRegistering events..."));
        Bukkit.getPluginManager().registerEvents(new MineListener(this), this);
    }
}
