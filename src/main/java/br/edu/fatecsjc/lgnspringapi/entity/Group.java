package br.edu.fatecsjc.lgnspringapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "groupsidgen", sequenceName = "groups_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupsidgen")
    private Long id;
    private String name;
    @OneToMany(mappedBy="group", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Member> members;
    @ManyToOne
    @JoinColumn(name="organization_id")
    private Organization organization;
}
