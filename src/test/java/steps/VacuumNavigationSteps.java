package steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class VacuumNavigationSteps {
    private int roomX;
    private int roomY;
    private List<Integer> startPosition;
    private List<List<Integer>> dirtPatches;
    private String directions;
    private Response response;

    @Given("a room of dimensions {int} by {int}")
    public void setRoomDimensions(int x, int y) {
        this.roomX = x;
        this.roomY = y;
    }

    @And("the vacuum starts at position {int}, {int}")
    public void setStartPosition(int startX, int startY) {
        this.startPosition = List.of(startX, startY);
    }

    @And("dirt patches are at {string}")
    public void dirtPatchesAreAtDirtLocations(String patches) {
        dirtPatches = new ArrayList<>();
        String[] patchArray = patches.replaceAll("[\\[\\]()]", "").split("\\s*,\\s*");
        for (int i = 0; i < patchArray.length; i += 2) {
            List<Integer> patch = List.of(
                    Integer.parseInt(patchArray[i]),
                    Integer.parseInt(patchArray[i + 1])
            );
            dirtPatches.add(patch);
        }

    }

    @When("I send the directions {string}")
    public void sendDirections(String directions) {
        this.directions = directions;

        String requestBody = String.format(
                "{ \"roomSize\": [%d, %d], \"coords\": [%d, %d], \"patches\": %s, \"instructions\": \"%s\" }",
                roomX, roomY, startPosition.get(0), startPosition.get(1), dirtPatches.toString(), directions
        );

        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post("http://localhost:8080/v1/cleaning-sessions");

        Assert.assertEquals(200, response.statusCode());
    }

    @Then("the vacuum should end at ({int}, {int})")
    public void checkEndPosition(int endX, int endY) {
        List<Integer> actualCoords = response.jsonPath().getList("coords");
        Assert.assertEquals(List.of(endX, endY), actualCoords);
    }

    @And("it should clean {int} dirt patches")
    public void checkDirtPatchesCleaned(int spotsCleaned) {
        int cleanedPatches = response.jsonPath().getInt("patches");
        Assert.assertEquals(spotsCleaned, cleanedPatches);
    }

}
