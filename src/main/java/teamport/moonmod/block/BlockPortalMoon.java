package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.world.World;

public class BlockPortalMoon extends BlockPortal {
	public BlockPortalMoon(String key, int id, int targetDimension, int portalMaterialId, int portalTriggerId) {
		super(key, id, targetDimension, portalMaterialId, portalTriggerId);
	}
}
