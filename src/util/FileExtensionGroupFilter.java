package util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.filechooser.FileFilter;
public class FileExtensionGroupFilter extends FileFilter implements Iterable<FileFilter> {
		protected String description;
		protected final FileExtensionGroupFilter thisfilter = this;
		protected HashMap<String,FileExtensionFilter> filters = new HashMap<String,FileExtensionFilter>();
		public FileExtensionGroupFilter(String description) {
			this.description = description;
		}
		public FileExtensionGroupFilter(String description, String extensions) {
			this(description);
			addExtensions(extensions);
		}
		public void addExtensions(String extensions) {
for (String extension : extensions.split(",")) {
			addExtension(extension);
		}
		}
		public void addExtension(String extension) {
			for(String ext : filters.keySet()) {
				if (extension.equals(ext)) {
					return;
				}
			}
			filters.put(extension,new FileExtensionFilter(extension));
		}
		public Iterator<FileFilter> iterator() {

			return new Iterator<FileFilter>() {
				boolean used = false;
				Iterator<FileExtensionFilter> childFilters = filters.values().iterator();
				public boolean hasNext() {
					if (!used) {
						return true;
					} else {
						return	childFilters.hasNext();
					}
				}
				public FileFilter next() {
					if (!used) {
						used = true;
						return thisfilter;
					} else {
						return childFilters.next();
					}
				}
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
				
		}
		public String getDescription() {
			String ret = description;
			if (filters.size() > 0) {
				ret += " (";
				for (FileFilter filter : this) {
					if (filter instanceof FileExtensionFilter) {
						ret += "*." + ((FileExtensionFilter)filter).getExtension() +", ";
					}
				}
				ret = ret.substring(0,ret.length()-2) + ")";
			}
			return ret;
		}
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}
			return filters.containsKey(FileUtils.getExtension(f));
		}
	}
	
