package GandyClient.mixins.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import GandyClient.events.impl.DrawBlockOutlineEvent;
import GandyClient.events.impl.RenderEvent;
import GandyClient.core.modules.ModInstances;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
	
	@Shadow 
	private float thirdPersonDistance;
    @Shadow 
    private float thirdPersonDistanceTemp;
	@Shadow
	private Minecraft mc;
	@Shadow
	private boolean isDrawBlockOutline() {return true;}
	
	@Inject(method = "updateCameraAndRender", at = @At("RETURN"))
	private void onRenderReturn (CallbackInfo info) {
		new RenderEvent().call();
	}
	
	// 360 perspective mod
	@Overwrite
	private void orientCamera (float partialTicks) {
		Entity entity = mc.getRenderViewEntity();
        float f = entity.getEyeHeight();
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        double d2 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;

        double d3 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;

        if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPlayerSleeping()) {
            ++f;
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!mc.gameSettings.debugCamEnable) {
                final BlockPos blockpos = new BlockPos(entity);
                final IBlockState iblockstate = mc.theWorld.getBlockState(blockpos);
                Block block = iblockstate.getBlock();

                if (block == Blocks.bed) {
                    int j = iblockstate.getValue(BlockBed.FACING).getHorizontalIndex();
                    GlStateManager.rotate((float) (j * 90), 0.0F, 1.0F, 0.0F);
                }
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0f, 0.0f, 0.0f);
            }
        } else if (mc.gameSettings.thirdPersonView > 0) {
            double d4 = thirdPersonDistanceTemp + (thirdPersonDistance - thirdPersonDistanceTemp) * partialTicks;
            if (mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float) (-d4));
            } else {
                float f2 = ModInstances.getModPerspective().getCameraYaw();
                float f3 = ModInstances.getModPerspective().getCameraPitch();
                
                if (mc.gameSettings.thirdPersonView == 2) {
                    f3 += 180.0f;
                }
                final double d5 = -MathHelper.sin(f2 / 180.0f * 3.1415927f) * MathHelper.cos(f3 / 180.0f * 3.1415927f) * d4;
                final double d6 = MathHelper.cos(f2 / 180.0f * 3.1415927f) * MathHelper.cos(f3 / 180.0f * 3.1415927f) * d4;
                final double d7 = -MathHelper.sin(f3 / 180.0f * 3.1415927f) * d4;
                for (int i = 0; i < 8; ++i) {
                    float f4 = (i & 0x1) * 2 - 1;
                    float f5 = (i >> 1 & 0x1) * 2 - 1;
                    float f6 = (i >> 2 & 0x1) * 2 - 1;
                    f4 *= 0.1f;
                    f5 *= 0.1f;
                    f6 *= 0.1f;
                    final MovingObjectPosition movingobjectposition = mc.theWorld.rayTraceBlocks(new Vec3(d0 + f4, d2 + f5, d3 + f6), new Vec3(d0 - d5 + f4 + f6, d2 - d7 + f5, d3 - d6 + f6));
                    if (movingobjectposition != null) {
                        final double d8 = movingobjectposition.hitVec.distanceTo(new Vec3(d0, d2, d3));
                        if (d8 < d4) {
                            d4 = d8;
                        }
                    }
                }
                if (mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }

                GlStateManager.rotate(ModInstances.getModPerspective().getCameraPitch() - f3, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(ModInstances.getModPerspective().getCameraYaw() - f2, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, (float) (-d4));
                GlStateManager.rotate(f2 - ModInstances.getModPerspective().getCameraYaw(), 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(f3 - ModInstances.getModPerspective().getCameraPitch(), 1.0f, 0.0f, 0.0f);    
            }
        } else {
            GlStateManager.translate(0.0f, 0.0f, -0.1f);
        }

        if (!mc.gameSettings.debugCamEnable) {
            float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f;
            final float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            final float roll = 0.0f;
            if (entity instanceof EntityAnimal) {
                final EntityAnimal entityanimal = (EntityAnimal) entity;
                yaw = entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0f;
            }

            GlStateManager.rotate(roll, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(ModInstances.getModPerspective().getCameraPitch(), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(ModInstances.getModPerspective().getCameraYaw() + 180.0f, 0.0f, 1.0f, 0.0f);
        }

        GlStateManager.translate(0.0f, -f, 0.0f);
        d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        d2 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
        d3 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        ((IEntityRendererMixin) (EntityRenderer) (Object) this).setCloudFog(mc.renderGlobal.hasCloudFog(d0, d2, d3, partialTicks));
	}
	
	@Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", args = "ldc=mouse"))
    private void updateCameraAndRender(float partialTicks, long nanoTime, CallbackInfo ci) {
		ModInstances.getModPerspective().overrideMouse();
    }
	
	// block outline event
	@Inject(method = "renderWorldPass", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", args = "ldc=outline"), cancellable = true)
	private void checkBlockOverlay(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
		if (
				mc.objectMouseOver == null || 
				mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK
		) {
            return;
        }

        MovingObjectPosition blockPosition = mc.thePlayer.rayTrace(6.0, partialTicks); // get block 6 units max away from player head (creative)
        if (
        		blockPosition == null || 
        		blockPosition.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK
        ) {
            return;
        }

        Block block = mc.thePlayer.worldObj.getBlockState(blockPosition.getBlockPos()).getBlock();
        if (
        		block == null || 
        		block == Blocks.air || 
        		block == Blocks.barrier || 
        		block == Blocks.water || 
        		block == Blocks.flowing_water || 
        		block == Blocks.lava || 
        		block == Blocks.flowing_lava
        ) {
            return;
        }
        
        EntityPlayer player = ((EntityPlayer) mc.getRenderViewEntity());
        MovingObjectPosition position = mc.objectMouseOver;
        
        /* 
         * Call BlockOutlineDraw event with args (player, position, partialTicks)
         * here
         * */
        
        new DrawBlockOutlineEvent(player, position, partialTicks).call();
	}
}
