package cn.cookiestudio.gun.network;

import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;

import java.util.ArrayList;
import java.util.List;

public class AnimateEntityPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.ANIMATE_ENTITY_PACKET;

    private String animation;
    private String nextState;
    private String stopExpression;
    private String controller;
    private float blendOutTime;
    private List<Long> entityRuntimeIds = new ArrayList<>();

    @Override
    public void decode() {
        this.animation = this.getString();
        this.nextState = this.getString();
        this.stopExpression = this.getString();
        this.controller = this.getString();
        this.blendOutTime = this.getLFloat();
        for (int i = 0, len = (int) this.getUnsignedVarInt(); i < len; i++) {
            this.entityRuntimeIds.add(this.getEntityRuntimeId());
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.putString(this.animation);
        this.putString(this.nextState);
        this.putString(this.stopExpression);
        this.putString(this.controller);
        this.putLFloat(this.blendOutTime);
        this.putUnsignedVarInt(this.entityRuntimeIds.size());
        for (long entityRuntimeId : this.entityRuntimeIds) {
            this.putEntityRuntimeId(entityRuntimeId);
        }
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public String getAnimation() {
        return this.animation;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }

    public String getNextState() {
        return this.nextState;
    }

    public void setStopExpression(String stopExpression) {
        this.stopExpression = stopExpression;
    }

    public String getStopExpression() {
        return this.stopExpression;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getController() {
        return this.controller;
    }

    public void setBlendOutTime(float blendOutTime) {
        this.blendOutTime = blendOutTime;
    }

    public float getBlendOutTime() {
        return this.blendOutTime;
    }

    public void setEntityRuntimeIds(List<Long> entityRuntimeIds) {
        this.entityRuntimeIds = entityRuntimeIds;
    }

    public List<Long> getEntityRuntimeIds() {
        return this.entityRuntimeIds;
    }
}
