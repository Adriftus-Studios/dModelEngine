package com.adriftus.dmodelengine;

import com.adriftus.dmodelengine.objects.ActiveModelTag;
import com.adriftus.dmodelengine.objects.ModeledEntityTag;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.objects.ObjectFetcher;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public void onEnable() {
		ObjectFetcher.registerWithObjectFetcher(ModeledEntityTag.class, ModeledEntityTag.tagProcessor);
		ModeledEntityTag.registerTags();
		ObjectFetcher.registerWithObjectFetcher(ActiveModelTag.class, ActiveModelTag.tagProcessor);
		ActiveModelTag.registerTags();
		EntityTag.registerTag("modeled_entity", (attribute, object) -> {
			return new ModeledEntityTag(object.getUUID().toString());
		});
	}
	
}
