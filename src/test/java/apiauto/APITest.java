package apiauto;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class APITest {

    @Test
    public void getUserTest() {
        // Define baseURI
        RestAssured.baseURI = "https://reqres.in/";

        // Test GET api/users?page=1 with total data 6 per page
        RestAssured
                .given()
                .when()
                    .get("api/users?page=1")
                .then()
                    .log().all()
                    .assertThat().statusCode(200)
                    .assertThat().body("page", Matchers.equalTo(1))
                    .assertThat().body("data.id", Matchers.hasSize(6));
    }

    @Test
    public void createNewUserTest() {
        // Define baseURI
        RestAssured.baseURI = "https://reqres.in/";

        // Create POST body with parameter "name" and "job" in JSON format
        String name = "Husensei";
        String job = "QA Engineer";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", name);
        jsonObject.put("job", job);

        // Test POST with header that accept JSON Format
        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body(jsonObject.toString())
                .when()
                    .post("api/users")
                .then()
                    .log().all()
                    .assertThat().statusCode(201)
                    .assertThat().body("name", Matchers.equalTo(name))
                    .assertThat().body("job", Matchers.equalTo(job))
                    .assertThat().body("$", Matchers.hasKey("id"))
                    .assertThat().body("$", Matchers.hasKey("createdAt"));
    }
}
