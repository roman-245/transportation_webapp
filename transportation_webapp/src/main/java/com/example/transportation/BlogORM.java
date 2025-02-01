package com.example.transportation;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@Entity
@Setter
@Getter
public class BlogORM {

    @Id
    private Long id;

    private String title;

//    @Lob
//    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Lob
    @Column(name = "maintext", columnDefinition = "TEXT")
    private String maintext;

    private String author;

    private LocalDate publishDate;

    private String image;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {return id;}

}
