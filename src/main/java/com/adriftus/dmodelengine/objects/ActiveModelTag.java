package com.adriftus.dmodelengine.objects;

import com.denizenscript.denizencore.objects.*;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.ObjectTagProcessor;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.tags.TagRunnable;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.component.StateProperty;

import java.util.UUID;

public class ActiveModelTag implements ObjectTag, Adjustable {

    // <--[language]
    // @name ActiveModelTag Objects
    // @group Object System
    // @plugin dModelEngine
    // @description
    // A ActiveModelTag is an object that represents a specific model that is attached to a ModeledEntity.
    //
    // These use the object notation "activemodel@".
    // -->

    static String prefix = "activemodel";
    ActiveModel am = null;

    public static ObjectTagProcessor<ActiveModelTag> tagProcessor = new ObjectTagProcessor<>();

    public static void registerTag(String name, TagRunnable.ObjectInterface<ActiveModelTag> runnable,
                                   String... variants) {
        tagProcessor.registerTag(name, runnable, variants);
    }

    public ObjectTag getObjectAttribute(Attribute attribute) {
        return tagProcessor.getObjectAttribute(this, attribute);
    }

    public static void registerTags() {
        // <--[tag]
        // @attribute <ActiveModelTag.modeled_entity>
        // @returns ModeledEntityTag
        // @plugin dModelEngine
        // @description
        // Returns the ModeledEntity that the model is assigned to.
        // -->
        registerTag("modeled_entity", (attribute, object) -> {
            return new ModeledEntityTag(object.am.getModeledEntity());
        });
        // <--[tag]
        // @attribute <ActiveModelTag.model_id>
        // @returns ElementTag(Number)
        // @plugin dModelEngine
        // @description
        // Returns the id of the active model.
        // -->
        registerTag("model_id", (attribute, object) -> {
            return new ElementTag(object.am.getModelId());
        });
        // <--[tag]
        // @attribute <ActiveModelTag.states>
        // @returns ListTag
        // @plugin dModelEngine
        // @mechanism ActiveModelTag.add_state
        // @mechanism ActiveModelTag.remove_state
        // @description
        // Returns the id of the active model.
        // -->
        registerTag("states", (attribute, object) -> {
            ListTag list = new ListTag();
            for (String s : object.am.getStates().keySet()) {
                list.addObject(new ElementTag(s));
            }
            return list;
        });
    }

    public ActiveModelTag(ActiveModel model) {
        am = model;
    }

    @Fetchable("activemodel")
    public static ActiveModelTag valueOf(String s, TagContext c) {
        return new ActiveModelTag(ModelEngineAPI.getModelManager().getModeledEntity(UUID.fromString(s.replace(prefix + "@", "").split(",")[0])).getModel(UUID.fromString(s.replace(prefix + "@", "").split(",")[1])));
    }

    public static boolean matches(String arg) {
        return arg.startsWith(prefix + "@");
    }

    public String getObjectType() {
        return "ActiveModelTag";
    }

    public String getPrefix() {
        return prefix;
    }

    public String identify() {
        return prefix + "@" + am.getModeledEntity().getEntity().getUniqueId().toString() + "," + am.getUniqueID().toString();
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
        // <--[mechanism]
        // @object ActiveModelTag
        // @name add_state
        // @input ElementTag
        // @description
        // Forces the ActiveModel to play an animation
        // @tags
        // <ActiveModelTag.states>
        // -->
        if(m.getName().equalsIgnoreCase("add_state")) {
            am.addState(m.getValue().asString(), StateProperty.create(0, 0, 1));
        }
        // <--[mechanism]
        // @object ActiveModelTag
        // @name remove_state
        // @input ElementTag
        // @description
        // Forces the ActiveModel to stop playing an animation
        // @tags
        // <ActiveModelTag.states>
        // -->
        if(m.getName().equalsIgnoreCase("remove_state")) {
            am.removeState(m.getValue().asString(), true);
        }
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
