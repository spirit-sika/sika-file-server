package cc.sika.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cc.sika.file.mapper")
public class SikaFileServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SikaFileServerApplication.class, args);
	}

}
