package cn.cookiestudio.gun.playersetting;

import cn.cookiestudio.gun.GunPlugin;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.Config;
import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerSettingPool {

    private Config config;
    private Map<String,PlayerSettingMap> settings = new HashMap<>();

    public PlayerSettingPool(){
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Server.getInstance().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event){
                String name = event.getPlayer().getName();
                if (!settings.containsKey(name)){
                    cache(name);
                }
            }
        }, GunPlugin.getInstance());
        for (Player player : Server.getInstance().getOnlinePlayers().values()){//reload fixed
            cache(player.getName());
        }
    }

    public void init() throws Exception {
        Path p = Paths.get(GunPlugin.getInstance().getDataFolder().toString(),"playerSettings.json");
        if (!Files.exists(p))
            Files.createFile(p);
        this.config = new Config(p.toFile(),Config.JSON);
    }

    public PlayerSettingMap cache(String name){
        String key = name;
        if (!existInFile(name)){
            PlayerSettingMap entry = PlayerSettingMap
                    .builder()
                    .fireMode(PlayerSettingMap.FireMode.AUTO)
                    .openTrajectoryParticle(false)
                    .build();
            settings.put(name,entry);
            return entry;
        }
        PlayerSettingMap e = PlayerSettingMap
                .builder()
                .fireMode(PlayerSettingMap.FireMode.values()[config.getInt(key + ".fireMode")])
                .openTrajectoryParticle(config.getBoolean(key + ".openTrajectoryParticle"))
                .build();
        settings.put(name,e);
        return e;
    }

    public void write(String name,PlayerSettingMap entry){
        config.set(name,entry.getMap());
        config.save();
    }

    public void writeAll(){
        for (Map.Entry<String, PlayerSettingMap> e : getSettings().entrySet())
            write(e.getKey(),e.getValue());
    }

    public boolean existInFile(String name){
        return config.exists(name);
    }

    public void close(){
        writeAll();
    }
}
