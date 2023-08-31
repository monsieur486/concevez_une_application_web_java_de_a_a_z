package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.utils.FakeData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymybuddyApplicationTests {

    public static void showEmail() {
        for (int i = 0; i < 100; i++) {
            String email = FakeData.getEmail();
            System.out.println(email);
        }
    }

    @BeforeAll
    static void setUp() {
        showEmail();
    }

	@Test
	void contextLoads() {
	}

}
