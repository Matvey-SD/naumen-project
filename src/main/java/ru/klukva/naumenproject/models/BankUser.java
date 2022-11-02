package ru.klukva.naumenproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class BankUser implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String phoneNumber;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "user"
    )
    private final List<BankAccount> accounts = new ArrayList<>();

    @Override
    public String getFullName() {
        return lastName + " " + firstName + " " + patronymic;
    }
}