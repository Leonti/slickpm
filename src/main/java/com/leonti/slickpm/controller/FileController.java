package com.leonti.slickpm.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.leonti.slickpm.domain.UploadedFile;
import com.leonti.slickpm.domain.dto.UploadedFileDTO;
import com.leonti.slickpm.service.UploadedFileService;

@Controller
@RequestMapping("/file")
public class FileController {

	@Resource(name = "UploadedFileService")
	UploadedFileService uploadedFileService;

	@Secured("ROLE_USER")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
	UploadedFileDTO upload(@RequestParam("file") MultipartFile file) {

		UploadedFile uploadedFile = uploadedFileService.save(file);

		return uploadedFile.getDTO();
	}

	@RequestMapping(value = "/download/{id}/*", method = RequestMethod.GET)
	public void getFile(HttpServletResponse response,
			@PathVariable("id") Integer id) throws IOException {

		UploadedFile uploadedFile = uploadedFileService.getById(id);

		if (uploadedFile == null) {
			response.setStatus(404);
		} else {
			response.setContentType(uploadedFile.getContentType());

			InputStream in;
			in = new FileInputStream(uploadedFileService.getFile(uploadedFile));
			ServletOutputStream out = response.getOutputStream();
			IOUtils.copy(in, out);
		}
	}
}
