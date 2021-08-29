package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import lombok.Getter;

@Getter
public class ItemGunAkm extends ItemGunBase {

    public ItemGunAkm(Integer meta, int count) {
        super(getGunData(ItemGunAkm.class).getGunId(), meta, count, getGunData(ItemGunAkm.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunAkm(Integer meta) {
        super(getGunData(ItemGunAkm.class).getGunId(), meta, 1, getGunData(ItemGunAkm.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunAkm() {
        super(getGunData(ItemGunAkm.class).getGunId(), 0, 1, getGunData(ItemGunAkm.class).getGunName());
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
        return new ItemMagAkm();
    }

    public static class ItemMagAkm extends ItemMagBase {
        public ItemMagAkm(Integer meta, int count) {
            super(getGunData(ItemGunAkm.class).getMagId(), meta, count, getGunData(ItemGunAkm.class).getMagName());
            this.setCustomName(getGunData(ItemGunAkm.class).getMagName());
        }

        public ItemMagAkm(Integer meta) {
            super(getGunData(ItemGunAkm.class).getMagId(), meta, 1, getGunData(ItemGunAkm.class).getMagName());
            this.setCustomName(getGunData(ItemGunAkm.class).getMagName());
        }

        public ItemMagAkm() {
            super(getGunData(ItemGunAkm.class).getMagId(), 0, 1, getGunData(ItemGunAkm.class).getMagName());
            this.setCustomName(getGunData(ItemGunAkm.class).getMagName());
        }
    }
}
