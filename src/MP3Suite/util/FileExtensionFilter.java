package MP3Suite.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
public class FileExtensionFilter extends FileFilter {
		protected String description;
		protected String extension;
		public FileExtensionFilter(String extension) {
			this.extension = extension.toLowerCase();
			this.description = "";
			try {
				File f = File.createTempFile("test","."+extension);
				this.description = FileSystemView.getFileSystemView().getSystemTypeDescription(f);
				f.delete();
			} catch (Exception e) {e.printStackTrace();}
		}
		public String getDescription() {
			return description + " (*." + extension + ")";
		}
		public String getExtension() {
			return extension;
		}
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}

			if (extension.equals(FileUtils.getExtension(f))) {
				return true;
			}

			return false;
		}

	}
	
