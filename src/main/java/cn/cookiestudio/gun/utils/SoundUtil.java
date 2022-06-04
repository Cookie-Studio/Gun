package cn.cookiestudio.gun.utils;

import cn.nukkit.Server;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlaySoundPacket;

public class SoundUtil {
    public static void playSound(Vector3 pos, String sound, float volume, float pitch) {
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
