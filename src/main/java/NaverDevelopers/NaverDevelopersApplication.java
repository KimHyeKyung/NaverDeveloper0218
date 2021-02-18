package NaverDevelopers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing//시간을 사용하기위해서 꼭 넣어준다.
@SpringBootApplication
public class NaverDevelopersApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaverDevelopersApplication.class, args);
	}

}
