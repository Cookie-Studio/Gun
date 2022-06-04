package cn.cookiestudio.gun.guns;

import cn.cookiestudio.gun.GunPlugin;
import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityMag extends EntityHuman {
    private ItemMagBase itemMag;

    public EntityMag(FullChunk chunk, CompoundTag nbt, ItemMagBase itemMag) {
        super(chunk, nbt);
        this.itemMag = itemMag;
        this.setNameTag(itemMag.getName());
        this.setSkin(GunPlugin.getInstance().getAmmoBoxSkin());
        this.setMaxHealth(20);
        this.setHealth(20);
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        int empty = player.getInventory().firstEmpty(null);
        if (empty != -1){
            player.getInventory().setItem(empty,this.getItemMag());
            this.close();
            return true;
        }
        return false;
    }

    public static CompoundTag getDefaultNBT(Vector3 pos){
        return Entity.getDefaultNBT(pos).putCompound("Skin",new CompoundTag());
    }
}
