package org.turbojax.bars;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BarsPlugin extends JavaPlugin implements Listener {
  public void onEnable() {
    getServer().getPluginManager().registerEvents((Listener) this, this);
  }

  private final Map<UUID,UUID> displays = new HashMap<>();
  private final Map<UUID,UUID> pigs = new HashMap<>();

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    player.getWorld().spawn(player.getLocation().add(2,2,2), TextDisplay.class, entity -> {
      entity.text(Component.text("LOOK AT ME"));
      entity.setBillboard(Display.Billboard.VERTICAL);
      entity.setVisibleByDefault(false);
      player.showEntity(this, entity);
      displays.put(player.getUniqueId(), entity.getUniqueId());
    });
    player.getWorld().spawn(player.getLocation().add(2,2,2), Pig.class, entity -> {
      entity.setAI(false);
      entity.setWaypointStyle(Key.key("power", "bar"));
      entity.setWaypointColor(org.bukkit.Color.WHITE);
//      entity.setVisibleByDefault(false);
//      player.showEntity(this, entity);
      pigs.put(player.getUniqueId(), entity.getUniqueId());
    });
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    Entity entity = Bukkit.getEntity(displays.remove(player.getUniqueId()));
    entity.remove();
    entity = Bukkit.getEntity(pigs.remove(player.getUniqueId()));
    entity.remove();
  }
}
