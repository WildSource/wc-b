package com.waifucomic.www.wc_b.comics;


import jakarta.persistence.*;

import java.util.List;

@Entity
//Prod @Table(name = "comic", schema = "wc")
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String path;
    @ElementCollection
    private List<String> paths;

    public Comic() {
    }

    public Comic(String title) {
        this.title = title;
    }

    public Comic(String title, String path) {
        this.title = title;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", paths=" + paths +
                '}';
    }
}
