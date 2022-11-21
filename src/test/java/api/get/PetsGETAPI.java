package api.get;


import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.WithTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import pojo.PetPOJO;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SerenityRunner.class)
public class PetsGETAPI {

    public Response getApiResponse()
    {
        return SerenityRest.given().baseUri("https://petstore.swagger.io/")
                .when()
                .get("v2/pet/findByStatus?status=available");
    }


    @Test
    @WithTag("API")
    public void verifyApi()
    {
        Response response = getApiResponse();
        assertThat(response.then().statusCode(200));
    }

    @Test
    @WithTag("API")
    public void verifyNumberOFPets()
    {
        Response response = getApiResponse();
        JsonPath jsonPath = response.jsonPath();
        PetPOJO[] petPOJOarray = jsonPath.getObject("",PetPOJO[].class);
        List<PetPOJO> petPOJOList = jsonPath.getList("",PetPOJO.class);
        AtomicInteger incrementer= new AtomicInteger();
        petPOJOList.stream().forEach(e->{
            if(e.getStatus().equalsIgnoreCase("available"))
                incrementer.getAndIncrement();
        });
        assertThat(petPOJOarray.length==incrementer.get());
        assertThat(petPOJOarray.length>5);
        Long count = petPOJOList.stream().count();
        System.out.println("Response Count: "+count);
    }

    @Test
    @WithTag("API")
    public void verifyHeaders()
    {
        Response response = getApiResponse();
        Headers headers = response.getHeaders();
        String contentType = headers.getValue("Content-Type");
        String methodsAllowed = headers.getValue("Access-Control-Allow-Methods");
        assertThat(contentType.equalsIgnoreCase("application/json"));
        assertThat(methodsAllowed.equalsIgnoreCase("GET, POST, DELETE, PUT"));
    }

    @Test
    @WithTag("API")
    public void failedTest()
    {
        Response response = SerenityRest.given().baseUri("https://petstore.swagger.io/")
                .when()
                .get("v2/pet/findByStatu");
        response.then().statusCode(200);
    }
}
