package top.imwonder.mcauth.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {

    private String id;

    private List<Property> properties;

    public UserInfo() {
        this.properties = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addProperties(Property... properties) {
        for (Property property : properties) {
            this.properties.add(property);
        }
    }

    public void addProperty(String name, String value) {
        Property property = new Property();
        property.setName(name);
        property.setValue(value);
        properties.add(property);
    }
}
