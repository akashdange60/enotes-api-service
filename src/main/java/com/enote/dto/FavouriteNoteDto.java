package com.enote.dto;

import com.enote.entity.Notes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteNoteDto {
	
	
	private Integer id;
	
	private NotesDto note;
	
	private Integer userId;

}
