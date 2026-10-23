package id.server.playerheaddrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerHeadDrop extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("PlayerHeadDrop berhasil diaktifkan!");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        // Kepala hanya jatuh jika dibunuh pemain lain.
        if (killer == null || killer.getUniqueId().equals(victim.getUniqueId())) {
            return;
        }

        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta != null) {
            meta.setOwningPlayer(victim);
            meta.setDisplayName("§eKepala " + victim.getName());
            head.setItemMeta(meta);
        }

        // Jatuhkan kepala di lokasi kematian.
        victim.getWorld().dropItemNaturally(victim.getLocation(), head);

        killer.sendMessage("§aKamu mendapatkan kepala dari §e"
                + victim.getName() + "§a!");
    }
}
