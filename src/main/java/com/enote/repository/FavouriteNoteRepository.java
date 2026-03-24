package com.enote.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.enote.entity.FavouriteNote;

public interface FavouriteNoteRepository extends JpaRepository<FavouriteNote, Integer>{

         List<FavouriteNote> findByUserId(int userId);

}
