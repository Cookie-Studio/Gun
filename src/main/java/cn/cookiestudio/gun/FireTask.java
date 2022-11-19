package cn.cookiestudio.gun;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerItemHeldEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class FireTask extends PluginTask {

    private final Map<Player, Boolean> firing = new HashMap<>();

    public FireTask(Plugin owner) {
        super(owner);
        Server.getInstance().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                firing.remove(event.getPlayer());
            }

            @EventHandler
            public void onPlayerInteractFiring(PlayerItemHeldEvent event) {
                if (firing.containsKey(event.getPlayer()) && !(event.getItem() instanceof ItemGunBase))
                    firing.put(event.getPlayer(), false);
            }
        }, GunPlugin.getInstance());
        Server.getInstance().getScheduler().scheduleRepeatingTask(this, 1);
    }

    @Override
    public void onRun(int i) {
        firing.keySet().forEach(player -> {
            if (!firing.get(player))
                return;
            if (!(player.getInventory().getItemInHand() instanceof ItemGunBase itemGun)) {
                this.firing.put(player, false);
            } else {
                ItemGunBase.GunInteractAction action = itemGun.interact(player);
                if (action == ItemGunBase.GunInteractAction.RELOAD) {
                    this.firing.put(player, false);
                }
            }
        });
    }

    public void changeState(Player player) {
        if (!firing.containsKey(player)) {
            firing.put(player, true);
        }
        if (firing.get(player)) {
            firing.put(player, false);
        } else {
            firing.put(player, true);
        }
    }

    public boolean firing(Player player) {
        if (!firing.containsKey(player)) {
            firing.put(player, false);
        }
        return firing.get(player);
    }
}
