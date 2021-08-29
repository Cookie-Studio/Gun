package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import lombok.Getter;

@Getter
public class ItemGunTaurus extends ItemGunBase {


    public ItemGunTaurus(Integer meta, int count) {
        super(getGunData(ItemGunTaurus.class).getGunId(), meta, count, getGunData(ItemGunTaurus.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunTaurus(Integer meta) {
        super(getGunData(ItemGunTaurus.class).getGunId(), meta, 1, getGunData(ItemGunTaurus.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunTaurus() {
        super(getGunData(ItemGunTaurus.class).getGunId(), 0, 1, getGunData(ItemGunTaurus.class).getGunName());
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
        return new ItemMagTaurus();
    }

    public static class ItemMagTaurus extends ItemMagBase {
        public ItemMagTaurus(Integer meta, int count) {
            super(getGunData(ItemGunTaurus.class).getMagId(), meta, count, getGunData(ItemGunTaurus.class).getMagName());
            this.setCustomName(getGunData(ItemGunTaurus.class).getMagName());
        }

        public ItemMagTaurus(Integer meta) {
            super(getGunData(ItemGunTaurus.class).getMagId(), meta, 1, getGunData(ItemGunTaurus.class).getMagName());
            this.setCustomName(getGunData(ItemGunTaurus.class).getMagName());
        }

        public ItemMagTaurus() {
            super(getGunData(ItemGunTaurus.class).getMagId(), 0, 1, getGunData(ItemGunTaurus.class).getMagName());
            this.setCustomName(getGunData(ItemGunTaurus.class).getMagName());
        }
    }
}
