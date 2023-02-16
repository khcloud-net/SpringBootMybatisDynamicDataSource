package net.khcloud.study;

import net.khcloud.study.dao.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class SpringBootMybatisWithRedisApplicationTests {

//	@LocalServerPort
	@Value("${server.port}")
	private int port;

	@Autowired
	private ServletWebServerApplicationContext webServerAppCtxt;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void test() {
		long productId = 1;

		port = webServerAppCtxt.getWebServer().getPort();

		Product product = restTemplate.getForObject("http://localhost:" + port + "/product/" + productId, Product.class);
		assertThat(product.getPrice()).isEqualTo(200);

		Product newProduct = new Product();
		long newPrice = new Random().nextLong();
		newProduct.setName("new name");
		newProduct.setPrice(newPrice);
		restTemplate.put("http://localhost:" + port + "/product/" + productId, newProduct);

		Product testProduct = restTemplate.getForObject("http://localhost:" + port + "/product/" + productId, Product.class);
		assertThat(testProduct.getPrice()).isEqualTo(newPrice);
	}
}
