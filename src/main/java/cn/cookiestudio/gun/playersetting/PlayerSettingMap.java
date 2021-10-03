package cn.cookiestudio.gun.playersetting;

import cn.nukkit.level.ParticleEffect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class PlayerSettingMap {

    private FireMode fireMode = FireMode.AUTO;
    private boolean openTrajectoryParticle = false;
    private boolean openMuzzleParticle = true;

    public Map<String,Object> getMap(){
        Map<String,Object> map = new HashMap<>();

        map.put("fireMode",this.fireMode.ordinal());
        map.put("openTrajectoryParticle",this.openTrajectoryParticle);
        map.put("openMuzzleParticle",this.openMuzzleParticle);

        return map;
    }

    public enum FireMode{
        AUTO,
        MANUAL
    }
}
