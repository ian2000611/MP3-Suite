package MP3Suite.util;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
public class FileUtils {
	private static final FileExtensionGroupFilter audioFileFilter = new FileExtensionGroupFilter(i18n.get("extensions.audiofiles.description"),Config.get("extensions.AUDIOFILES"));
	
	public static File[] openAudioFiles(Component parent) {
		JFileChooser chooser = new JFileChooser();
		// Note: source for ExampleFileFilter can be found in FileChooserDemo,
		// under the demo/jfc directory in the Java 2 SDK, Standard Edition.
		chooser.resetChoosableFileFilters();
		chooser.setAcceptAllFileFilterUsed(true);
		//chooser.addChoosableFileFilter(audioFileFilter);
		if (audioFileFilter != null) {
			for(FileFilter filter : audioFileFilter) {
				chooser.addChoosableFileFilter(filter);
			}
			chooser.setFileFilter(audioFileFilter);
		}
		chooser.setFileView(FileSystemView.getFileSystemView());
		chooser.setMultiSelectionEnabled(true);
		int returnVal = chooser.showOpenDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFiles();
		} else {
			return new File[]{};
		}
	}
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 &&  i < s.length() - 1) {
			ext = s.substring(i+1).toLowerCase();
		}
		return ext;
	}
} 
