package com.quandoanh.priceformatter;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.quandoanh.priceformatter.config.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({AppConfig.class})
public abstract class BaseTest {

}
