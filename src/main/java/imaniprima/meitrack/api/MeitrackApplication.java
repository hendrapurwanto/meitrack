package imaniprima.meitrack.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class MeitrackApplication {

	public static void main(String[] args) {

		SpringApplication.run(MeitrackApplication.class, args);
		log.warn("--Application Meitrack is Started--");
	}

}
