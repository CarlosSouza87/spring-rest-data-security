package br.edu.fatecsjc.lgnspringapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "organizations")
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "organizationsidgen", sequenceName = "organizations_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizationsidgen")
    private Long id;
    private String name;
    private String institutionName;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;

    public Organization(Long id) {
        this.id = id;
    }
}
