package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetPOJO {
    Long id;
    String name;
    String status;

    HashMap<String,String> category;

    ArrayList<String> photoUrls;

    ArrayList<HashMap<String,String>> tags;

    public void setCategory(HashMap<String, String> category) {this.category = category; }

    public HashMap<String,String> getCategory() { return category; }

    public void setPhotoUrls(ArrayList<String> photoUrls) { this.photoUrls = photoUrls; }

    public ArrayList<String> getPhotoUrls() { return photoUrls; }

    public void setTags(ArrayList<HashMap<String,String>> tags) { this.tags = tags; }

    public ArrayList<HashMap<String,String>> getTags() { return tags; }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

}
