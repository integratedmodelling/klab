package org.integratedmodelling.klab.hub.tags.payload;

public class TagRequest {

    private String id;
    private String type;
    private String iTagElement;
    private String iTagElementId;
    private String name;
    private String title;
    private String message;
    private String visible;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getiTagElement() {
        return iTagElement;
    }
    public void setiTagElement(String iTagElement) {
        this.iTagElement = iTagElement;
    }
    public String getiTagElementId() {
        return iTagElementId;
    }
    public void setiTagElementId(String iTagElementId) {
        this.iTagElementId = iTagElementId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getVisible() {
        return visible;
    }
    public void setVisible(String visible) {
        this.visible = visible;
    }

}
