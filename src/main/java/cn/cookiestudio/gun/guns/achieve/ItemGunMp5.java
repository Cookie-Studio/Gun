package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import lombok.Getter;

@Getter
public class ItemGunMp5 extends ItemGunBase {
    public ItemGunMp5() {
        super(getGunData(ItemGunMp5.class).getGunName());
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
    public int getSkinId() {
        return 18;
    }

    @Override
    public float getDropItemScale() {
        return 0.08f;
    }

    @Override
    public ItemMagBase getItemMagObject() {
        return new ItemMagMp5();
    }

    public static class ItemMagMp5 extends ItemMagBase {

        public ItemMagMp5() {
            super(getGunData(ItemGunMp5.class).getMagName());
            this.setCustomName(getGunData(ItemGunMp5.class).getMagName());
        }

        @Override
        public int getSkinId() {
            return 19;
        }

        @Override
        public float getDropItemScale() {
            return 0.08f;
        }
    }
}
