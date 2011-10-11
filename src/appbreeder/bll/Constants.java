package appbreeder.bll;

import java.io.File;

public final class Constants {
	//public static final String dataBaseURLString = "http://devdata.appbreeder.com/Service.asmx/GetSQLiteDatabase?ID=1";

	public static File getPathTempFolder() {
		return getFileForFolder(getPathRootFolder().getAbsolutePath() + "/temp");
	}
	public static File getPathDBFolder() {
		return getFileForFolder(getPathRootFolder().getAbsolutePath() + "/db");
	}
	public static File getPathDBFolder(String fileName) {
		File dirDB=getFileForFolder(getPathRootFolder().getAbsolutePath() + "/db");
		return new File(dirDB.getAbsolutePath()+ "/"+fileName);
	}

	public static File getPathResourcesFolder() {
		return getFileForFolder(getPathRootFolder().getAbsolutePath() + "/res");
	}
	public static File getPathRootFolder() {
		return getFileForFolder(BaseApplicationManager.externalBasePath + "/"
				+ BaseApplicationManager.appBrendName);
	}
	public static File getFileForFolder(String folderpath) {
		File file = new File(folderpath);
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}
	
	

}
