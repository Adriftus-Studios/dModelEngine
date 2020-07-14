package com.adriftus.dmodelengine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.utilities.implementation.BukkitScriptEntryData;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.scripts.containers.ScriptContainer;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import com.ticxo.modelengine.events.ModelEntityFallEvent;

public class ModelEntityFallScriptEvent extends BukkitScriptEvent implements Listener {

	ModelEntityFallEvent event;
	ModelEntityFallScriptEvent instance;
	EntityTag entity;
	
	public ModelEntityFallScriptEvent() {
		this.instance = this;
	}

	// @Override ????
	public boolean couldMatch(ScriptContainer scriptContainer, String s) {
		return CoreUtilities.toLowerCase(s).startsWith("model entity fall event");
	}

	@Override
	public String getName() {
		return "ModelEntityFallScriptEvent";
	}

	@Override
	public ScriptEntryData getScriptEntryData() {
		return new BukkitScriptEntryData(null, null);
	}

	@Override
	public boolean matches(ScriptPath path) {
		//TODO
		return true;
	}

	@Override
	public ObjectTag getContext(String name) {
		//TODO
		return super.getContext(name);
	}

	@EventHandler
	public void onEvent(ModelEntityFallEvent e) {
		if (EntityTag.isNPC(e.getEntity())) {
			return;
		}
		this.event = e;
		this.entity = new EntityTag(e.getEntity());
		fire(e);
	}

}
