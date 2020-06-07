package sova.another_try.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Название предмета не может быть пустым")
    @Size(min = 2, max = 40, message = "Название предмета должно содержать от 2 до 40 символов")
    @Column(name = "subject_name")
    private String subjectName;

    @Range(min = 0, max = 100, message = "Оценка должна лежать в пределах от 0 до 100")
    @Column(name = "subject_mark")
    private String subjectMark;

    @ManyToOne(fetch = FetchType.LAZY,optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id",nullable = false)
    @JsonIgnore
    private Student student;
}
