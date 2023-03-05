package com.example.arniepanblog.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    private String createdAt;

    private String modifiedAt;

    private String photos;

    private String uploadDir;

    private Boolean hasEditDeletePermission;

    private Boolean hasReadPermission;

    private String publishMode;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;
        return "/post-image/" + id + "/" + photos;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", body='" + body + "'" +
                ", createdAt='" + createdAt + "*" +
                ", updatedAt='" + modifiedAt + "'" +
                ", photos='" + photos + "'" +
                "}";
    }
}
