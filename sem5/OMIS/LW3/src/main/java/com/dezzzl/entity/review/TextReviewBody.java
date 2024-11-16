package com.dezzzl.entity.review;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("TEXT")
public class TextReviewBody extends ReviewBody {
    private String text;
}

