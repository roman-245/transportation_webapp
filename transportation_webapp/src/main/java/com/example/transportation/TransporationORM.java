package com.example.transportation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Entity
public class TransporationORM {
    @Id
    private Long id;
    @Getter
    private String name;
    @Getter
    private String content;
    @Getter
    private String dispatch_city;
    @Getter
    private LocalDate dispatch_date;
    @Getter
    private String arrival_city;
    @Getter
    private LocalDate arrival_date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
}
