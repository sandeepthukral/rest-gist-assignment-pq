package qa.sandeep.dto;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class GistFileDTO {
    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public GistFileDTO setName(String name) {
        this.name = name;
        return this;
    }

    public GistFileDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public JsonObjectBuilder getJsonObject() {

        return Json.createObjectBuilder().add("content", this.content);
    }

}
