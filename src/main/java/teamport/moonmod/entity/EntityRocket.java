package teamport.moonmod.entity;

import teamport.moonmod.MoonMod;
import teamport.moonmod.block.MMBlocks;
import teamport.moonmod.item.MMItems;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.piston.BlockPistonBase;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import net.minecraft.core.world.IVehicle;
import com.mojang.nbt.CompoundTag;

import java.util.ArrayList;

public class EntityRocket extends Entity implements IVehicle {
    // 0 - On ground
    // 1 - Count down
    // 2 - Launching
    // 3 - Landing
    public int state = 0;

    // State 1
    private int countdown = 0;
    private float xb = 0, zb = 0;
    // State 2 and 3
    private float yv = 0;

    // Shape data
    public int sx = 0, sy = 0, sz = 0;
    public float rx = 0, ry = 0, rz = 0;
    public short[] rocketIds = null;
    public byte[] rocketMeta = null;

	public EntityRocket(World world, short[] rocketIds, byte[] rocketMeta, Vec3d size, Vec3d riderPos) {
		super(world);
		this.rocketIds = rocketIds;
		this.rocketMeta = rocketMeta;
		sx = (int) size.xCoord;
		sy = (int) size.yCoord;
		sz = (int) size.zCoord;
		rx = (int) riderPos.xCoord;
		ry = (int) riderPos.yCoord;
		rz = (int) riderPos.zCoord;
		setSize(((float) sx + sz) / 2 - 1, (float) sy - 1);
	}

	public EntityRocket(World world) {
		super(world);
	}

    private static boolean isRocketWorthy(int id, World world, int x, int y, int z) {
        if (id == 0) return false;
        if (id == Block.obsidian.id) return false;
        if (id == Block.pistonBase.id || id == Block.pistonBaseSticky.id) {
            return !BlockPistonBase.isPowered(world.getBlockMetadata(x, y, z));
        }
        if (Block.blocksList[id].getHardness() == -1.0F) return false;
        if (Block.blocksList[id].getImmovable()) return false;
        if (
            Block.blocksList[id].getPistonPushReaction() == 2 ||
            Block.blocksList[id].getPistonPushReaction() == 1
        ) return false;
        // TODO
        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
        return (tileentity == null);
    }

    public static EntityRocket buildRocket(World world, int x, int y, int z) {
        // Get bottom/top (skip the first air chunk)
        int sy = y, ey = y;

        while (world.getBlock(x, sy - 1, z) == null && sy >= 0) sy--;
        sy--;
        if (sy < 0) sy = y;
        while (world.getBlock(x, sy, z) != null) sy--;

        while (world.getBlock(x, ey + 1, z) == null && ey <= 256) ey++;
        ey++;
        if (ey > 256) ey = y;
        while (world.getBlock(x, ey, z) != null) ey++;

        if (sy < 0 || ey > 256) return null;

        // Get sides
        int sx = x, ex = x;
        while (world.getBlock(sx, y, z) != null && ( x - sx) <= 32) sx--;
        while (world.getBlock(ex, y, z) != null && (ex -  x) <= 32) ex++;
        if (x - sx > 32 || ex - x > 32) return null;

        int sz = z, ez = z;
        while (world.getBlock(x, y, sz) != null && ( z - sz) <= 32) sz--;
        while (world.getBlock(x, y, ez) != null && (ez -  z) <= 32) ez++;
        if (z - sz > 32 || ez - z > 32) return null;

        // Check "landing gear"
        boolean landingGear = false;
        for (int nx = sx; nx <= ex; nx++) {
            for (int nz = sz; nz <= ez; nz++) {
                if (isRocketWorthy(world.getBlockId(nx, sy, nz), world, nx, sy, nz)) {
                    landingGear = true;
                    break;
                }
            }
        }
        if (!landingGear) sy++;

        // Size limit (8 * 16 * 8 == 1024)
        int len = (ey - sy) * (ey - sy) * (ez - sz);
        if (len > 1024) return null;

        // Get all blocks, this is bad, but it can be fixed later
        // To be clear I never said *will be*, just *can be* ;)
        ArrayList<Short> ids = new ArrayList<Short>(len);
        ArrayList<Byte> metas = new ArrayList<Byte>(len);
        //int teid = 0;
        for (int nx = sx; nx <= ex; nx++) {
            for (int ny = sy; ny <= ey; ny++) {
                for (int nz = sz; nz <= ez; nz++) {
                    short id = (short) world.getBlockId(nx, ny, nz);
                    if (!isRocketWorthy(id, world, nx, ny, nz)) {
                        ids.add((short) 0);
                        metas.add((byte) 0);
                    } else {
                        byte meta = (byte) world.getBlockMetadata(nx, ny, nz);

                        ids.add(id);
                        metas.add(meta);

                        world.setBlock(nx, ny, nz, 0);
                    }
                }
            }
        }
        // Convert the arrays
        short[] idsA = new short[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            idsA[i] = ids.get(i);
        }
        byte[] metasA = new byte[metas.size()];
        for (int i = 0; i < metas.size(); i++) {
            metasA[i] = metas.get(i);
        }
        return new EntityRocket(
            world,
            idsA,
            metasA,
            Vec3d.createVectorHelper(ex - sx, ey - sy, ez - sz),
            Vec3d.createVectorHelper((x - sx) - 1, (y - sy) + 0.75, (z - sz) - 1)
        );
    }

	@Override
	public boolean interact(EntityPlayer player) {
        if (passenger != null) {
            return true;
        }
        if (!this.world.isClientSide) {
            player.startRiding(this);
            state = 1;
            countdown = 20 * 3;
            xb = (float) x;
            zb = (float) z;
        }
        return true;
	}

    void overboardCheck() {
        if (this.passenger == null || !(this.passenger instanceof EntityPlayer)) {
            // Land
            state = 3;
        }
	}

	public void emit(String name) {
        world.spawnParticle(name, (bb.minX + bb.maxX) / 2.0f, bb.minY, (bb.minZ + bb.maxZ) / 2.0f, 0.0D, -0.1D, 0.0D, 0);
	}

    public void land() {
        // TODO, this offsetting is weird
        // This is super buggy
        int index = 0;
        remove();
        for (int rx = 0; rx <= sx; rx++) {
            for (int ry = 0; ry <= sy; ry++) {
                for (int rz = 0; rz <= sz; rz++) {
                    short id = rocketIds[index];
                    byte meta = rocketMeta[index];
                    index++;
                    if (id == 0 || world.getBlockId((int) x + rx - sx / 2 - 2, (int) y + ry, (int) z + rz - (sz + 1) / 2) != 0) continue;
                    world.setBlockAndMetadata((int) x + rx - sx / 2 - 2, (int) y + ry, (int) z + rz - (sz + 1) / 2, id, meta);
                }
            }
        }
	}

	@Override
	public void tick() {
	    overboardCheck();
	    if (rocketIds == null) remove();
		if (state == 1) {
		    // Jiggle
            float newX = xb + (random.nextFloat() - random.nextFloat()) * 0.1f;
            float newZ = zb + (random.nextFloat() - random.nextFloat()) * 0.1f;

            if (countdown % 20 == 0) {
                Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage((countdown / 20) + "...");
            }
            countdown--;
            if (countdown <= 0) {
                Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("Blast off!");
                yv = 0.01f;
                state = 2;
            }
            emit("flame");
            setPos(newX, y, newZ);
		} else if (state == 2) {
		    yv = yv * 1.1f;
		    setPos(x, y + yv, z);

            if (y > 300) {
                int targetDim = MoonMod.dimensionMoon.id;
                if (((EntityPlayer) this.passenger).dimension == targetDim) {
                    MoonMod.dimensionShift(0);
                } else {
                    MoonMod.dimensionShift(targetDim);
                }
                state = 3;
            }
            if (random.nextInt(3) == 0) emit("largesmoke");
            else emit("smoke");
            if (random.nextInt(5) == 0) emit("flame");
		} else if (state == 3) {
		    yd -= 0.04D;
		    move(xd, yd, zd);
		    yd *= 0.98D;
		    if (this.onGround) {
                state = 0;
                land();
		    } else if (y < -50) {
		        world.setEntityDead(this);
		    }
		}
	}

    @Override
    public void positionRider() {
        passenger.setPos(x + rx, y + ry + passenger.getRidingHeight(), z + rz);
    }

    @Override
    public boolean isPickable() {
        return !this.removed;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putShortArray("RIds", rocketIds);
        tag.putByteArray("RMeta", rocketMeta);
        tag.putInt("SX", sx);
        tag.putInt("SY", sy);
        tag.putInt("SZ", sz);
        tag.putFloat("RX", rx);
        tag.putFloat("RY", ry);
        tag.putFloat("RZ", rz);
        // TODO: Rider pos?
    }
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        rocketIds = tag.getShortArrayOrDefault("RIds", null);
        rocketMeta = tag.getByteArrayOrDefault("RMeta", null);
        sx = tag.getInteger("SX");
        sy = tag.getInteger("SY");
        sz = tag.getInteger("SZ");
		setSize(((float) sx + sz) / 2 - 1, (float) sy - 1);
        rx = tag.getFloat("RX");
        ry = tag.getFloat("RY");
        rz = tag.getFloat("RZ");
    }
    @Override
    public void init() {}
}
