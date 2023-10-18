package vuedb.vuestagram.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String post_image;
    @CreatedDate
    private String date;
    private String content;
    private String filter;

    @PrePersist
    public void onPrePersist() {
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member member;

    @JsonBackReference
    @OneToMany(mappedBy = "post",
                fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostLike> postLikes;

    @JsonBackReference
    @OneToMany(mappedBy = "post",
                fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostComment> postComments;
}
