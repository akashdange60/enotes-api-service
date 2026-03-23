package com.enote.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NotesDto {
	
private Integer id;
	
	private String title;
	
	private String description;
	
	private CategoryDto category;
	
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;
	
	private FilesDto fileDetails;  //fileDetails name should be same in notes class where ManytoOne mapping is done
	
    private Boolean isDeleted;  //this field is for delete and recovering the notes.
	
	private LocalDateTime deletedOn;
	
	
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Setter
	@Getter
	public static class FilesDto
	{
		private Integer id;
		private String originalFileName;
		private String displayFileName;
		
	}
	
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Setter
	@Getter
	public static class CategoryDto
	{
		private Integer id;
		
		private String title;
	}

}
