package org.lectures.restapi.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.lectures.restapi.user.User;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    @Size(min = 1, max = 100)
    private String title;
    @Size(min = 1)
    private String body;

    @ManyToOne
    @JsonIgnore
    private User user;

    protected Post() {}

    public Post(
        Long id,
        String title,
        String body,
        User user
    ) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
