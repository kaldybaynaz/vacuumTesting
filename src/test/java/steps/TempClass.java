package steps;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TempClass {
  //  public static void main(String[] args) {

        Response crumbResponse = given()
                .get("http://localhost:8080/crumbIssuer/api/json");

            String crumbField = crumbResponse.jsonPath().getString("crumbRequestField");
            String crumbValue = crumbResponse.jsonPath().getString("crumb");

      //  System.out.println(crumbField);
      //  System.out.println(crumbValue);
      /*


// Use the crumb in the POST request
        response = given()
                .header("Content-Type", "application/json")
                .header(crumbField, crumbValue)
                .body(payload)
                .when()
                .post("http://localhost:8080/v1/cleaning-sessions");


        Response response;

        //RestAssured.baseURI = "http://localhost:8080/v1";
String payload="{\n" +
        "  \"roomSize\" : [5, 5],\n" +
        "  \"coords\" : [1, 2],\n" +
        "  \"patches\" : [\n" +
        "    [1, 0],\n" +
        "    [2, 2],\n" +
        "    [2, 3]\n" +
        "  ],\n" +
        "  \"instructions\" : \"NNESEESWNWW\"\n" +
        "}";
        response = given()
                //.contentType(ContentType.JSON)
               // .header("Content-Type", "application/json")
                .header("User-Agent", "PostmanRuntime/7.26.8")
                .header("Accept", "application/json")
                .body(payload)
                //.when()
                .post("http://localhost:8080/v1/cleaning-sessions");

        System.out.println(response.statusCode());
          response.prettyPrint();
            */


    }




//}
