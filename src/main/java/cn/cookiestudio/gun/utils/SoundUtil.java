package cn.cookiestudio.gun.utils;

import cn.nukkit.Server;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlaySoundPacket;
import com.google.common.base.Preconditions;

public class SoundUtil {
    public static void playSound(Vector3 pos, String sound, float volume, float pitch) {
        Preconditions.checkArgument(volume >= 0.0F && volume <= 1.0F, "Sound volume must be between 0 and 1");
        Preconditions.checkArgument(pitch >= 0.0F, "Sound pitch must be higher than 0");
        PlaySoundPacket packet = new PlaySoundPacket();
        packet.name = sound;
        packet.volume = volume;
        packet.pitch = pitch;
        packet.x = pos.getFloorX();
        packet.y = pos.getFloorY();
        packet.z = pos.getFloorZ();
        Server.broadcastPacket(Server.getInstance().getOnlinePlayers().values(), packet);
    }
}
