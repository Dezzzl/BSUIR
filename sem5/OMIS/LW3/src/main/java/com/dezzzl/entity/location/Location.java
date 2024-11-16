package com.dezzzl.entity.location;

import com.dezzzl.entity.event.Event;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String geoLocation;

    @ElementCollection
    @CollectionTable(name = "location_photo_urls", joinColumns = @JoinColumn(name = "location_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls;

    @OneToOne
    @JoinColumn(name = "nearest_event_id")
    private Event nearestEvent;

    @ElementCollection
    @CollectionTable(name = "location_review_ids", joinColumns = @JoinColumn(name = "location_id"))
    @Column(name = "review_id")
    private List<Integer> reviewIds;

    @ElementCollection
    @CollectionTable(name = "location_administrator_emails", joinColumns = @JoinColumn(name = "location_id"))
    @Column(name = "administrator_email")
    private List<String> administratorEmails;
}

