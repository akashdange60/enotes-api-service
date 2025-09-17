package com.enote.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enote.dto.NotesDto;
import com.enote.dto.NotesDto.CategoryDto;
import com.enote.entity.Notes;
import com.enote.exception.ResourceNotFoundException;
import com.enote.repository.CategoryRepository;
import com.enote.repository.NotesRepository;
import com.enote.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService{
	
	@Autowired
	private NotesRepository notesRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public Boolean saveNotes(NotesDto notesDto) throws Exception {
		
		//Validation notes
		checkCategoryExist(notesDto.getCategory());
		
		Notes notes = mapper.map(notesDto, Notes.class);
		Notes saveNotes = notesRepo.save(notes);
		
		if (!ObjectUtils.isEmpty(saveNotes)) {
			
			return true;
		}
		return false;
		
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		categoryRepo.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Category ID is Invalid"));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		
	return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
		
		
	}

}
