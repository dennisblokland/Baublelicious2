package com.denbukki.baublelicious2.items;

import baubles.api.BaubleType;
import net.minecraft.item.ItemStack;

public class ItemBelt extends BaublesItemBase {
    public ItemBelt() {
        setUnlocalizedName("ItemBelt");
        setRegistryName("ItemBelt");
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.BELT;
    }
}
