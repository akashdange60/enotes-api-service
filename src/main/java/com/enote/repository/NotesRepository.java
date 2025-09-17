package com.enote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enote.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>{

}
