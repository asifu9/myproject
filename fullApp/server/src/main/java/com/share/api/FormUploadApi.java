	package com.share.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.share.domain.core.FormAttachment;
import com.share.domain.core.FormAttachmentItems;
import com.share.exception.AppException;
import com.share.master.data.service.FormAttachmentService;


@RestController
@RequestMapping("/FormUpload")
@CrossOrigin(origins = "http://localhost:4200")
public class FormUploadApi {

	
	@Autowired
	private FormAttachmentService formAttachmentService;
	
	@Value("${filePath}")
	private String filePathConfig;

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<FormAttachment> getById(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<FormAttachment>( formAttachmentService.getById(id),headers,HttpStatus.OK);
	
	}
    
    @RequestMapping(method = RequestMethod.POST, value = "/items/{userId}/{form}/{tenantId}/{id}", 
    		consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createAttachments(@PathVariable(value = "userId") String userId,
    		@PathVariable(value = "form") String form,
    		@PathVariable(value = "tenantId") String tenantId,
    		@PathVariable(value = "id") String id,
    		@RequestParam("file[]") MultipartFile[] uploadFiles)throws AppException
             {
    	System.out.println("id "+id + " : " + form + " : " + userId);
    	FormAttachment result=null;
    	MultiValueMap<String, String> headers = new HttpHeaders();
    	List<FormAttachmentItems> att = createUploads(uploadFiles,userId,form,tenantId);
    	if(id==null||"null".equals(id)){
    		FormAttachment fa=new FormAttachment();
        	fa.setCreatedBy(userId);
        	fa.setCreatedOn(Calendar.getInstance().getTime());
        	fa.setForm(form);
        	fa.setId(UUID.randomUUID().toString());
        	fa.setList(att);
        	fa.setTenantId(tenantId);
        	result=formAttachmentService.saveOrUpdate(fa);
    	}else{
    		FormAttachment fa=formAttachmentService.getById(id);
    		if(fa!=null){
    			fa.setList(att);
    			result=formAttachmentService.saveOrUpdate(fa);
    		}
    	}
    	return new ResponseEntity<FormAttachment>(result, headers, HttpStatus.OK);
    }
    public List<FormAttachmentItems> createUploads(MultipartFile[] files,String userId,String form,String tenantId){
    	
    	List<FormAttachmentItems> list=new ArrayList<>();
    	 Map<String,String> attachmentIds = new HashMap<>();
         try {
             for (MultipartFile file : files) {
                 String contentType = file.getContentType();
                 String fileName = file.getOriginalFilename();
                 long size = file.getSize();
                 System.out.println(fileName);
                 int dotPos = fileName.lastIndexOf('.');
                 if (dotPos < 0) {
                 }
                 String fileExtension = fileName.substring(dotPos + 1, fileName.length());
                 InputStream in = null;
                 try {
                     in = file.getInputStream();
                     String filePathCreated = create(userId,form,tenantId,contentType, fileName, size, in);
                     FormAttachmentItems ii=new FormAttachmentItems();
                     ii.setAttId(UUID.randomUUID().toString());
                     ii.setCreatedOn(Calendar.getInstance().getTime());
                     ii.setFileName(fileName);
                     ii.setPath(filePathCreated);
                     list.add(ii);
                 }
                 catch (IOException ex) {
                     ex.printStackTrace();
                 }
             }
         }
         finally {
             for (MultipartFile file : files) {
                 try {
                     IOUtils.closeQuietly(file.getInputStream());
                 }
                 catch (IOException ex) {
                     ex.printStackTrace();
                 }
             }
         }
         return list;
    }
    private String create (
    		String userId,
    		String form,
    		String tenantId,
            String contentType,
            String fileName,
            long fileLength,
            InputStream fileContentStream)
    {
        try {
        	String filePath=filePathConfig+tenantId+"/"+form+"/"+userId;
        	System.out.println("filePath "+filePath);
        	File f=new File(filePath);
        	if(!f.isDirectory()){
        		f.mkdirs();
        	}
        	String fileNamee=Calendar.getInstance().getTimeInMillis()+"_"+fileName;
        	File targetFile = new File(filePath+"/"+""+fileNamee);
            OutputStream outStream = new FileOutputStream(targetFile);
         
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fileContentStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            return fileNamee;
           
        }catch(Exception ex){
        	ex.printStackTrace();
        }
		return "failure";
        
    }
    
    
}
