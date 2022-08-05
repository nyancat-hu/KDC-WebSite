package top.imwonder.mcauth.enumeration;

public enum TextureType {
    STEVE("Steve", "default"), ALEX("Alex", "slim"), CAPE("Cape", null);

    private String name;
    private String model;

    TextureType(String name, String model) {
        this.name = name;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
