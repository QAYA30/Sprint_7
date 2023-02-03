import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        CourierHandles courierHandles = new CourierHandles();
        courierHandles.addCourier("Aleks", "12345", "Aleksandr");
        courierHandles.deleteCourier("Aleks", "12345");
    }

    @Test
    @DisplayName ("Проверяет позитивную попытку создать пользователя")
    public void createCourierTestPositive() {
        CourierHandles courierHandles = new CourierHandles();
        Response response = courierHandles.addCourier("Aleks", "12345", "Aleksandr");
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName ("Негативные проверки метода создания курьера")
    public void createCourierTestNegative() {
        CourierHandles courierHandles = new CourierHandles();
        Response response = courierHandles.addCourier("Aleks1", "12345", "Aleksandr1");
        response.then().assertThat().statusCode(409);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        Response response1 = courierHandles.addCourier("Aleks2", "", "Aleksandr2");
        response1.then().assertThat().statusCode(400);
        response1.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourier() {
        CourierHandles courierHandles = new CourierHandles();
        courierHandles.deleteCourier("Aleks", "12345");
    }

}
