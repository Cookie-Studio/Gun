package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import lombok.Getter;

@Getter
public class ItemGunBarrett extends ItemGunBase {


    public ItemGunBarrett(Integer meta, int count) {
        super(getGunData(ItemGunBarrett.class).getGunId(), meta, count, getGunData(ItemGunBarrett.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunBarrett(Integer meta) {
        super(getGunData(ItemGunBarrett.class).getGunId(), meta, 1, getGunData(ItemGunBarrett.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunBarrett() {
        super(getGunData(ItemGunBarrett.class).getGunId(), 0, 1, getGunData(ItemGunBarrett.class).getGunName());
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
        return new ItemMagBarrett();
    }

    public static class ItemMagBarrett extends ItemMagBase {
        public ItemMagBarrett(Integer meta, int count) {
            super(getGunData(ItemGunBarrett.class).getMagId(), meta, count, getGunData(ItemGunBarrett.class).getMagName());
            this.setCustomName(getGunData(ItemGunBarrett.class).getMagName());
        }

        public ItemMagBarrett(Integer meta) {
            super(getGunData(ItemGunBarrett.class).getMagId(), meta, 1, getGunData(ItemGunBarrett.class).getMagName());
            this.setCustomName(getGunData(ItemGunBarrett.class).getMagName());
        }

        public ItemMagBarrett() {
            super(getGunData(ItemGunBarrett.class).getMagId(), 0, 1, getGunData(ItemGunBarrett.class).getMagName());
            this.setCustomName(getGunData(ItemGunBarrett.class).getMagName());
        }
    }
}
