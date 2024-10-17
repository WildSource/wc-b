package com.waifucomic.www.wc_b.comics;

import org.springframework.stereotype.Component;

@Component
public class Comic {
    private Long id;
    private String title;

    public Comic() {
    }

    public Comic(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
