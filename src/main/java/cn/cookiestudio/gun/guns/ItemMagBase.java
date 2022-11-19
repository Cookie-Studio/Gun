package cn.cookiestudio.gun.guns;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public abstract class ItemMagBase extends ItemCustom {
    public ItemMagBase(String name) {
        super("gun:" + name, name, name);
    }

    public abstract int getSkinId();

    public abstract float getDropItemScale();

    @Override
    public int getMaxStackSize() {
        return 16;
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .simpleBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .creativeGroup("itemGroup.name.ammo")
                .allowOffHand(true)
                .build();
    }
}
