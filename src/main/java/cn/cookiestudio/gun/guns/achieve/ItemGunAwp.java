package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import cn.nukkit.item.ItemTool;
import lombok.Getter;

@Getter
public class ItemGunAwp extends ItemGunBase {

    public ItemGunAwp(Integer meta, int count) {
        super(getGunData(ItemGunAwp.class).getGunId(), meta, count, getGunData(ItemGunAwp.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunAwp(Integer meta) {
        super(getGunData(ItemGunAwp.class).getGunId(), meta, 1, getGunData(ItemGunAwp.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunAwp() {
        super(getGunData(ItemGunAwp.class).getGunId(), 0, 1, getGunData(ItemGunAwp.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public void doInit() {
    }

    @Override
    public ItemMagBase getItemMagObject() {
        return new ItemMagAwp();
    }

    public static class ItemMagAwp extends ItemMagBase {
        public ItemMagAwp(Integer meta, int count) {
            super(getGunData(ItemGunAwp.class).getMagId(), meta, count, getGunData(ItemGunAwp.class).getMagName());
            this.setCustomName(getGunData(ItemGunAwp.class).getMagName());
        }

        public ItemMagAwp(Integer meta) {
            super(getGunData(ItemGunAwp.class).getMagId(), meta, 1, getGunData(ItemGunAwp.class).getMagName());
            this.setCustomName(getGunData(ItemGunAwp.class).getMagName());
        }

        public ItemMagAwp() {
            super(getGunData(ItemGunAwp.class).getMagId(), 0, 1, getGunData(ItemGunAwp.class).getMagName());
            this.setCustomName(getGunData(ItemGunAwp.class).getMagName());
        }
    }
}
