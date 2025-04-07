package fr.ensai.running.model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_competition")
    private Long idCompetition;

    private String designation;
    private String city;

    @Column(name = "event_date")
    private LocalDate eventDate;

    private float distance;

    @Column(name = "max_athletes")
    private int maxAthletes;

    // Constructors
    public Competition() {
    }

    public Competition(String designation, String city, LocalDate eventDate, float distance, int maxAthletes) {
        this.designation = designation;
        this.city = city;
        this.eventDate = eventDate;
        this.distance = distance;
        this.maxAthletes = maxAthletes;
    }

    // Getters and Setters
    public Long getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(Long idCompetition) {
        this.idCompetition = idCompetition;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getMaxAthletes() {
        return maxAthletes;
    }

    public void setMaxAthletes(int maxAthletes) {
        this.maxAthletes = maxAthletes;
    }
}

