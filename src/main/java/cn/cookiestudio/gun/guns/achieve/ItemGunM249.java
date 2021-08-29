package cn.cookiestudio.gun.guns.achieve;

import cn.cookiestudio.gun.guns.ItemGunBase;
import cn.cookiestudio.gun.guns.ItemMagBase;
import lombok.Getter;

@Getter
public class ItemGunM249 extends ItemGunBase {


    public ItemGunM249(Integer meta, int count) {
        super(getGunData(ItemGunM249.class).getGunId(), meta, count, getGunData(ItemGunM249.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunM249(Integer meta) {
        super(getGunData(ItemGunM249.class).getGunId(), meta, 1, getGunData(ItemGunM249.class).getGunName());
        gunData = getGunData(this.getClass());
        this.setCustomName(gunData.getGunName());
        if (!this.getNamedTag().contains("ammoCount")){
            this.setAmmoCount(this.getGunData().getMagSize());
        }else{
            this.setAmmoCount(this.getAmmoCount());
        }
    }

    public ItemGunM249() {
        super(getGunData(ItemGunM249.class).getGunId(), 0, 1, getGunData(ItemGunM249.class).getGunName());
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
        return new ItemMagM249();
    }

    public static class ItemMagM249 extends ItemMagBase {
        public ItemMagM249(Integer meta, int count) {
            super(getGunData(ItemGunM249.class).getMagId(), meta, count, getGunData(ItemGunM249.class).getMagName());
            this.setCustomName(getGunData(ItemGunM249.class).getMagName());
        }

        public ItemMagM249(Integer meta) {
            super(getGunData(ItemGunM249.class).getMagId(), meta, 1, getGunData(ItemGunM249.class).getMagName());
            this.setCustomName(getGunData(ItemGunM249.class).getMagName());
        }

        public ItemMagM249() {
            super(getGunData(ItemGunM249.class).getMagId(), 0, 1, getGunData(ItemGunM249.class).getMagName());
            this.setCustomName(getGunData(ItemGunM249.class).getMagName());
        }
    }
}
