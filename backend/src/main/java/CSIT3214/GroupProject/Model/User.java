package CSIT3214.GroupProject.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_id", referencedColumnName = "id")
    private Membership membership;
}