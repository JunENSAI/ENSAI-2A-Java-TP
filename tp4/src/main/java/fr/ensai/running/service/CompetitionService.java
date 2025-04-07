package fr.ensai.running.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ensai.running.model.Athlete;
import fr.ensai.running.model.Competition;
import fr.ensai.running.model.Registration;
import fr.ensai.running.repository.AthleteRepository;
import fr.ensai.running.repository.CompetitionRepository;
import fr.ensai.running.repository.RegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class CompetitionService {

    private static final Logger log = LoggerFactory.getLogger(CompetitionService.class);

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    /**
     * Find a Competition by id
     *
     * @return Competition or null if not found
     */
    public Competition findById(Long id) {
        // Return null if not found to match previous behaviour,
        // though throwing an exception or returning Optional might be better design.
        return competitionRepository.findById(id).orElse(null);
    }

    /**
     * List of all Competitions
     */
    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    /**
     * Create or Update a Competition
     */
    public Competition save(Competition competition) {
        return competitionRepository.save(competition);
    }


    public void deleteById(Long id) {
        competitionRepository.deleteById(id);
        log.warn("Competition {} deleted", id);
    }


    /**
     * Finds all athletes registered for a given competition.
     *
     * @param idCompetition The ID of the competition.
     * @return A list of registered Athlete objects, or an empty list if none.
     */
    public List<Athlete> findRegisteredAthletes(Long idCompetition) {
        List<Long> athleteIds = registrationRepository.findAthleteIdsByCompetitionId(idCompetition);
        if (athleteIds.isEmpty()) {
            return Collections.emptyList();
        }
        return athleteRepository.findAllById(athleteIds);
    }

    /**
     * Deletes the registration linking a specific athlete to a specific competition.
     *
     * @param idCompetition The ID of the competition.
     * @param idAthlete     The ID of the athlete.
     */
    @Transactional // Add Transactional for delete operations
    public void deleteRegistration(Long idCompetition, Long idAthlete) {
        Optional<Registration> registrationOpt = registrationRepository.findByAthleteIdAndCompetitionIdCompetition(idAthlete, idCompetition);

        if (registrationOpt.isPresent()) {
            registrationRepository.delete(registrationOpt.get());
            log.warn("Registration deleted for Athlete {} in Competition {}", idAthlete, idCompetition);
        } else {
            log.warn("Could not find Registration to delete for Athlete {} in Competition {}", idAthlete, idCompetition);
        }
    }

    /**
     * Finds athletes who are NOT registered for the given competition.
     * @param idCompetition The ID of the competition.
     * @return A list of eligible athletes.
     */
    public List<Athlete> findEligibleAthletes(Long idCompetition) {
        log.debug("--- Finding eligible athletes for competition ID: {} ---", idCompetition);

        List<Long> registeredAthleteIds = registrationRepository.findAthleteIdsByCompetitionId(idCompetition);
        log.debug("Registered athlete IDs found: {}", registeredAthleteIds); // Log des IDs inscrits

        List<Athlete> allAthletes = athleteRepository.findAll();
        log.debug("Total athletes fetched from DB ({}): {}", allAthletes.size(), allAthletes.stream().map(a -> a.getId() + ":" + a.getFirstName()).collect(Collectors.toList()));

        List<Athlete> eligibleAthletesResult;
        if (registeredAthleteIds.isEmpty()) {
             log.debug("No athletes registered, all are eligible.");
            eligibleAthletesResult = allAthletes;
        } else {
             log.debug("Filtering all athletes against registered IDs...");
            eligibleAthletesResult = allAthletes.stream()
                    .filter(athlete -> {
                        boolean isRegistered = registeredAthleteIds.contains(athlete.getId());
                        return !isRegistered;
                     })
                    .collect(Collectors.toList());
        }
        log.debug("Eligible athletes calculated ({}): {}", eligibleAthletesResult.size(), eligibleAthletesResult.stream().map(a -> a.getId() + ":" + a.getFirstName()).collect(Collectors.toList()));
        log.debug("--- End finding eligible athletes ---");
        return eligibleAthletesResult;
    }

    /**
     * Registers a specific athlete for a specific competition.
     * Creates a new Registration record.
     * @param competitionId The ID of the competition.
     * @param athleteId The ID of the athlete.
     * @return The created Registration object.
     * @throws EntityNotFoundException if competition or athlete is not found.
     * @throws IllegalStateException if athlete is already registered or competition is full.
     */
    @Transactional // Important for saving new entities
    public Registration registerAthleteToCompetition(Long competitionId, Long athleteId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with ID: " + competitionId));

        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found with ID: " + athleteId));

        boolean alreadyRegistered = registrationRepository.findByAthleteIdAndCompetitionIdCompetition(athleteId, competitionId).isPresent();
        if (alreadyRegistered) {
            log.warn("Athlete {} already registered for competition {}", athleteId, competitionId);
            throw new IllegalStateException("Athlete is already registered for this competition.");
        }

        List<Long> registeredIds = registrationRepository.findAthleteIdsByCompetitionId(competitionId);
        if (registeredIds.size() >= competition.getMaxAthletes()) {
             log.warn("Competition {} is full. Cannot register athlete {}", competitionId, athleteId);
             throw new IllegalStateException("Competition is full. Cannot register more athletes.");
        }

        Registration newRegistration = new Registration();
        newRegistration.setAthlete(athlete);
        newRegistration.setCompetition(competition);
        newRegistration.setRegistrationDate(LocalDate.now()); // Set current date

        Registration savedRegistration = registrationRepository.save(newRegistration);
        log.info("Athlete {} registered successfully for competition {}", athleteId, competitionId);
        return savedRegistration;
    }

}