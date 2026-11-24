package com.vixello.tickets.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "organizers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organizer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name ="tax_id", nullable = false)
    private String taxId;

    @Column(name = "business_email", nullable = false)
    private String businessEmail;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrganizerStatus status;

    @OneToMany(mappedBy = "organizer")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "organizer")
    private List<Event> organizedEvents = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organizer organizer = (Organizer) o;
        return Objects.equals(id, organizer.id) && Objects.equals(name, organizer.name) && Objects.equals(taxId, organizer.taxId) && Objects.equals(businessEmail, organizer.businessEmail) && Objects.equals(address, organizer.address) && status == organizer.status && Objects.equals(createdAt, organizer.createdAt) && Objects.equals(updatedAt, organizer.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, taxId, businessEmail, address, status, createdAt, updatedAt);
    }

    @CreatedDate
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;
}
