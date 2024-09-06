package teamport.moonmod.entity.render;

import teamport.moonmod.entity.EntityRocket;

import net.minecraft.core.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;

import org.lwjgl.opengl.GL11;

public class RocketRenderer extends EntityRenderer<EntityRocket> {
    public RenderBlocks rb = new RenderBlocks(new RocketWorld());

    public void doRender(Tessellator tessellator, EntityRocket entity, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x - (entity.sx / 2), (float) y + 0.5f, (float) z - (entity.sz / 2));

        TextureRegistry.blockAtlas.bindTexture();

        GL11.glDisable(GL11.GL_LIGHTING);
        Tessellator t = Tessellator.instance;
        tessellator.startDrawingQuads();
        BlockModel.setRenderBlocks(rb);
        RocketWorld.target = entity;
        for (int rx = 0; rx <= entity.sx; rx++) {
            for (int ry = 0; ry <= entity.sy; ry++) {
                for (int rz = 0; rz <= entity.sz; rz++) {
                    Block b = rb.blockAccess.getBlock(rx, ry, rz);
                    if (b == null) continue;
                    GL11.glTranslatef((float) rx, (float) ry, (float) rz);
                    BlockModelDispatcher.getInstance().getDispatch(b).renderNoCulling(t, rx, ry, rz);
                    GL11.glTranslatef((float) -rx, (float) -ry, (float) -rz);
                }
            }
        }
        RocketWorld.target = null;

        tessellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
