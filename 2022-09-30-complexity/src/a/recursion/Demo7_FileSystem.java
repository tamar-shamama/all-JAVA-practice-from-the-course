package a.recursion;

import java.io.File;

public class Demo7_FileSystem {
	
	
	/**prints all files and directories in path
	 * @param path
	 */
	public static void printFS(String path) {
		
		File file = new File(path);
		
		if (file.isFile()) {               // base case
			System.out.println("file:\t" + file);
		}
		
		if (file.isDirectory()) {
			
			System.out.println("directory:\t" + file);
			File[] filesAndDirs = file.listFiles();
			for (File f : filesAndDirs) {
				printFS(f.getPath());
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		printFS("C:/Users/tamar/Pictures/הורדות/פירות לתרגיל בהעלאת קבצים");
	}

}
