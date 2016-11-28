package com.jew.kit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.jew.log.Log;

/**
 * FileKit 
 */
public class FileKit {
	
	private static Log log = Log.getLog(FileKit.class);
	
	/**
	 * write file with directory and shortFileName
	 * @param dir
	 * @param shortFileName
	 */
	public static void writeFile(String dir, String shortFileName,String content,boolean isOverWrite){
		if(dir == null ){
			throw new IllegalArgumentException("directory name can not be null");
		}
		File fileDir = new File(dir);
		if(false == fileDir.exists()){
			fileDir.mkdirs();
		}
		if(shortFileName == null ){
			throw new IllegalArgumentException("FileName can not be null");
		}
		File srcFile = new File(dir + File.separator + shortFileName);
		if(false == isOverWrite){
			if(srcFile.isFile()){
				return ;
			}
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(srcFile);
			fw.write(content);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}finally {
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
	}
	/**
	 * write file with fullFileName 
	 * <example>
	 * 	F:/file/t.txt  or
	 *  /Home/clark/dir/t.txt
	 * </example>
	 * @param fullFileName
	 * @param content content which is going to write 
	 */
	@SuppressWarnings("unused")
	public static void writeFile(String fullFileName,String content,boolean isOverWrite){
		
		File file = new File(fullFileName);
		
		String parentDir = file.getParent();
		
		if( parentDir == null ){
			throw new IllegalArgumentException("illegal file name , should be a absolute path name ");
		}
		
		writeFile(parentDir, file.getName(),content,isOverWrite);
	}
	
}
