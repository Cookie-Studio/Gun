package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import lombok.Getter;

@Getter
public class ItemGunMp5 extends ItemGunBase {


    public ItemGunMp5(Integer meta, int count) {
        super(getGunData(ItemGunMp5.class).getGunId(), meta, count, getGunData(ItemGunMp5.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunMp5(Integer meta) {
        super(getGunData(ItemGunMp5.class).getGunId(), meta, 1, getGunData(ItemGunMp5.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunMp5() {
        super(getGunData(ItemGunMp5.class).getGunId(), 0, 1, getGunData(ItemGunMp5.class).getGunName());
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
        return new ItemMagMp5();
    }

    public static class ItemMagMp5 extends ItemMagBase {
        public ItemMagMp5(Integer meta, int count) {
            super(getGunData(ItemGunMp5.class).getMagId(), meta, count, getGunData(ItemGunMp5.class).getMagName());
            this.setCustomName(getGunData(ItemGunMp5.class).getMagName());
        }

        public ItemMagMp5(Integer meta) {
            super(getGunData(ItemGunMp5.class).getMagId(), meta, 1, getGunData(ItemGunMp5.class).getMagName());
            this.setCustomName(getGunData(ItemGunMp5.class).getMagName());
        }

        public ItemMagMp5() {
            super(getGunData(ItemGunMp5.class).getMagId(), 0, 1, getGunData(ItemGunMp5.class).getMagName());
            this.setCustomName(getGunData(ItemGunMp5.class).getMagName());
        }
    }
}
