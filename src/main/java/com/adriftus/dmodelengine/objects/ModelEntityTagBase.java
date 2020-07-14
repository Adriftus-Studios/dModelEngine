package com.adriftus.dmodelengine.objects;

import org.bukkit.Bukkit;

import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.ReplaceableTagEvent;
import com.denizenscript.denizencore.tags.TagManager;
import com.denizenscript.denizencore.tags.TagRunnable;
import com.denizenscript.denizencore.utilities.CoreUtilities;

public class ModelEntityTagBase {
	public ModelEntityTagBase() {
		TagManager.registerTagHandler(new TagRunnable.RootForm() {
			@Override
			public void run(ReplaceableTagEvent event) {
				event(event);
			}
		}, "modelentity");
	}
	public void event(ReplaceableTagEvent event) {
		if (!event.matches("modelentity") || event.replaced()) {
			return;
		}
		ModelEntityTag tag = null;
		if (event.hasNameContext()) {
			tag = ModelEntityTag.valueOf(event.getNameContext(), event.getAttributes().context);
		}
		if (tag == null) {
			return;
		}
		Bukkit.getPlayer("AJ_4real").sendMessage("1");
		Bukkit.getPlayer("AJ_4real").sendMessage(tag.identify());
		Attribute att = event.getAttributes();
		event.setReplacedObject(CoreUtilities.autoAttrib(tag, att.fulfill(1)));
	}
}
