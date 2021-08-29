package cn.cookiestudio.gun.network;

import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraShakePacket extends DataPacket {
    private float intensity;
    private float duration;
    private CameraShakeType shakeType;
    private CameraShakeAction shakeAction;

    public static final byte NETWORK_ID = ProtocolInfo.CAMERA_SHAKE_PACKET;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.intensity = this.getLFloat();
        this.duration = this.getLFloat();
        this.shakeType = CameraShakeType.values()[this.getByte()];
        this.shakeAction = CameraShakeAction.values()[this.getByte()];
    }

    @Override
    public void encode() {
        this.reset();
        this.putLFloat(this.intensity);
        this.putLFloat(this.duration);
        this.putByte((byte) this.shakeType.ordinal());
        this.putByte((byte) this.shakeAction.ordinal());
    }

    public enum CameraShakeAction {
        ADD,
        STOP
    }

    public enum CameraShakeType {
        POSITIONAL,
        ROTATIONAL
    }
}
