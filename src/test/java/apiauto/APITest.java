package apiauto;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;


public class APITest {

    @Test
    public void getUserTest() {
        // Define baseURI
        RestAssured.baseURI = "https://reqres.in/";

        int userId = 1;

        // Test GET api/users?page=1 with total data 6 per page
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("api/users/" + userId)
                .then()
                    .log().all()
                    .assertThat().statusCode(200)
                    .assertThat().body("data.id", Matchers.equalTo(userId));
    }

    @Test
    public void validateJsonSchemaGetSingleUserTest() {
        // Define baseURI
        RestAssured.baseURI = "https://reqres.in";

        int userId = 5;

        // File JSON Schema to compare
        File file = new File("src/test/resources/jsonSchema/GetSingleUserSchema.json");

        // Test GET api
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("api/users/" + userId)
                .then()
                    .log().all()
                    .assertThat().statusCode(200)
                    .assertThat().body(JsonSchemaValidator.matchesJsonSchema(file));
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
                    .log().all()
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

    @Test
    public void updateUserPutTest() {
        // Define baseURI
        RestAssured.baseURI = "https://reqres.in/";

        // Data to update
        int userId = 2;
        String newName = "updatedUser";

        // Test PUT userId 2, update first name
        String fname = RestAssured
                .given()
                .when()
                    .get("api/users/" + userId)
                    .getBody().jsonPath()
                    .get("data.first_name");
        String lname = RestAssured
                .given()
                .when()
                    .get("api/users/" + userId)
                    .getBody().jsonPath()
                    .get("data.last_name");
        String avatar = RestAssured
                .given()
                .when()
                    .get("api/users/" + userId)
                    .getBody().jsonPath()
                    .get("data.avatar");
        String email = RestAssured
                .given()
                .when()
                    .get("api/users/" + userId)
                    .getBody().jsonPath()
                    .get("data.email");

        System.out.println("name before = " + fname);

        // Create body request with HashMap and convert it to JSON
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", userId);
        bodyMap.put("email", email);
        bodyMap.put("first_name", newName);
        bodyMap.put("last_name", lname);
        bodyMap.put("avatar", avatar);
        JSONObject jsonObject = new JSONObject(bodyMap);

        RestAssured
                .given()
                    .log().all()
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                .when()
                    .put("api/users/" + userId)
                .then()
                    .log().all()
                    .assertThat().statusCode(200)
                    .assertThat().body("first_name", Matchers.equalTo(newName));
    }

    @Test
    public void updateUserPatchTest() {
        // Define baseURI
        RestAssured.baseURI = "https://reqres.in/";

        // Data to update
        int userId = 3;
        String newName = "updatedUser";

        // Test PATCH userId 3, update first name
        String fname = RestAssured
                .given()
                .when()
                    .get("api/users/" + userId)
                    .getBody().jsonPath()
                    .get("data.first_name");

        System.out.println("name before = " + fname);

        // Create body request with HashMap and convert it to JSON
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("first_name", newName);
        JSONObject jsonObject = new JSONObject(bodyMap);

        RestAssured
                .given()
                    .log().all()
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                .when()
                    .patch("api/users/" + userId)
                .then()
                    .log().all()
                    .assertThat().statusCode(200)
                    .assertThat().body("first_name", Matchers.equalTo(newName));
    }

    @Test
    public void deleteUserTest() {
        // Define baseURI
        RestAssured.baseURI = "https://reqres.in/";

        // Data to delete
        int userIdToDelete = 4;

        RestAssured
                .given()
                    .log().all()
                .when()
                    .delete("api/users" + userIdToDelete)
                .then()
                    .log().all()
                    .assertThat().statusCode(204);
    }
}
