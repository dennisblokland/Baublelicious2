package com.denbukki.curio.network;


import com.denbukki.curio.tiles.TileEntityInfusionTable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateInfusionTable implements IMessage {


    public PacketUpdateInfusionTable(BlockPos pos, ItemStack stack, ItemStack stack2, int level, int infuseTime) {
        this.pos = pos;
        this.stack = stack;
        this.stack2 = stack2;
        this.level = level;
        this.infuseTime = infuseTime;
    }
    public PacketUpdateInfusionTable(TileEntityInfusionTable te) {
        this(te.getPos(), te.getInventory().getStackInSlot(0), te.getInventory().getStackInSlot(1), te.getLevel() ,te.getInfuseTime());
    }

    private BlockPos pos;
    private ItemStack stack;
    private ItemStack stack2;
    private int level;
    private int infuseTime;

    public PacketUpdateInfusionTable(){

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        ByteBufUtils.writeItemStack(buf, stack2);
        buf.writeInt(level);
        buf.writeInt(infuseTime);

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        stack2 = ByteBufUtils.readItemStack(buf);
        level = buf.readInt();
        infuseTime = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketUpdateInfusionTable, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateInfusionTable message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityInfusionTable te = (TileEntityInfusionTable)Minecraft.getMinecraft().world.getTileEntity(message.pos);
                te.getInventory().setStackInSlot(0, message.stack);
                te.getInventory().setStackInSlot(1, message.stack2);
                te.setLevel(message.level);
                te.setLevel(message.infuseTime);
            });
            return null;
        }
    }
}