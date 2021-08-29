package cn.cookiestudio.gun.guns;

import cn.cookiestudio.gun.GunPlugin;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityGun extends EntityHuman {

    private GunData gunData;
    private ItemGunBase itemGun;

    public EntityGun(FullChunk chunk, CompoundTag nbt, GunData gunData, ItemGunBase itemGun) {
        super(chunk, nbt);
        this.gunData = gunData;
        this.itemGun = itemGun;
        this.setNameTag(gunData.getGunName());
        this.setSkin(GunPlugin.getInstance().getCrateSkin());
    }

    public static CompoundTag getDefaultNBT(Vector3 pos){
        return Entity.getDefaultNBT(pos).putCompound("Skin",new CompoundTag());
    }


}
