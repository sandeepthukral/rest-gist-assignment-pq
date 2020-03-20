package qa.sandeep.utils;

import org.apache.commons.lang3.RandomStringUtils;
import qa.sandeep.dto.GistDTO;
import qa.sandeep.dto.GistFileDTO;

public class RandomUtils {

    private String getFileName() {
        return RandomStringUtils.randomAlphabetic(20);
    }

    private String getFileContent() {
        return RandomStringUtils.randomAlphabetic(100);
    }

    private String getDescription() {
        return RandomStringUtils.randomAlphanumeric(50);
    }

    public String getRandomGist(boolean isPublic){
        GistDTO gist = new GistDTO();
        GistFileDTO file = new GistFileDTO()
                .setName(getFileName())
                .setContent(getFileContent());
        return gist
                .setDescription(getDescription())
                .setPublic(isPublic)
                .addGistFile(file)
                .toString();
    }

    public String getRandomGistWithMultipleFiles(boolean isPublic){
        GistDTO gist = new GistDTO();
        GistFileDTO file1 = new GistFileDTO()
                .setName(getFileName())
                .setContent(getFileContent());
        GistFileDTO file2 = new GistFileDTO()
                .setName(getFileName())
                .setContent(getFileContent());
        return gist
                .setDescription(getDescription())
                .setPublic(isPublic)
                .addGistFile(file1)
                .addGistFile(file2)
                .toString();
    }
}
