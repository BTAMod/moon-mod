package teamport.moonmod.entity.render;

import teamport.moonmod.entity.EntityRocket;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.season.SeasonManager;
import net.minecraft.core.world.WorldSource;


public class RocketWorld implements WorldSource {
    public static EntityRocket target = null;

    @Override
    public int getBlockId(int x, int y, int z) {
        // Making size like this was such a stupid idea
        if (x >= target.sx + 1 || y >= target.sy + 1 || z >= target.sz + 1) return 0;
        if (0 > x || 0 > y || 0 > z) return 0;
        return target.rocketIds[z + y * (1 + target.sz) + x * (1 + target.sy) * (1 + target.sz)];
    }
    @Override
    public int getBlockMetadata(int x, int y, int z) {
        if (x >= target.sx + 1 || y >= target.sy + 1 || z >= target.sz + 1) return 0;
        if (0 > x || 0 > y || 0 > z) return 0;
        return target.rocketMeta[z + y * (1 + target.sz) + x * (1 + target.sy) * (1 + target.sz)];
    }
    // TODO
    @Override
    public TileEntity getBlockTileEntity(int x, int y, int z) { return null; }

    // Easy, relies on getBlockId
    @Override
    public Block getBlock(int x, int y, int z) { return Block.getBlock(getBlockId(x, y, z)); }
    @Override
    public Material getBlockMaterial(int x, int y, int z) {
        Block b = getBlock(x, y, z);
        if (b == null) return Material.air;
        return b.blockMaterial;
    }
    @Override
    public boolean isBlockOpaqueCube(int x, int y, int z) {
        Block block = Block.blocksList[getBlockId(x, y, z)];
        if (block == null)
            return false;
        return block.isSolidRender();
    }
    @Override
    public boolean isBlockNormalCube(int x, int y, int z) {
        Block b = getBlock(x, y, z);
        if (b == null) {
            return false;
        }
        return b.blockMaterial.blocksMotion() && b.renderAsNormalBlock();
    }

    // Easy stuff, just forward to hardcord
    @Override
    public float getBrightness(int x, int y, int z, int paramInt4) { return 15.0f; }
    @Override
    public int getLightmapCoord(int x, int y, int z, int paramInt4) {
        return LightmapHelper.getLightmapCoord(15, 15);
    }
    @Override
    public float getLightBrightness(int x, int y, int z) { return 15.0f; }
    @Override
    public double getBlockTemperature(int paramInt1, int paramInt2) { return 0.0; }
    @Override
    public double getBlockHumidity(int paramInt1, int paramInt2) { return 0.0; }
    @Override
    public SeasonManager getSeasonManager() { return target.world.getSeasonManager(); }
    @Override
    public Biome getBlockBiome(int x, int y, int z) { return target.world.getBlockBiome(x, y, z); }
    @Override
    public int getSavedLightValue(LightLayer paramLightLayer, int x, int y, int z) { return 15; }
}