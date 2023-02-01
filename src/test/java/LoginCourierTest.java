import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;



@RunWith(Parameterized.class)
public class LoginCourierTest {
        private final String login;
        private final String password;
        private final int expectedCode;
        private final String expectedMessage;

        public LoginCourierTest(String login, String password, int expectedCode, String expectedMessage) {
            this.login = login;
            this.password = password;
            this.expectedCode = expectedCode;
            this.expectedMessage = expectedMessage;
        }

        @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2} {3}")
        public static Object[][] getCredentials() {
            return new Object[][]{
                    {"Aleks", "12345", 200, ""},
                    {"Aleks", "", 400, "Недостаточно данных для входа"},
                    {"", "12345", 400, "Недостаточно данных для входа"},
                    {"Aleks2", "12345", 404, "Учетная запись не найдена"}


            };
        }

        @Before
        public void setUp() {
            RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
            CourierHandles courierHandles = new CourierHandles();
            //Добавляем тестовую учетку для проверки успешного первого login
            courierHandles.addCourier("Aleks", "12345", "Aleksandr");
            //На всякий случай удаляем учетку для проверки несуществующего пользователя
            courierHandles.deleteCourier("Aleks2", "12345");
        }

        @Test
        @DisplayName("Тестирование метода логина курьера")
        public void testLoginCourier() {
            CourierHandles courierHandles = new CourierHandles();
            Response response = courierHandles.loginCourier(login, password);
            response.then().assertThat().statusCode(expectedCode);
            if (expectedCode == 200) {
                response.then().assertThat().body("id", notNullValue());
            } else
                response.then().assertThat().body("message", Matchers.equalTo(expectedMessage));
        }
    }





