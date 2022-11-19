package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import lombok.Getter;

@Getter
public class ItemGunAkm extends ItemGunBase {

    public ItemGunAkm() {
        super(getGunData(ItemGunAkm.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")) {
            this.setAmmoCount(this.getGunData().getMagSize());
        } else {
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public void doInit() {
    }

    @Override
    public int getSkinId() {
        return 12;
    }

    @Override
    public float getDropItemScale() {
        return 0.05f;
    }

    @Override
    public ItemMagBase getItemMagObject() {
        return new ItemMagAkm();
    }

    public static class ItemMagAkm extends ItemMagBase {

        public ItemMagAkm() {
            super(getGunData(ItemGunAkm.class).getMagName());
            this.setCustomName(getGunData(ItemGunAkm.class).getMagName());
        }

        @Override
        public int getSkinId() {
            return 13;
        }

        @Override
        public float getDropItemScale() {
            return 0.05f;
        }
    }
}
