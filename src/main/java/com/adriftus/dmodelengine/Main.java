package com.adriftus.dmodelengine;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.adriftus.dmodelengine.events.ModelEntityFallScriptEvent;
import com.adriftus.dmodelengine.objects.ModelEntityTag;
import com.adriftus.dmodelengine.objects.ModelEntityTagBase;
import com.denizenscript.denizencore.events.ScriptEvent;
import com.denizenscript.denizencore.objects.ObjectFetcher;

public class Main extends JavaPlugin {

	public void onEnable() {
//		Bukkit.getPluginManager().registerEvents(new ModelEntityFallScriptEvent(), this);
		ScriptEvent.registerScriptEvent(new ModelEntityFallScriptEvent());
		ObjectFetcher.registerWithObjectFetcher(ModelEntityTag.class, ModelEntityTag.tagProcessor);
//		PropertyParser.registerProperty(MyFetchableObjectTagProvider.class, PlayerTag.class);
		ModelEntityTag.registerTags();
		new ModelEntityTagBase();
	}
	
}
