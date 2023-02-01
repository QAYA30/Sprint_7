import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
        private final String firstName;
        private final String lastName;
        private final String address;
        private final String metroStation;
        private final String phone;
        private final int rentTime;
        private final String deliveryDate;
        private final String comment;
        private final String[] color;

        public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.metroStation = metroStation;
            this.phone = phone;
            this.rentTime = rentTime;
            this.deliveryDate = deliveryDate;
            this.comment = comment;
            this.color = color;
        }

        @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2} {3}")
        public static Object[][] getCredentials() {
            return new Object[][]{
                    {"Aleksandr", "Ivanov", "Gogolya", "4", "+7 995 503 33 33", 2, "2022", "Faster", new String[]{"BLACK", "GREY"}},
                    {"Aleksandr", "Ivanov", "Gogolya", "4", "+7 995 503 33 33", 2, "2022", "Faster", new String[]{"BLACK"}},
                    {"Aleksandr", "Ivanov", "Gogolya", "4", "+7 995 503 33 33", 2, "2022", "Faster", new String[]{"GREY"}},
                    {"Aleksandr", "Ivanov", "Gogolya", "4", "+7 995 503 33 33", 2, "2022", "Faster", new String[]{""}},
            };
        }

        @Before
        public void setUp() {
            RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        }

        @Test
        @DisplayName("Создание заказа")
        public void createOrderTest() {
            OrderHandles ordersHandles = new OrderHandles();
            Response response = ordersHandles.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
            response.then().assertThat().statusCode(201);
            response.then().assertThat().body("track", notNullValue());
        }
    }




