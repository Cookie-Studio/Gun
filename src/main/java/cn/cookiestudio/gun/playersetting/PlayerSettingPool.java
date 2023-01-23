package cn.cookiestudio.gun.playersetting;

import cn.cookiestudio.gun.GunPlugin;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.Config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PlayerSettingPool {

    private Config config;
    private final Map<String, PlayerSettingMap> settings = new HashMap<>();

    public PlayerSettingPool() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Server.getInstance().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event) {
                String name = event.getPlayer().getName();
                if (!settings.containsKey(name)) {
                    cache(name);
                }
            }
        }, GunPlugin.getInstance());
        for (Player player : Server.getInstance().getOnlinePlayers().values()) {//reload fixed
            cache(player.getName());
        }
    }

    public void init() throws Exception {
        Path p = Paths.get(GunPlugin.getInstance().getDataFolder().toString(), "playerSettings.json");
        if (!Files.exists(p))
            Files.createFile(p);
        this.config = new Config(p.toFile(), Config.JSON);
    }

    public PlayerSettingMap getPlayerSetting(String name) {
        if (!settings.containsKey(name)) {
            return cache(name);
        }
        return settings.get(name);
    }

    public PlayerSettingMap cache(String name) {
        if (!existInFile(name)) {
            PlayerSettingMap entry = PlayerSettingMap
                    .builder()
                    .fireMode(PlayerSettingMap.FireMode.MANUAL)
                    .openTrajectoryParticle(true)
                    .openMuzzleParticle(true)
                    .build();
            settings.put(name, entry);
            return entry;
        }
        Map playerSetting = (Map) config.get(name);
        PlayerSettingMap e = PlayerSettingMap
                .builder()
                .fireMode(PlayerSettingMap.FireMode.values()[((Double) playerSetting.get("fireMode")).intValue()])
                .openTrajectoryParticle((Boolean) playerSetting.get("openTrajectoryParticle"))
                .openMuzzleParticle((Boolean) playerSetting.get("openMuzzleParticle"))
                .build();
        settings.put(name, e);
        return e;
    }

    public void write(String name, PlayerSettingMap entry) {
        config.set(name, entry.getMap());
        config.save();
    }

    public void writeAll() {
        for (Map.Entry<String, PlayerSettingMap> e : settings.entrySet())
            write(e.getKey(), e.getValue());
    }

    public boolean existInFile(String name) {
        return config.exists(name);
    }

    public void close() {
        writeAll();
    }
}
