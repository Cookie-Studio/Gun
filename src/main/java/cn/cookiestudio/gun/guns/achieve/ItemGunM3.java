package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import lombok.Getter;

@Getter
public class ItemGunM3 extends ItemGunBase {


    public ItemGunM3(Integer meta, int count) {
        super(getGunData(ItemGunM3.class).getGunId(), meta, count, getGunData(ItemGunM3.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunM3(Integer meta) {
        super(getGunData(ItemGunM3.class).getGunId(), meta, 1, getGunData(ItemGunM3.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunM3() {
        super(getGunData(ItemGunM3.class).getGunId(), 0, 1, getGunData(ItemGunM3.class).getGunName());
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
        return new ItemMagM3();
    }

    public static class ItemMagM3 extends ItemMagBase {
        public ItemMagM3(Integer meta, int count) {
            super(getGunData(ItemGunM3.class).getMagId(), meta, count, getGunData(ItemGunM3.class).getMagName());
            this.setCustomName(getGunData(ItemGunM3.class).getMagName());
        }

        public ItemMagM3(Integer meta) {
            super(getGunData(ItemGunM3.class).getMagId(), meta, 1, getGunData(ItemGunM3.class).getMagName());
            this.setCustomName(getGunData(ItemGunM3.class).getMagName());
        }

        public ItemMagM3() {
            super(getGunData(ItemGunM3.class).getMagId(), 0, 1, getGunData(ItemGunM3.class).getMagName());
            this.setCustomName(getGunData(ItemGunM3.class).getMagName());
        }

        @Override
        public int getMaxStackSize() {
            return 64;
        }
    }
}
