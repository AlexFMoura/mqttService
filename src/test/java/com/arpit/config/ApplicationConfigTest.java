package com.arpit.config;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class ApplicationConfigTest {

	@BeforeClass
	public static void setSystemProperty() {
		Properties properties = System.getProperties();
		properties.setProperty("spring.profiles.active", "dev");
	}

	@Test
	public void testContext() throws Exception {
		assertTrue(true);
	}
}
