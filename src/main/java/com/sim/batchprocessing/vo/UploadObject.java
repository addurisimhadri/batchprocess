package com.sim.batchprocessing.vo;

import com.sim.batchprocessing.entity.ContentType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UploadObject  {
	
	private String srcDir="";
	private String destDir="";
	private String ctName="";
	private int ctId=-1;
	private int cpId=-1;
	private String zipFilePath="";
	private String zipFileName="";
	private String folder="";
	private ContentType contentType;
	private String cpName="";
	private int pfId=-1;
	private String source="Web";
	
	
}
