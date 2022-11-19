package cn.cookiestudio.gun;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerItemHeldEvent;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Getter
public class CoolDownTimer {

    private final Map<Player, CoolDown> coolDownMap = new ConcurrentHashMap<>();

    {
        Server.getInstance().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerInterruptCoolDown(PlayerItemHeldEvent event) {
                if (coolDownMap.containsKey(event.getPlayer())) {
                    if (interrupt(event.getPlayer()) == Operator.CANCELLED_EVENT)
                        event.setCancelled();
                }
            }
        }, GunPlugin.getInstance());
        Server.getInstance().getScheduler().scheduleRepeatingTask(GunPlugin.getInstance(), () -> {
            coolDownMap.forEach((p, c) -> {
                c.coolDownTick--;
                if (c.coolDownTick == 0) {
                    finish(p);
                }
            });
        }, 1);
    }

    public Operator interrupt(Player player) {
        Operator operator = coolDownMap.get(player).onInterrupt.get();
        switch (operator) {
            case INTERRUPT:
                coolDownMap.remove(player);
                break;
            case NO_ACTION:
                break;
            case CANCELLED_EVENT:
                break;
        }
        return operator;
    }

    public boolean isCooling(Player player) {
        return coolDownMap.containsKey(player);
    }

    public void finish(Player player) {
        coolDownMap.get(player).onFinish.run();
        coolDownMap.remove(player);
    }

    public void addCoolDown(Player player, int coolDownTick, Runnable onFinish, Supplier<Operator> onInterrupt, Type type) {
        coolDownMap.put(player, new CoolDown(coolDownTick, onFinish, onInterrupt, type));
    }

    public enum Operator {
        INTERRUPT,
        NO_ACTION,
        CANCELLED_EVENT
    }

    public enum Type {
        RELOAD,
        FIRECOOLDOWN
    }

    @Getter
    public class CoolDown {
        public int coolDownTick;
        public Runnable onFinish;
        public Supplier<Operator> onInterrupt;
        public Type type;

        public CoolDown(int coolDownTick, Runnable onFinish, Supplier<Operator> onInterrupt, Type type) {
            this.coolDownTick = coolDownTick;
            this.onFinish = onFinish;
            this.onInterrupt = onInterrupt;
            this.type = type;
        }
    }
}
