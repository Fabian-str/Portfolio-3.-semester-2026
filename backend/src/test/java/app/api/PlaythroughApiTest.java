package app.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class PlaythroughApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7071;
    }

    @Test
    void getAllPlaythroughsShouldReturn200() {
        given()
            .when()
            .get("/api/playthroughs")
            .then()
            .statusCode(200)
            .body("$", isA(List.class));
    }

    @Test
    void getPlaythroughByIdShouldReturn404WhenNotFound() {
        given()
            .when()
            .get("/api/playthroughs/999999")
            .then()
            .statusCode(404)
            .body("error", is("Playthrough not found"));
    }

    @Test
    void createPlaythroughShouldReturn201() {
        String requestBody = """
            {
              "name": "Rest Assured Test Run",
              "game": "RED"
            }
            """;

        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/api/playthroughs")
            .then()
            .statusCode(201)
            .body("name", is("Rest Assured Test Run"))
            .body("game", is("RED"));
    }

    @Test
    void updatePlaythroughShouldReturn200() {
        String createBody = """
            {
              "name": "Before Update",
              "game": "RED"
            }
            """;

        int id =
            given()
                .contentType("application/json")
                .body(createBody)
                .when()
                .post("/api/playthroughs")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        String updateBody = """
            {
              "name": "After Update",
              "game": "BLUE"
            }
            """;

        given()
            .contentType("application/json")
            .body(updateBody)
            .when()
            .put("/api/playthroughs/" + id)
            .then()
            .statusCode(200)
            .body("name", is("After Update"))
            .body("game", is("BLUE"));
    }

    @Test
    void deletePlaythroughShouldReturn204() {
        String createBody = """
            {
              "name": "Delete Test Run",
              "game": "RED"
            }
            """;

        int id =
            given()
                .contentType("application/json")
                .body(createBody)
                .when()
                .post("/api/playthroughs")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        given()
            .when()
            .delete("/api/playthroughs/" + id)
            .then()
            .statusCode(204);

        given()
            .when()
            .get("/api/playthroughs/" + id)
            .then()
            .statusCode(404)
            .body("error", is("Playthrough not found"));
    }
}