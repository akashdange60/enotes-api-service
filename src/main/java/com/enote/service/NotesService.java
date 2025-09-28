package com.enote.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enote.dto.NotesDto;
import com.enote.entity.FileDetails;

public interface NotesService {
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;

}
