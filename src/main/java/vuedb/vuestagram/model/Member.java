package vuedb.vuestagram.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userid;
    private String name;
    private String user_image;
    private String password;
    @CreatedDate
    private String regDate;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @PrePersist
    public void onPrePersist() {
        this.regDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    @JsonBackReference
    @OneToMany(mappedBy = "member",
                fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

}
