package org.nutzx.siter.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;
import org.nutz.lang.util.Disks;
import org.nutz.lang.util.FileVisitor;
import org.nutzx.siter.SiteRender;

public class SimpleSiteRender implements SiteRender {

	private File root;
	private Map<String, String> map;

	public void render(File src, File dest) throws IOException {
		if (null == root) {
			root = src;
			buildLibrary();
		}
		File[] fs = src.listFiles();
		for (File f : fs) {
			if (f.isHidden())
				continue;
			// File
			if (f.isFile()) {
				if (map.containsKey(f.getName()))
					continue;

				String ext = Files.getSuffixName(f);
				if (null != ext)
					ext = ext.toLowerCase();

				File newFile = Files.getFile(dest, f.getName());
				if ("htm".equals(ext) || "html".equals(ext)) {
					parseHtmlFile(f, newFile);
				} else {
					Files.copyFile(f, newFile);
				}
			}
			// Dir
			else if (f.isDirectory()) {
				File newDest = Files.getFile(dest, f.getName());
				Files.makeDir(newDest);
				render(f, newDest);
			}
		}
	}

	private void parseHtmlFile(File f, File dest) throws IOException {
		// Parsing
		String txt = Files.read(f);
		try {
			txt = linkSegment(txt);
		} catch (SegmentNoFoundException e) {
			throw Lang.wrapThrow(e, "Fail link seg '%s'", f.getName());
		}
		Segment seg = new CharSegment(txt);
		for (String key : seg.keys()) {
			if (key.startsWith("/")) {
				String path = key.substring(1);
				File rs = Files.getFile(root, path);
				String myPath = Disks.getRelativePath(f, rs);
				seg.set(key, myPath);
			}
			/*
			 * else { throw Lang.makeThrow("Unknown ${%s} in '%s'", key, f); }
			 */
		}
		// Check dest file
		if (!dest.exists())
			Files.createNewFile(dest);
		// Write file
		Files.write(dest, seg);
	}

	private String linkSegment(String str) throws SegmentNoFoundException {
		Segment seg = new CharSegment(str);
		for (String key : seg.keys())
			if (key.startsWith(":")) {
				String s = map.get(key.substring(1));
				if (Strings.isBlank(s))
					throw new SegmentNoFoundException(key);
				seg.set(key, s);
			} else {
				seg.set(key, "${" + key + "}");
			}
		return seg.toString();
	}

	private void buildLibrary() {
		map = new HashMap<String, String>();
		Disks.visitFile(root, new FileVisitor() {
			public void visit(File file) {
				String txt = Files.read(file);
				map.put(file.getName(), txt);
			}
		}, new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".seg");
			}
		});
		// link each segment
		for (Entry<String, String> en : map.entrySet()) {
			try {
				String v = linkSegment(en.getValue());
				en.setValue(v);
			} catch (SegmentNoFoundException e) {
				throw Lang.wrapThrow(e, "Fail link seg '%s'", en.getKey());
			}
		}
	}

}
