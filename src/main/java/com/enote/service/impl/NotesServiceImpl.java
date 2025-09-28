package com.enote.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import com.enote.dto.NotesDto;
import com.enote.dto.NotesDto.CategoryDto;
import com.enote.entity.FileDetails;
import com.enote.entity.Notes;
import com.enote.exception.ResourceNotFoundException;
import com.enote.repository.CategoryRepository;
import com.enote.repository.FileRepository;
import com.enote.repository.NotesRepository;
import com.enote.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService{
	
	@Autowired
	private NotesRepository notesRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Autowired
	private FileRepository fileRepo;

	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {
		
		ObjectMapper ob=new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);
		
		//Validation notes
		checkCategoryExist(notesDto.getCategory());
		
		Notes notesMap = mapper.map(notesDto, Notes.class);
		
//		we have to create A new class for storing file related details and also mention it within FileDetails cls
			FileDetails fileDtls=saveFileDetails(file);
			
			if (!ObjectUtils.isEmpty(fileDtls)) {
				notesMap.setFileDetails(fileDtls);
				
			}else {
				notesMap.setFileDetails(null);
			}
		
		Notes saveNotes = notesRepo.save(notesMap);
		if (!ObjectUtils.isEmpty(saveNotes)) {	
			return true;
		}
		return false;
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		
		if (!ObjectUtils.isEmpty(file)) {
			
			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);
			List<String> extentionAllow=Arrays.asList("pdf","xlsx","jpg","txt");
			if (!extentionAllow.contains(extension)) {
				throw new IllegalArgumentException("Invalid file format ! Upload only .pdf, .xlsx, .jpg, .txt files");
			}	
//	Creating random number
			String rndString=UUID.randomUUID().toString();
			//String extension = FilenameUtils.getExtension(originalFilename);
			String uploadFileName = rndString+"."+extension;
			
			File saveFile=new File(uploadPath);
			if (!saveFile.exists()) {
				saveFile.mkdir();	
			}
//	path : enotes-api-service/notes/java_lang.pdf
			String storePath = uploadPath.concat(uploadFileName);
			
//	upload file
			//long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			long upload =Files.copy(file.getInputStream(),Paths.get(storePath));

			if (upload!=0) {
				FileDetails fileDtls=new FileDetails();
				fileDtls.setOriginalFileName(originalFilename);
				fileDtls.setDisplayFileName(getDisplayName(originalFilename));
				fileDtls.setUploadFileName(uploadFileName);
//				 Need to mention local path hence add file path within application.property file and @Autowired here
				fileDtls.setFileSize(file.getSize());
				fileDtls.setPath(storePath);
				
				FileDetails saveFileDtls = fileRepo.save(fileDtls);
				System.out.println("555555555555555 "+saveFileDtls);
				return saveFileDtls;
			}	
		}
		return null;
	}

	private String getDisplayName(String originalFilename) {
//		java_programming_language.pdf
//		java_lan.pdf
		
		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);
		
		if (fileName.length()>8) {
			fileName=fileName.substring(0,6);
		}
		fileName=fileName+"."+extension;
		
		return fileName;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		categoryRepo.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Category ID is Invalid"));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		
	return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
		
		
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		
		InputStream io=new FileInputStream(fileDetails.getPath()); //if file is absent then exception is handle within globally
		
		//Now converting byte array	and return
		return StreamUtils.copyToByteArray(io);
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		
		FileDetails fileDtls = fileRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("File is Not Available"));

		return fileDtls;
	}
	
	

}
