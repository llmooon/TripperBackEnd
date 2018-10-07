package org.soma.tripper.place.repository;

import org.soma.tripper.place.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search,Integer> {
    Search findByName(String name);
    Optional<List<Search>> findByNameContains(String name);
}
