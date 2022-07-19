package cn.cookiestudio.gun.guns;

import cn.nukkit.item.customitem.ItemCustom;

public abstract class ItemMagBase extends ItemCustom {


    public ItemMagBase(String name){
        super("gun:" + name,name,name);
    }

    public abstract int getSkinId();

    public abstract float getDropItemScale();

    @Override
    public int getMaxStackSize() {
        return 16;
    }

}
