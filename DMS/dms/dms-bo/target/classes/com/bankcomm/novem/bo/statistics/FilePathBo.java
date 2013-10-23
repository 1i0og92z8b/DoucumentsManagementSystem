package com.bankcomm.novem.bo.statistics;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 荆昊熠<jing_hy@bankcomm.com> 
 * 
 */
@EqualsAndHashCode(callSuper = false)
@Data

public class FilePathBo {
	private int fileId;//文件Id
	private String fileName;//用户上传文件时设定的文件名
	private String filePath;//文件在服务器上存放路径，不包括文件名部分
	private String fullName;//文件在服务器上的文件名
}
