package RestAssuredProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Project {
    RequestSpecification requestSpec;
    // Declare response specification
    ResponseSpecification responseSpec;
String sshKey;
int sshKeyId;
    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                // Set content type
                .setContentType(ContentType.JSON)
                // Set base URL
                .addHeader("Authorization","token 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")
                .setBaseUri("https://api.github.com")
                // Build request specification
                .build();
        responseSpec = new ResponseSpecBuilder()
                // Check status code in response
                .expectStatusCode(201)
                // Check response content type
                .expectContentType("application/json")
                // Check if response contains name property
                // Build response specification
                .build();
sshKey="ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIFxZbHTMDBXev5----";
    }
@Test
    public void post()
{
    String reqBody = "{\"title\": \"RestAssuredAPIKey\",  \"key\": \""+sshKey+"\" }";

    Response response=given().spec(requestSpec).body(reqBody).when().post("/user/keys");
    String resBody= response.getBody().asPrettyString();
    System.out.println(resBody);
    System.out.println(response.getStatusCode());
    sshKeyId=response.then().extract().path("id");
    System.out.println(sshKeyId);

    Assert.assertEquals(response.getStatusCode(), 201, "Correct status code passing");


}

    @Test(priority=2)
    public void getKey() {
        Response response =
                given().spec(requestSpec).when()
                        .get("/user/keys");
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Correct status code is returned");
    }

    @Test(priority=3)
    public void deleteKey() {
        Response response =
                given().spec(requestSpec)// Set headers
                        .when()
                        .delete("/user/keys/"+sshKeyId); // Send DELETE request

        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 204, "Correct status code is not returned");

}}