package fr.ensai.running.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ensai.running.model.Athlete;
import fr.ensai.running.model.Competition;
import fr.ensai.running.service.CompetitionService;
import jakarta.persistence.EntityNotFoundException;



@Controller
@RequestMapping("/competition") 
public class CompetitionController {

    private static final Logger log = LoggerFactory.getLogger(CompetitionController.class);

    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }


    @GetMapping("/{id}/athletes")
    public String getCompetitionAthletes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Competition competition = competitionService.findById(id);
        if (competition == null) {
            redirectAttributes.addFlashAttribute("error", "Competition not found.");
            return "redirect:/competition";
        }

        List<Athlete> registeredAthletes = competitionService.findRegisteredAthletes(id);
        List<Athlete> eligibleAthletes = competitionService.findEligibleAthletes(id);



        model.addAttribute("competition", competition);
        model.addAttribute("athletes", registeredAthletes);
        model.addAttribute("eligibleAthletes", eligibleAthletes);

        return "competitionAthletes";
    }
    /**
     * Handles GET requests to /competition or /competition/
     * Displays the list of all competitions.
     */
    @GetMapping({"", "/"})
    public String findAllCompetition(Model model) {
        List<Competition> competitions = competitionService.findAll();
        model.addAttribute("competitions", competitions);
        return "allCompetitions";
    }


    /**
     * Handles GET requests to /competition/delete/{id_comp}/athlete/{id_ath}
     * Deletes the registration linking a specific athlete to a specific competition.
     * @param id_comp The ID of the competition (from the URL path).
     * @param id_ath The ID of the athlete (from the URL path).
     * @param redirectAttributes Used to pass messages during redirect (optional).
     * @return A redirect instruction to refresh the competition's athlete list.
     */
    @GetMapping("/delete/{id_comp}/athlete/{id_ath}")
    public String deleteCompetitionRegistration(@PathVariable Long id_comp,
                                                @PathVariable Long id_ath,
                                                RedirectAttributes redirectAttributes) {
        competitionService.deleteRegistration(id_comp, id_ath);
        return "redirect:/competition/" + id_comp + "/athletes";
    }

    /**
     * Handles GET requests to /competition/delete/{id}
     * Deletes an entire competition by its ID. (This method likely existed before)
     * @param id The ID of the competition to delete (from the URL path).
     * @return A redirect instruction to the main competition list.
     */
    @GetMapping("/delete/{id}")
    public String deleteCompetitionById(@PathVariable(value = "id") long id  ) {
        Competition competition = competitionService.findById(id);
        if (competition != null) {
            try {

                List<Athlete> registeredAthletes = competitionService.findRegisteredAthletes(id);
                for (Athlete athlete : registeredAthletes) {
                    competitionService.deleteRegistration(id, athlete.getId());
                }
                competitionService.deleteById(id);
            } catch (Exception e) {
            }
        } else {
            log.warn("Competition with ID {} not found for deletion", id);
            return "redirect:/competition";
        }
        return "redirect:/competition";
    }

    /**
     * Handles the POST request to register an athlete for a competition.
     * @param competitionId ID of the competition (from hidden form field).
     * @param athleteId ID of the athlete to register (from dropdown).
     * @param redirectAttributes Used to pass success/error messages back.
     * @return Redirects back to the competition's athlete list page.
     */
    @PostMapping("/register")
    public String registerAthleteCompetition(@RequestParam Long competitionId,
                                             @RequestParam Long athleteId,
                                             RedirectAttributes redirectAttributes) {
        try {
            competitionService.registerAthleteToCompetition(competitionId, athleteId);
            redirectAttributes.addFlashAttribute("message", "Athlete registered successfully!");
        } catch (EntityNotFoundException | IllegalStateException e) {
            log.error("Registration failed: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during registration", e);
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred during registration.");
        }
        return "redirect:/competition/" + competitionId + "/athletes";
    }
}