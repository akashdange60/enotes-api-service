package com.enote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enote.dto.FavouriteNoteDto;
import com.enote.dto.NotesDto;
import com.enote.dto.NotesResponse;
import com.enote.entity.FileDetails;
import com.enote.service.NotesService;
import com.enote.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestParam String notes,
			@RequestParam(required = false) MultipartFile file) throws Exception
	{
		
		Boolean saveNotes = notesService.saveNotes(notes,file);
		if (saveNotes) {
			return CommonUtil.createBuildResponseMessage("Notes Save successfully", HttpStatus.CREATED);
		}
		return CommonUtil.createBuildResponseMessage("Notes NOT Save", HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception
	{
		FileDetails fileDetails=notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		
		HttpHeaders header=new HttpHeaders();	
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		header.setContentType(MediaType.parseMediaType(contentType));
		header.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		
		return ResponseEntity.ok().headers(header).body(data);	
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes()
	{
		List<NotesDto> notes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);	
	}
	
	@GetMapping("/user-notes")
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name = "pageNo" , defaultValue = "0") Integer pageNo ,
			@RequestParam(name = "pageSize" , defaultValue = "10") Integer pageSize
		)
	{
		Integer userId=2;
		
		NotesResponse notes = notesService.getAllNotesByUser(userId,pageNo,pageSize);
		
//		if (CollectionUtils.isEmpty(notes)) {
//			return ResponseEntity.noContent().build();
//		}
		
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);	
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception
	{	
		notesService.softDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delete Successfully", HttpStatus.OK);	
	}

	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception
	{	
		notesService.restoreNotes(id);
		return CommonUtil.createBuildResponseMessage("Notes Restore successfully... !", HttpStatus.OK);	
	}
	
	
	@GetMapping("/recycle-bin")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception
	{	
		Integer userId=2;
	    List<NotesDto>  notes =notesService.getUserRecycleBinNotes(userId);
	    if (CollectionUtils.isEmpty(notes)) 
	    {	
	    	return CommonUtil.createBuildResponseMessage("Currently Notes NOT available In Recycle Bin...", HttpStatus.OK);
	    }
	    
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);	
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception
	{	
		notesService.hardDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delete Successfully", HttpStatus.OK);	
	}
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> emptyRecycleBin() throws Exception
	{	
		Integer userId=2;
		notesService.emptyRecycleBin(userId);
		return CommonUtil.createBuildResponseMessage("Delete Successfully", HttpStatus.OK);	
	}
	
	@GetMapping("/fav/{noteId}")
	public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId) throws Exception
	{	
		notesService.favouriteNotes(noteId);
		return CommonUtil.createBuildResponseMessage("Notes Added As Favourite...", HttpStatus.CREATED);	
	}
	
	
	@DeleteMapping("/un-fav/{favNoteId}")
	public ResponseEntity<?> unFavouriteNotes(@PathVariable Integer favNoteId) throws Exception
	{	
		notesService.unFavouriteNotes(favNoteId);
		return CommonUtil.createBuildResponseMessage("Remove Favourite Notes Successfully", HttpStatus.OK);	
	}
	
	@GetMapping("/fav-note")
	public ResponseEntity<?> getUserFavouriteNotes() throws Exception
	{	
		List<FavouriteNoteDto> userFavouriteNotes = notesService.getUserFavouriteNotes();
		if(CollectionUtils.isEmpty(userFavouriteNotes))
		{
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(userFavouriteNotes, HttpStatus.OK);	
	}
	
	
	
	

}
