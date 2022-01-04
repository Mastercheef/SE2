package org.hbrs.se2.project.coll;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CollApplication.class)
class SpringBootApplicationTest {

    @Test
    void contextLoads() {
        //Testing CollApplication
    }

    @Test
    public void main() {
        CollApplication.main(new String[] {});
    }

}
