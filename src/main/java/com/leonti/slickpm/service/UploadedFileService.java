package com.leonti.slickpm.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.UploadedFile;
import com.leonti.slickpm.domain.User;

public interface UploadedFileService {

	UploadedFile save(MultipartFile file);

	File getFile(UploadedFile uploadedFile);

	void delete(UploadedFile uploadedFile);

	UploadedFile getById(Integer id);

	String getTaskAvatar(Task task);

	String getUserAvatar(User user);

}