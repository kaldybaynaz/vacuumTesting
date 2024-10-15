package  steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;


    import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*;

public class VacuumSteps {
        private Map<String, Object> requestBody;
        private Response response;

        @Given("the room dimensions are {int} by {int}")
        public void setRoomDimensions(int roomX, int roomY) {
            requestBody = Map.of(
                    "roomSize", List.of(roomX, roomY),
                    "coords", List.of(),
                    "patches", List.of(),
                    "instructions", ""
            );
        }

        @Given("the vacuum starts at position ({int}, {int})")
        public void setStartingPosition(int startX, int startY) {
            requestBody.put("coords", List.of(startX, startY));
        }

        @Given("dirt spots are located at {string}")
        public void setDirtSpots(String dirtLocations) {
            // Convert string like "[(1,0), (2,2), (2,3)]" into a List of Lists [[1,0], [2,2], [2,3]]
            String[] spots = dirtLocations.replaceAll("[\\[\\]()]", "").split(",");
            List<List<Integer>> patches = new ArrayList<>();
            for (int i = 0; i < spots.length; i += 2) {
                patches.add(List.of(Integer.parseInt(spots[i].trim()), Integer.parseInt(spots[i + 1].trim())));
            }
            requestBody.put("patches", patches);
        }

        @When("I give it the directions \"{string}\"")
        public void setDirections(String directions) {
            requestBody.put("instructions", directions);
            // Send POST request with the requestBody
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post("http://localhost:8080/v1/cleaning-sessions");
        }

        @Then("it should end up at position ({int}, {int})")
        public void verifyFinalPosition(int expectedX, int expectedY) {
            List<Integer> coords = response.jsonPath().getList("coords");
            assertEquals("X coordinate mismatch", expectedX, coords.get(0).intValue());
            assertEquals("Y coordinate mismatch", expectedY, coords.get(1).intValue());
        }

        @Then("it should have cleaned {int} dirt spots")
        public void verifyCleanedSpots(int expectedCleanedSpots) {
            int cleanedSpots = response.jsonPath().getInt("patches");
            assertEquals("Number of cleaned spots mismatch", expectedCleanedSpots, cleanedSpots);
        }
    }






