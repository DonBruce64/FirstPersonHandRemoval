package firstpersonhandremoval;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = FPHR.MODID, version = FPHR.VERSION, clientSideOnly = true)
public class FPHR{
    public static final String MODID = "fphr";
    public static final String VERSION = "2.0";
    private static boolean firstRun;
    private static boolean mainhandSwitchedState;
    private static boolean offhandSwitchedState;
    private static byte mainhandSwitchDelay = 0;
    private static byte offhandSwitchDelay = 0;
    
    @Instance
    public static FPHR instance;
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	MinecraftForge.EVENT_BUS.register(instance);
    }
    
    @SubscribeEvent
    public void on(RenderSpecificHandEvent event){
    	if(firstRun){
    		mainhandSwitchedState = Minecraft.getMinecraft().player.getHeldItemMainhand().isEmpty();
    		offhandSwitchedState = Minecraft.getMinecraft().player.getHeldItemOffhand().isEmpty();
    	}
    	if(event.getHand().equals(EnumHand.MAIN_HAND)){
    		if(event.getSwingProgress() != 0){
    			mainhandSwitchedState = false;
    			mainhandSwitchDelay = 0;
    			return;
    		}
	    	if(Minecraft.getMinecraft().player.getHeldItemMainhand().isEmpty()){
	    		mainhandSwitchedState = true;
	    		event.setCanceled(true);
	    	}else{
	    		if(mainhandSwitchedState){
	    			event.setCanceled(true);
	    			mainhandSwitchedState = false;
	    			mainhandSwitchDelay = 15;
	    		}else if(mainhandSwitchDelay > 0){
	    			event.setCanceled(true);
	    			--mainhandSwitchDelay;
	    		}
	    	}
    	}
    	if(event.getHand().equals(EnumHand.OFF_HAND)){
    		if(event.getSwingProgress() != 0){
    			offhandSwitchedState = false;
    			offhandSwitchDelay = 0;
    			return;
    		}
	    	if(Minecraft.getMinecraft().player.getHeldItemOffhand().isEmpty()){
	    		offhandSwitchedState = true;
	    		event.setCanceled(true);
	    	}else{
	    		if(offhandSwitchedState){
	    			event.setCanceled(true);
	    			offhandSwitchedState = false;
	    			offhandSwitchDelay = 15;
	    		}else if(offhandSwitchDelay > 0){
	    			event.setCanceled(true);
	    			--offhandSwitchDelay;
	    		}
	    	}
    	}
    }
}
