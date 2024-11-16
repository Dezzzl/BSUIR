package com.dezzzl.entity.review;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "body_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ReviewBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}

