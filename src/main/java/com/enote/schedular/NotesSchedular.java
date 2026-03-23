package com.enote.schedular;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.enote.entity.Notes;
import com.enote.repository.NotesRepository;

@Component
public class NotesSchedular {
	
	@Autowired
	private NotesRepository notesRepo;
	
	
	//@Scheduled(fixedRate = 1000)			//1000 = every 1 sec 
	//@Scheduled(cron = "* * * ? * *")						//every second
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNotesSchedular()
	{
		// 20 Nov - 14 Nov  - 7 Days
		
		LocalDateTime cutOfDate = LocalDateTime.now().minusDays(7);
		List<Notes> deleteNotes=notesRepo.findAllByIsDeletedAndDeletedOnBefore(true,cutOfDate);
		notesRepo.deleteAll(deleteNotes);
	}
	
	
	
	
	

}
