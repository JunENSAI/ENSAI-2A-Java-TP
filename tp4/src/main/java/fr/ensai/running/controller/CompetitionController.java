package fr.ensai.running.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ensai.running.service.CompetitionService;


@Controller
@RequestMapping("/competition")
public class CompetitionController {

    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    /**
     * Affiche toutes les compétitions.
     * Cet endpoint redirige vers la vue "allCompetitions.html".
     */
    @GetMapping({"", "/"})
    public String findAllCompetition(Model model) {
        model.addAttribute("competitions", competitionService.findAll());
        return "allCompetitions";
    }

}


