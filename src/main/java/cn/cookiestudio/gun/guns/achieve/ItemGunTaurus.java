package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import lombok.Getter;

@Getter
public class ItemGunTaurus extends ItemGunBase {

    public ItemGunTaurus() {
        super(getGunData(ItemGunTaurus.class).getGunName());
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

        public ItemMagTaurus() {
            super(getGunData(ItemGunTaurus.class).getMagName());
            this.setCustomName(getGunData(ItemGunTaurus.class).getMagName());
        }
    }
}
