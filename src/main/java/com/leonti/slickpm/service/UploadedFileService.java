package com.leonti.slickpm.service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.leonti.slickpm.domain.Points;
import com.leonti.slickpm.domain.UploadedFile;

@Service("UploadedFileService")
@Transactional
public class UploadedFileService {

	protected static Logger logger = Logger.getLogger("UploadedFileService"); 
	
    @Autowired
    private SessionFactory sessionFactory;	
    
	public UploadedFile save(MultipartFile file) {
		
		System.out.println(file.getName());
		
		UploadedFile uploadedFile = new UploadedFile(file.getOriginalFilename(), file.getContentType(), file.getSize());
		sessionFactory.getCurrentSession().saveOrUpdate(uploadedFile);
		
		Properties props;		
		try {
			props = PropertiesLoaderUtils.loadAllProperties("configuration.properties");
			String folder = props.getProperty("uploadDir") + File.separator + uploadedFile.getId() + File.separator;
			new File(folder).mkdirs();
			
			File dest = new File(folder + file.getOriginalFilename());  
			
			file.transferTo(dest);
			
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}		
		
		return uploadedFile;
	}
	
	public File getFile(UploadedFile uploadedFile) {
		Properties props;		
		try {
			props = PropertiesLoaderUtils.loadAllProperties("configuration.properties");
			
			String folder = props.getProperty("uploadDir") + File.separator + uploadedFile.getId() + File.separator;			
			return new File(folder + uploadedFile.getFilename());  
			
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}	
		
		return null;
	}	
    
    public void delete(UploadedFile uploadedFile) {
    	sessionFactory.getCurrentSession().delete(uploadedFile);
    }

	public UploadedFile getById(Integer id) {

    	return (UploadedFile) sessionFactory.getCurrentSession()
    			.createQuery("FROM UploadedFile WHERE id = ?")
    			.setLong(0, id)
    			.setMaxResults(1)
    			.uniqueResult();
	}
	
}
