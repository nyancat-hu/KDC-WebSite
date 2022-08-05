package top.imwonder.mcauth.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileInfo {

    private String id;

    private String name;

    private List<Property> properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProperties(Property... properties) {
        if (this.properties == null) {
            this.properties = new ArrayList<>();
        }
        for (Property property : properties) {
            this.properties.add(property);
        }
    }

    public void addProperty(String name, String value, boolean isSign) {
        if (this.properties == null) {
            this.properties = new ArrayList<>();
        }
        Property property = new Property();
        property.setName(name);
        property.setValue(value);
        if (isSign) {
            // property.setSignature(signature);
        }
        this.properties.add(property);
    }
}