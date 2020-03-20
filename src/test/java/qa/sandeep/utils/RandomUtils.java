package qa.sandeep.utils;

import qa.sandeep.dto.GistDTO;
import qa.sandeep.dto.GistFileDTO;

public class RandomUtils {

    public static String getRandomGist(boolean isPublic){
        GistDTO gist = new GistDTO();
        GistFileDTO file = new GistFileDTO()
                .setName("fileName")
                .setContent("this is file content");
        return gist
                .setDescription("desc")
                .setPublic(isPublic)
                .addGistFile(file)
                .toString();
    }

    public static String getRandomGistWithMultipleFiles(boolean isPublic){
        GistDTO gist = new GistDTO();
        GistFileDTO file1 = new GistFileDTO()
                .setName("fileName1")
                .setContent("this is file content1");
        GistFileDTO file2 = new GistFileDTO()
                .setName("fileName2")
                .setContent("this is file content2");
        return gist
                .setDescription("desc")
                .setPublic(isPublic)
                .addGistFile(file1)
                .addGistFile(file2)
                .toString();
    }
}
