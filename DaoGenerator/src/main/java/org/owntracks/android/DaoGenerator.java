package org.owntracks.android;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

// Generates Data Access Objects in src/main/java/org.owntracks.android/db
// Increase schema version if changes are made
// To generate files, open Gradle (View > Tool Windows > Gradle) tasks and chose android > :DaoGenerator > Tasks > other, right click "run"  and select Run '[run]'.

public class DaoGenerator {
    private static final int SCHEMA_VERSION = 3;

    public static void main(String args[]) throws Exception {

        Schema schema = new Schema(SCHEMA_VERSION, "org.owntracks.android.db");
        schema.enableKeepSectionsByDefault();


        Entity contactLink = schema.addEntity("ContactLink");
        contactLink.addIdProperty();
        Property topic = contactLink.addStringProperty("topic").notNull().getProperty();
        contactLink.addLongProperty("contactId");
        Property modeId = contactLink.addIntProperty("modeId").notNull().getProperty();

        // GreenDao does not yet support compound primary keys. We create a unique index on the two columns instead
        Index compoundPk = new Index();
        compoundPk.addProperty(topic);
        compoundPk.addProperty(modeId);
        compoundPk.makeUnique();
        contactLink.addIndex(compoundPk);


        Entity waypoint = schema.addEntity("Waypoint");
        waypoint.addIdProperty();
        waypoint.addStringProperty("description");
        waypoint.addDoubleProperty("latitude");
        waypoint.addDoubleProperty("longitude");
        waypoint.addStringProperty("geocoder");
        waypoint.addBooleanProperty("shared");
        waypoint.addDateProperty("date");
        waypoint.addIntProperty("radius");
        waypoint.addIntProperty("transitionType");
        waypoint.addStringProperty("geofenceId");
        waypoint.addStringProperty("ssid");
        waypoint.addIntProperty("modeId").notNull();

        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, args[0]);
    }
}
