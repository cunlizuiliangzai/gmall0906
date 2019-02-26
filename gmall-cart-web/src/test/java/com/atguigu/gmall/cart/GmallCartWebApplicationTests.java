package com.atguigu.gmall.cart;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallCartWebApplicationTests {

	@Test
	public void contextLoads() {

		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DATE,1);
		Date time = instance.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String format = sdf.format(time);
		System.out.println(format);
	}

}

