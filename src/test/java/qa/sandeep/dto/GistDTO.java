package qa.sandeep.dto;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Vector;

public class GistDTO {

    private String description;
    private boolean isPublic;
    private Vector<GistFileDTO> gistFiles = new Vector<>();

    public GistDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public GistDTO setPublic(boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public String toString() {
        JsonObjectBuilder gistFilesBuilder = Json.createObjectBuilder();
        for (GistFileDTO file : this.gistFiles){
            gistFilesBuilder.add(file.getName(), file.getJsonObject());
        }

        JsonObject gist = Json.createObjectBuilder()
                .add("description", this.description)
                .add("public", this.isPublic)
                .add("files", gistFilesBuilder.build())
                .build();

        return gist.toString();
    }

    public GistDTO addGistFile(GistFileDTO gistFile) {
        this.gistFiles.add(gistFile);
        return this;
    }
}
