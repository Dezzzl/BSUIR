package com.dezzzl.entity.event;

import com.dezzzl.entity.location.Location;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private LocalDateTime dateTime;

    private Float rating;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ElementCollection
    @CollectionTable(name="event_review_ids", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "review_id")
    private List<Integer> reviewIds;
}
