package com.leonti.slickpm.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.leonti.slickpm.domain.UploadedFile;
import com.leonti.slickpm.service.UploadedFileService;

@Controller
@RequestMapping("/file")
public class FileController {

	@Resource(name="UploadedFileService")
	UploadedFileService uploadedFileService;
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> upload(
		@RequestParam("file") MultipartFile file) {
		
		UploadedFile uploadedFile = uploadedFileService.save(file);
		
    	Map<String, String> result = new HashMap<String, String>();  	
    	result.put("filename", uploadedFile.getFilename());
    	result.put("id", String.valueOf(uploadedFile.getId()));
    	result.put("result", "OK");
    	
    	return result;		
	}
	
    @RequestMapping(value = "/download/{id}/*", method=RequestMethod.GET)
    public void getFile(HttpServletResponse response, @PathVariable("id") Integer id) throws IOException {
		
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
