package com.cdbros.openlog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenlogApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

    @Test
    void shouldExecuteMainAndReturn() {
        OpenlogApplication.main(new String[]{});
        Assertions.assertTrue(true);
    }

}
