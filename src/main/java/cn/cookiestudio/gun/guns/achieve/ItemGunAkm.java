package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import lombok.Getter;

@Getter
public class ItemGunAkm extends ItemGunBase {

    public ItemGunAkm() {
        super(getGunData(ItemGunAkm.class).getGunName());
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

        public ItemMagAkm() {
            super(getGunData(ItemGunAkm.class).getMagName());
            this.setCustomName(getGunData(ItemGunAkm.class).getMagName());
        }
    }
}
