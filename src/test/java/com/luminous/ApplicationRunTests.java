package com.luminous;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/18 14:27
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ApplicationRunTests {

    @Test
    public void HelloWorld() {
        System.out.println("hello world!");
    }


}
