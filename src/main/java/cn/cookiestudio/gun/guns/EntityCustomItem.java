package cn.cookiestudio.gun.guns;

import cn.nukkit.entity.custom.CustomEntity;
import cn.nukkit.entity.custom.CustomEntityDefinition;
import cn.nukkit.entity.data.IntEntityData;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.types.EntityLink;
import lombok.Getter;
import lombok.Setter;

import static cn.nukkit.network.protocol.SetEntityLinkPacket.TYPE_PASSENGER;

@Getter
@Setter
public class EntityCustomItem extends EntityItem implements CustomEntity {

    public final static int NETWORK_ID = 0;
    public final static CustomEntityDefinition DEFINITION = CustomEntityDefinition.builder()
            .identifier("pixelpoly:firearm_item")
            .spawnEgg(false)
            .summonable(true)
            .build();

    public EntityCustomItem(FullChunk chunk, CompoundTag nbt, int skinId, float scale) {
        super(chunk, nbt.putInt("skinId", skinId));
        this.setScale(scale);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.setDataProperty(new IntEntityData(DATA_SKIN_ID, namedTag.getInt("skinId")));
    }

    @Override
    public CustomEntityDefinition getDefinition() {
        return DEFINITION;
    }

    @Override
    public DataPacket createAddEntityPacket() {
        AddEntityPacket addEntity = new AddEntityPacket();
        addEntity.type = this.getNetworkId();
        addEntity.entityUniqueId = this.getId();
        addEntity.id = this.getDefinition().getStringId();
        addEntity.entityRuntimeId = this.getId();
        addEntity.yaw = (float) this.yaw;
        addEntity.headYaw = (float) this.yaw;
        addEntity.pitch = (float) this.pitch;
        addEntity.x = (float) this.x;
        addEntity.y = (float) this.y + this.getBaseOffset();
        addEntity.z = (float) this.z;
        addEntity.speedX = (float) this.motionX;
        addEntity.speedY = (float) this.motionY;
        addEntity.speedZ = (float) this.motionZ;
        addEntity.metadata = this.dataProperties;

        addEntity.links = new EntityLink[this.passengers.size()];
        for (int i = 0; i < addEntity.links.length; i++) {
            addEntity.links[i] = new EntityLink(this.getId(), this.passengers.get(i).getId(), i == 0 ? EntityLink.TYPE_RIDER : TYPE_PASSENGER, false, false);
        }

        return addEntity;
    }
}
