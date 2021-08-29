package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import lombok.Getter;

@Getter
public class ItemGunP90 extends ItemGunBase {


    public ItemGunP90(Integer meta, int count) {
        super(getGunData(ItemGunP90.class).getGunId(), meta, count, getGunData(ItemGunP90.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunP90(Integer meta) {
        super(getGunData(ItemGunP90.class).getGunId(), meta, 1, getGunData(ItemGunP90.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunP90() {
        super(getGunData(ItemGunP90.class).getGunId(), 0, 1, getGunData(ItemGunP90.class).getGunName());
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
        return new ItemMagP90();
    }

    public static class ItemMagP90 extends ItemMagBase {
        public ItemMagP90(Integer meta, int count) {
            super(getGunData(ItemGunP90.class).getMagId(), meta, count, getGunData(ItemGunP90.class).getMagName());
            this.setCustomName(getGunData(ItemGunP90.class).getMagName());
        }

        public ItemMagP90(Integer meta) {
            super(getGunData(ItemGunP90.class).getMagId(), meta, 1, getGunData(ItemGunP90.class).getMagName());
            this.setCustomName(getGunData(ItemGunP90.class).getMagName());
        }

        public ItemMagP90() {
            super(getGunData(ItemGunP90.class).getMagId(), 0, 1, getGunData(ItemGunP90.class).getMagName());
            this.setCustomName(getGunData(ItemGunP90.class).getMagName());
        }
    }
}
