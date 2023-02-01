import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class ShowOrderListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка получение списка заказов")
    public void testGetListOrder() {
        OrderHandles ordersHandles = new OrderHandles();
        Response response = ordersHandles.getListOrder();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body(notNullValue());
    }
}