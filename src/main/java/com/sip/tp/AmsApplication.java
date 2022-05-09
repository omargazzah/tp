package com.sip.tp;

import com.sip.tp.controllers.CommandeController;
import com.sip.tp.controllers.LivreurController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;



@SpringBootApplication
public class AmsApplication {

    public static void main(String[] args) {
        // to enter the uploadDirectory in ArticleController and put this new directory in her place
        new File(CommandeController.uploadDirectory).mkdir();
        new File(LivreurController.uploadDirectorProviderLogo).mkdir();
        SpringApplication.run(AmsApplication.class, args);
    }

}
