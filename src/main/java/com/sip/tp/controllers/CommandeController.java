package com.sip.tp.controllers;

import com.sip.tp.entities.Commande;
import com.sip.tp.entities.Livreur;
import com.sip.tp.repositories.CommandeRepository;
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
@RequestMapping("/commande/")
public class CommandeController {
    // Declaration directory (uploadDirectory) where we  save images
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images/articles";
    private final CommandeRepository commandeRepository;
    private final LivreurRepository livreurRepository;

    @Autowired
    public CommandeController(CommandeRepository commandeRepository, LivreurRepository livreurRepository) {
        this.commandeRepository = commandeRepository;
        this.livreurRepository = livreurRepository;
    }

    @GetMapping("list")
    public String listProviders(Model model) {
        List<Commande> la = (List<Commande>) commandeRepository.findAll();
        if (la.size() == 0) la = null;
        //model.addAttribute("articles", null);
        model.addAttribute("articles", la);
        return "article/listArticles";
    }

    @GetMapping("add")
    public String showAddArticleForm(Commande commande, Model model) {
        model.addAttribute("providers", livreurRepository.findAll());
        model.addAttribute("article", new Commande());
        return "article/addArticle";
    }

    @PostMapping("add")
    //@ResponseBody
    public String addArticle(@Valid Commande commande, BindingResult result,
                             @RequestParam(name = "providerId", required = false) Long p) {
        if (result.hasErrors()) {
            return "article/addArticle";
        }
        Livreur livreur = livreurRepository.findById(p).orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + p));
        commande.setProvider(livreur);


        commandeRepository.save(commande);
        return "redirect:list";

        //return article.getLabel() + " " +article.getPrice() + " " +p.toString();
    }

    @GetMapping("show/{id}")
    public String showArticleDetails(@PathVariable("id") long id, Model model) {
        Commande commande = commandeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

        model.addAttribute("article", commande);

        return "article/showArticle";
    }


    @GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {
        Commande artice = commandeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));
        commandeRepository.delete(artice);
        model.addAttribute("articles", commandeRepository.findAll());
        return "article/listArticles";
    }

    @GetMapping("edit/{id}")
    public String showArticleFormToUpdate(@PathVariable("id") long id, Model model) {
        Commande commande = commandeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));
        model.addAttribute("article", commande);
        model.addAttribute("providers", livreurRepository.findAll());
        model.addAttribute("idProvider", commande.getProvider().getId());

        return "article/updateArticle";
    }

    @PostMapping("edit/{id}")
    public String updateArticle(@PathVariable("id") long id,
                                @Valid Commande commande,
                                BindingResult result,
                                Model model,
                                @RequestParam(name = "providerId", required = false) Long p,
                                @RequestParam("files") MultipartFile[] files) {
        if (result.hasErrors()) {
            commande.setId(id);

            return "article/updateArticle";
        }

        Livreur livreur = livreurRepository.findById(p).orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + p));
        commande.setProvider(livreur);
        //upload image

        //pour stocker le nouveau name de fichierdans StringBuilder
        StringBuilder fileName = new StringBuilder();

        //recuperer le fichier qu'on a envoyer
        MultipartFile file = files[0];

        //donner le path jusqu'au nom de fichier(image)
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());

        //append l'original nom d'image dans notre file
        fileName.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandeRepository.save(commande);
        model.addAttribute("articles", commandeRepository.findAll());
        return "article/listArticles";
    }
}
