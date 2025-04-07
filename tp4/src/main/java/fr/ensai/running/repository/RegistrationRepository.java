package fr.ensai.running.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; 

import fr.ensai.running.model.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Optional<Registration> findByAthleteIdAndCompetitionIdCompetition(Long athleteId, Long competitionId);
    @Query("SELECT r.athlete.id FROM Registration r WHERE r.competition.id = :idCompetition")
    List<Long> findAthleteIdsByCompetitionId(@Param("idCompetition") Long idCompetition);

}