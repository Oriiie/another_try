package sova.another_try.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "NotNull")
    @Size(min = 2, max= 40, message = "Invalid format")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "NotNull")
    @Size(min = 2, max= 40, message = "Invalid format")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "NotNull")
    @Size(min = 2, max= 40, message = "Invalid format")
    @Column(name = "patronymic")
    private String patronymic;

    @NotNull(message = "NotNull")
    @Size(min = 10, max = 15, message = "Invalid format")
    @Column(name = "student_phone")
    private String phone;

    @Size(min = 10, max = 100, message = "Invalid format")
    @Column(name = "student_address")
    private String address;

    @Email(message = "Invalid email")
    @Column(name = "email")
    private String email;

    @Range(min = 15, max = 30, message = "Invalid format")
    @Column(name = "age")
    private int age;

    @Size(min = 8, max = 15, message = "Invalid format")
    @Column(name = "student_card_id")
    private String studentCardId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id",nullable = false)
    @JsonIgnore
    private Group group;
}
