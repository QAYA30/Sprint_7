import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
public class CourierHandles {
    @Step("Send POST request to /api/v1/courier - Создание курьера\n")
    public Response addCourier(String login, String password, String firstName) {
        Courier jsonBody = new Courier(login, password, firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(jsonBody)
                        .when()
                        .post("/api/v1/courier");
        return response;
   }
    @Step("Send POST request to /api/v1/courier/login - Логин курьера в системе")
    public Response loginCourier(String login, String password) {
        Login jsonBody = new Login(login, password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(jsonBody)
                        .when()
                        .post("/api/v1/courier/login");
        return response;
    }
    @Step("Send DELETE request to /api/v1/courier/ - Удаление курьера\n")
    public void deleteCourier(String login, String password) {
        Response idCourier = loginCourier(login, password);
        Integer id = idCourier.then().extract().body().path("id");
        if (id != null) {
            Response response =
                    given()
                            .delete("/api/v1/courier/" + id);
        }
    }
}
