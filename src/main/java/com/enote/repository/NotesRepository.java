package com.enote.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.enote.entity.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer>{

	//Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);  
	//Here two properties are add so field name should be match properly in Notes.class
	//findByCreatedBy check ID and IsDeleted will check its status like true or false simultaneously.

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);

	//Below custom method is for schedular
	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOfDate);

}
