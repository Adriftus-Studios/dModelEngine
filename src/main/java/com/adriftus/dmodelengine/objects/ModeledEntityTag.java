package com.adriftus.dmodelengine.objects;

import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.objects.*;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.ObjectTagProcessor;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.tags.TagRunnable;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;

import java.util.Map;
import java.util.UUID;

public class ModeledEntityTag implements ObjectTag, Adjustable {

	// <--[language]
	// @name ModeledEntityTag Objects
	// @group Object System
	// @plugin dModelEngine
	// @description
	// A ModeledEntityTag is an object that represents an entity with a custom model (provided by ModelEngine).
	//
	// These use the object notation "modeledentity@".
	// -->

	static String prefix = "modeledentity";
	ModeledEntity me = null;
	
	public static ObjectTagProcessor<ModeledEntityTag> tagProcessor = new ObjectTagProcessor<ModeledEntityTag>();

	public static void registerTag(String name, TagRunnable.ObjectInterface<ModeledEntityTag> runnable,
			String... variants) {
		tagProcessor.registerTag(name, runnable, variants);
	}

	public ObjectTag getObjectAttribute(Attribute attribute) {
		return tagProcessor.getObjectAttribute(this, attribute);
	}

	public ModeledEntity getModeledEntity() {
		return me;
	}

	public static void registerTags() {
		// <--[tag]
		// @attribute <ModeledEntityTag.entity>
		// @returns EntityTag
		// @plugin dModelEngine
		// @description
		// Returns the entity that the ModeledEntity is attached to.
		// -->
		registerTag("entity", (attribute, object) -> {
			return new EntityTag(object.me.getEntity());
		});
		// <--[tag]
		// @attribute <ModeledEntityTag.uuid>
		// @returns ElementTag
		// @plugin dModelEngine
		// @description
		// Returns the UUID of the ModeledEntity.
		// -->
		registerTag("uuid", (attribute, object) -> {
			return new ElementTag(object.me.getEntity().getUniqueId().toString());
		});
		// <--[tag]
		// @attribute <ModeledEntityTag.active_models>
		// @returns ListTag(ActiveModelTag)
		// @plugin dModelEngine
		// @description
		// Returns a list of ActiveModels that are attached to the ModeledEntity
		// -->
		registerTag("active_models", (attribute, object) -> {
			ListTag list = new ListTag();
			for (Map.Entry<UUID, ActiveModel> e : object.me.getModels().entrySet()) {
				list.addObject(new ActiveModelTag(e.getValue()));
			}
			return list;
		});
	}
	
	public ModeledEntityTag(String uuid) {
		me = ModelEngineAPI.getModelManager().getModeledEntity(UUID.fromString(uuid));
	}

	public ModeledEntityTag(ModeledEntity modeledEntity) {
		me = modeledEntity;
	}

	@Fetchable("modeledentity")
	public static ModeledEntityTag valueOf(String s, TagContext c) {
		return new ModeledEntityTag(s.replace(prefix + "@", ""));
	}

	public static boolean matches(String arg) {
		return arg.startsWith(prefix + "@");
	}

	public String getObjectType() {
		return "ModeledEntityTag";
	}

	public String getPrefix() {
		return prefix;
	}

	public String identify() {
		return prefix + "@" + me.getEntity().getUniqueId().toString();
	}
	
	public String identifySimple() {
		return identify();
	}

	public boolean isUnique() {
		return false;
	}

	public ObjectTag setPrefix(String prefix) {
//		this.prefix = prefix;
		return this;
	}

	public void adjust(Mechanism m) {
		String name = m.getName();
		String value = m.getValue().identify();
//		System.out.println("You have adjusted mechanism '" + name + "' to " + value);
	}

	public void applyProperty(Mechanism m) {
		// Dont use this, use adjust
	}

	@Override
	public String toString() {
		// this method solves an issue with ObjectFetcher when narrating this tag without attributes
		return identify();
	}
}
