package com.example.demo.models;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "jornalisti")
public class Jornalisti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name, fam;
    private Double cena;
    private Integer kolvastat;
    @Temporal(TemporalType.DATE)
    private Date denroj;

    private int views;

    public Jornalisti(String name, String fam, Integer kolvastat, Date denroj, Double cena) {
        this.name = name;
        this.fam = fam;
        this.kolvastat = kolvastat;
        this.denroj = denroj;
        this.cena = cena;
    }

    public Jornalisti() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }


    public Date getDenroj() {
        return denroj;
    }

    public void setDenroj(Date denroj) {
        this.denroj = denroj;
    }


    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Integer getKolvastat() {
        return kolvastat;
    }

    public void setKolvastat(Integer kolvastat) {
        this.kolvastat = kolvastat;
    }
}
