package api.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class Pet {
        Long id;
        HashMap<String,String> category;
        String name;
        ArrayList<String> photoUrls;
        ArrayList<HashMap<String,String>> tags;
        String status;
}
