package com.sim.batchprocessing.processor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sim.batchprocessing.entity.ContentProcessFTP;
import com.sim.batchprocessing.entity.ContentProvider;
import com.sim.batchprocessing.entity.ContentType;
import com.sim.batchprocessing.entity.PhysicalFolder;
import com.sim.batchprocessing.service.ContentDeviceService;
import com.sim.batchprocessing.service.ContentProviderService;
import com.sim.batchprocessing.service.ContentService;
import com.sim.batchprocessing.service.ContentTypeService;
import com.sim.batchprocessing.service.PhysicalFolderService;
import com.sim.batchprocessing.service.SongMetaService;
import com.sim.batchprocessing.utils.ContentProcessFTPUtility;
import com.sim.batchprocessing.utils.ImageResize;
import com.sim.batchprocessing.utils.Utility;
import com.sim.batchprocessing.utils.ZipUtility;
import com.sim.batchprocessing.vo.FTPProcessContentObject;

@Component
public class ContentProcessFTPProcessor implements ItemProcessor<ContentProcessFTP, ContentProcessFTP> {
	private static final Logger logger = LoggerFactory.getLogger(ContentProcessFTPProcessor.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");	
	
	@Value("${upload.unziplocation}")
	public  String unZipLocation;
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	ContentTypeService  contentTypeService;
	
	@Autowired
	ContentProviderService contentProviderService;
	
	@Autowired
	PhysicalFolderService physicalFolderService;
	
	@Autowired
	ContentDeviceService contentDeviceService;
	
	@Autowired
	SongMetaService songMetaService;	
	
	@Override
	public ContentProcessFTP process(ContentProcessFTP item) throws Exception {	
		ContentType contentType=contentTypeService.getCType(item.getContentTypeId());	
		checkUploadExtractLoc(item, contentType);
		logger.info(myMarker, " ContentProcessFTP  {} ",item);
		return item;
	}
	
public void checkUploadExtractLoc(ContentProcessFTP item, ContentType contentType ) {
		
		try {
			switch (contentType.getContentId()) {
			case 6:
				ContentProvider contentProvider=contentProviderService.getContentProvider(item.getCpId());				
				String cpPath = contentProvider.getServerFtpHome()+File.separator+contentType.getContentName().toUpperCase()+File.separator;
				if(new File(cpPath+item.getProcessZipfile()).exists()) {			
					songsUploadExtraLoc(item, cpPath, contentType, contentProvider);
					Files.delete(Paths.get(cpPath+item.getProcessZipfile()));
					
				}else {
					item.setProcessStatus("Failed ("+item.getProcessZipfile()+" is not found)");
					logger.info(myMarker, " ContentProcessFTP  {} ",item);
				}
				
				break;
			case 31:
				imageTypeResize(item);
				break;
			case 11:
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.info(myMarker," Ex::  {} ",e);
		}
	}
	
	public boolean songsUploadExtraLoc(ContentProcessFTP item,String cpPath, ContentType contentType,ContentProvider contentProvider) {
		boolean status=false;
		try {
			String zipFileName=item.getProcessZipfile();
			PhysicalFolder physicalFolder=  physicalFolderService.getPF(item.getPfId());
			String zipFileName1  = zipFileName;
			boolean isZipFile = false;			
			if( zipFileName.endsWith(".zip") ) {
				zipFileName1 = zipFileName.substring(0,zipFileName.indexOf("zip")-1);
				isZipFile = true;
			}
			String uploadExtractLoc = unZipLocation;
			if(!uploadExtractLoc.endsWith(File.separator)) uploadExtractLoc = uploadExtractLoc+File.separator;
			uploadExtractLoc = uploadExtractLoc+"UnzipZipFileContent"+File.separator+contentProvider.getCpId()+File.separator+contentType.getContentId()+File.separator;
			File uploadExtractFile = new File(uploadExtractLoc);
			String zipFilePath ="";
			zipFilePath=cpPath+zipFileName;
			if(!uploadExtractFile.exists()) {
					uploadExtractFile.mkdirs();
			}
			logger.info(myMarker, "zipFilePath: {} uploadExtractLoc {}{} ",cpPath,uploadExtractLoc,zipFileName);
			if(isZipFile)
				status = ZipUtility.extractZipContentWithZip64File(zipFilePath, uploadExtractLoc,zipFileName1);
			String startextra=zipFileName+" on "+new java.util.Date();
			logger.info(myMarker," Upzipping Ended of {} {} ", startextra,status);
			FTPProcessContentObject ftpObject = new FTPProcessContentObject();
			
			ftpObject.setContentId(item.getCpfContId());
			ftpObject.setContentTypeId(item.getContentTypeId());
			ftpObject.setPhysicalLocation(physicalFolder.getLocation()+File.separator+item.getLocation());
			ftpObject.setTitle(item.getTitle());
			ftpObject.setContentURL(uploadExtractLoc+zipFileName1+File.separator+item.getCpContentName());
			ftpObject.setPreviewURL(uploadExtractLoc+zipFileName1+File.separator+item.getCpContentName()+File.separator+"Preview"+File.separator);
			ftpObject.setThumbnail1URL(uploadExtractLoc+zipFileName1+File.separator+item.getCpContentName()+File.separator+"Thumbnails"+File.separator);
			ftpObject.setProcessId(item.getProcessId()+"");
			ftpObject.setUploadType(item.getUploadType());
			
			status=ContentProcessFTPUtility.processContentDownload(ftpObject, contentDeviceService, contentService, item, songMetaService);
			logger.info(myMarker," processContentDownload {} ",status);
			if( new File(uploadExtractLoc+zipFileName1).exists() ) {
				String str=uploadExtractLoc+zipFileName1;							
				logger.info(myMarker, "UnZipFile location {} is Deleted",str);
				org.apache.commons.io.FileUtils.deleteDirectory( new File(str) );
				
			}	
		} catch (Exception e) {
			logger.info(myMarker," Ex::  {} ",e);
		}	
		return status;
	}
	
	public void imageTypeResize(ContentProcessFTP item) {
		String result="";
		try {
			ImageResize imageresize=new ImageResize();
			PhysicalFolder physicalFolder=  physicalFolderService.getPF(item.getPfId());
			String imagelocations=physicalFolder.getLocation()+File.separator+item.getLocation()+File.separator;
			List<File> files= ContentProcessFTPUtility.getFilesFromImageLocation(imagelocations);
			Iterator<File> it=files.iterator();
			Map<String,String> baseFiles = new HashMap<>();
			while (it.hasNext()) {
				File imageFile = it.next();
				String imageName = imageFile.getName();
				long width = Utility.getWidth(imageFile);
				long height = Utility.getHeight(imageFile);								
				baseFiles.put(width+"-"+height,imagelocations+imageName);
			}
			if(!baseFiles.isEmpty()) {
				result=imageresize.processImageResizes(imagelocations,imagelocations,1, baseFiles);
				ContentProcessFTPUtility.insertImagesToDB(imagelocations, item.getCpfContId(), item.getContentTypeId(), contentDeviceService);
			}
			if(result.equalsIgnoreCase("ok")) {
				item.setProcessStatus("Success");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(myMarker," imageTypeResize Ex::  {} ",e);
		}
		//
	}

}
