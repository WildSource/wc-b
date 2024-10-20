package com.waifucomic.www.wc_b.comics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComicRepository extends CrudRepository<Comic, Long> {
    Optional<Comic> findByTitle(String title);
    Optional<List<Comic>> findByTitleContaining(String pattern);
}
