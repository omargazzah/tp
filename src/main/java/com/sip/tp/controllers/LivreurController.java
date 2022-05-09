package com.sip.tp.controllers;

import com.sip.tp.entities.Livreur;
import com.sip.tp.repositories.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@RequestMapping("/provider/")
public class LivreurController {
    public static String uploadDirectorProviderLogo = System.getProperty("user.dir") + "/src/main/resources/static/images/providers";
    private final LivreurRepository livreurRepository;

    @Autowired
    public LivreurController(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }

    @GetMapping("list")
    //@ResponseBody
    public String listProviders(Model model) {
        List<Livreur> lp = (List<Livreur>) livreurRepository.findAll();
        if (lp.size() == 0) lp = null;
        model.addAttribute("providers", lp);

        return "provider/listProviders";

        //List<Provider> lp =(List<Provider>) providerRepository.findAll();
        //System.out.println(lp);

        //return "Nombre de fournisseur = " + lp.size();
    }

    @GetMapping("add")
    public String showAddProviderForm(Model model) {
        Livreur livreur = new Livreur();// object dont la valeur desattributs par defaut
        model.addAttribute("provider", livreur);
        return "provider/addProvider";
    }

    @PostMapping("add")
    public String addProvider(@Valid Livreur livreur,
                              BindingResult result,
                              @RequestParam("files") MultipartFile[] files) {
        if (result.hasErrors()) {
            return "provider/addProvider";
        }
        //upload Logo

        StringBuilder fileName = new StringBuilder();
        MultipartFile file = files[0];
        Path fileNameAndPath = Paths.get(uploadDirectorProviderLogo, file.getOriginalFilename());
        fileName.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        livreur.setLogo(fileName.toString());
        // fin upload logo
        livreurRepository.save(livreur);
        return "redirect:list";
    }


    @GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {


        //long id2 = 100L;

        Livreur livreur = livreurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

        System.out.println("suite du programme...");

        livreurRepository.delete(livreur);

 /*model.addAttribute("providers",
providerRepository.findAll());
 return "provider/listProviders";*/

        return "redirect:../list";
    }


    @GetMapping("edit/{id}")

    public String showProviderFormToUpdate(@PathVariable("id") long id, Model model) {
        Livreur livreur = livreurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

        model.addAttribute("provider", livreur);

        return "provider/updateProvider";
    }

    @PostMapping("update")
    public String updateProvider(@Valid Livreur livreur,
                                 BindingResult result,
                                 Model model,
                                 @RequestParam("files") MultipartFile[] files) {


        //upload Logo

        //pour stocker le nouveau name de fichierdans StringBuilder
        StringBuilder fileName = new StringBuilder();

        //recuperer le fichier qu'on a envoyer
        MultipartFile file = files[0];

        //donner le path jusqu'au nom de fichier(logo)
        Path fileNameAndPath = Paths.get(uploadDirectorProviderLogo, file.getOriginalFilename());

        //append l'original nom d'image dans notre file
        fileName.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        livreur.setLogo(fileName.toString());
        // fin upload logo
        livreurRepository.save(livreur);


        return "redirect:list";

    }
}

