package com.adriftus.dmodelengine.objects;

import com.denizenscript.denizencore.objects.Adjustable;
import com.denizenscript.denizencore.objects.Fetchable;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.ObjectTagProcessor;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.tags.TagRunnable;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import com.ticxo.modelengine.model.ModelEntity;

public class ModelEntityTag implements ObjectTag, Adjustable {
	
	static String prefix = "modelentity";
	ModelEntity me = null;
	
	
	public static ObjectTagProcessor<ModelEntityTag> tagProcessor = new ObjectTagProcessor<ModelEntityTag>();

	public static void registerTag(String name, TagRunnable.ObjectInterface<ModelEntityTag> runnable,
			String... variants) {
		tagProcessor.registerTag(name, runnable, variants);
	}

	public ObjectTag getObjectAttribute(Attribute attribute) {
		return tagProcessor.getObjectAttribute(this, attribute);
	}

	public static void registerTags() {
//	    registerTag("property_1", (att, obj) -> 			// <my_tag@value.property_1>
//	        new ElementTag("my property"), new String[0]);
//	    registerTag("string", (att, obj) -> 				// <my_tag@value.string>
//        	new ElementTag(obj.str), new String[0]);
//	    registerTag("item", (att, obj) -> 					// <my_tag@value.item>
//    		new ItemTag(new ItemStack(Material.DIAMOND_SWORD)), new String[0]);
//	    registerTag("context", (att, obj) -> 				// <my_tag@value.context>
//    		new ElementTag(att.getContext(1)), new String[0]);
	}
	
	public ModelEntityTag(String s) {
	}

	@Fetchable("modelentity")
	public static ModelEntityTag valueOf(String s, TagContext c) {
		Debug.echoError(c.toString());
		return new ModelEntityTag(s.replace(prefix + "@", ""));
	}

	public static boolean matches(String arg) {
		return arg.startsWith(prefix + "@");
	}

	public String getObjectType() {
		return "ModelEntityTag";
	}

	public String getPrefix() {
		return prefix;
	}

	public String identify() {
		return prefix + "@" + me.getModelId();
	}
	
	public String identifySimple() {
		return identify();
	}

	public boolean isUnique() {
		return false;
	}

	public ObjectTag setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public void adjust(Mechanism m) {
		String name = m.getName();
		String value = m.getValue().identify();
		System.out.println("You have adjusted mechanism '" + name + "' to " + value);
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
