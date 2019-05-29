package com.mitrais.javabootcamp.charles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.javabootcamp.charles.entity.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Integer> {

}
