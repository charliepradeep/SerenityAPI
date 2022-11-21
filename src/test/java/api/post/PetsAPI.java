package api.post;


import Utils.JSONFileReader;
import api.model.Pet;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.WithTag;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SerenityRunner.class)
public class PetsAPI {

    final String baseURL = "https://petstore.swagger.io/";
    final String postURL = "v2/pet";

    public Response getApiResponse(String requestType, String filePath) throws ParseException, IOException {
        String jsonString = new JSONFileReader().convertJSONFileToString(filePath);

        switch(requestType)
        {
            case "put":
                return SerenityRest.given().baseUri(baseURL)
                        .body(jsonString)
                        .contentType(ContentType.JSON)
                        .when()
                        .put(postURL);

            default:
                return SerenityRest.given().baseUri(baseURL)
                        .body(jsonString)
                        .contentType(ContentType.JSON)
                        .when()
                        .post(postURL);
        }
    }

    public Response getApiResponse(String id) throws ParseException, IOException {
                return SerenityRest.given().baseUri(baseURL)
                        .header("api_key","special-key")
                        .contentType(ContentType.JSON)
                        .when()
                        .delete(postURL+"/"+id);
    }


    @Test
    @WithTag("API")
    public void verifyPOSTApi() throws ParseException, IOException {


        String filePath = "src/test/resources/input.json";
        Response response = getApiResponse("post",filePath);
        System.out.println(response.getBody().asPrettyString());
        Serenity.reportThat("POST API Verification", ()-> assertThat(response.then().statusCode(200)));
    }

    @Test
    @WithTag("API")
    public void verifyPUTApi() throws ParseException, IOException {
        String filePath = "src/test/resources/modified.json";
        Response response = getApiResponse("put",filePath);
        System.out.println(response.getBody().asPrettyString());
        Serenity.reportThat("PUT API Verification", ()-> assertThat(response.then().statusCode(200)));
    }

    @Test
    @WithTag("API")
    public void verifyDELETEApi() throws ParseException, IOException {
        Response response = getApiResponse("9223372036854529124");
        System.out.println(response.getBody().asPrettyString());
        Serenity.reportThat("DELETE API Verification", ()-> assertThat(response.then().statusCode(200)));
    }

    @AfterAll
    public void endReport() throws IOException {
        File file = new File("SerenityReports/serenity-summary.html");
        System.out.println(file.getCanonicalPath());
        Path generatedReport = Paths.get(file.getCanonicalPath());
        Serenity.recordReportData().withTitle("Customized XML report")
                .fromFile(generatedReport);
    }



}
