package Utils;

import api.model.Pet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JSONFileReader {

    public String convertJSONFileToString(String filePath) throws FileNotFoundException, JsonProcessingException {
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader inputStream = new BufferedReader(fileReader);
        Pet pet = new Gson().fromJson(inputStream,Pet.class);
        System.out.println("My JSON Object String : "+pet.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(pet);
    }
}
