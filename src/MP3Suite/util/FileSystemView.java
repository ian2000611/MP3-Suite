package MP3Suite.util;

import java.io.File;
import javax.swing.Icon;
import javax.swing.filechooser.FileView;
public class FileSystemView extends FileView {
		private FileSystemView(){}
		private static FileSystemView instance = new FileSystemView();
		public static FileSystemView getFileSystemView() {
			return instance;
		}
		javax.swing.filechooser.FileSystemView fsv = javax.swing.filechooser.FileSystemView.getFileSystemView();
		public String getTypeDescription(File f) {
			return fsv.getSystemTypeDescription(f);
		}
		public Icon getIcon(File f) {
			return fsv.getSystemIcon(f);
		}
	}
	
