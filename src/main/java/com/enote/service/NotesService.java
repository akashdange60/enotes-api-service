package com.enote.service;

import java.util.List;

import com.enote.dto.NotesDto;

public interface NotesService {
	
	public Boolean saveNotes(NotesDto notesDto) throws Exception;
	
	public List<NotesDto> getAllNotes();

}
