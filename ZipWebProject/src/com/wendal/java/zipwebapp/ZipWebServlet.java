package com.wendal.java.zipwebapp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Files;
import org.nutz.lang.Streams;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@SuppressWarnings("serial")
public class ZipWebServlet extends HttpServlet {

	private static final Log LOG = Logs.getLog(ZipWebServlet.class);

	private List<ZipFile> files;

	private List<String> indexs;
	
	private String root;

	@Override
	public void init(ServletConfig config) throws ServletException {
		String fileStr = config.getInitParameter("files");
		files = new ArrayList<ZipFile>();
		for (String path : fileStr.split(";")) {
			File file = Files.findFile(path);
			try {
				files.add(new ZipFile(file));
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
		indexs = Arrays.asList(config.getInitParameter("indexs").split(";"));
		root = config.getInitParameter("root");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqURI = request.getRequestURI().substring(root.length());
		String path;
		if (reqURI.length() == 0 || "/".equals(reqURI))
			path = "";
		else
			path = reqURI.substring(1);
		if ((!"".equals(path)) && (!path.endsWith("/")))
			for (ZipFile zipFile : files) {
				ZipEntry entry = zipFile.getEntry(path);
				if (entry != null && (! entry.isDirectory())) {
					InputStream is = zipFile.getInputStream(entry);
					if (is == null)
						continue;
					OutputStream os = response.getOutputStream();
					Streams.write(os, is);
					os.flush();
					is.close();
					os.close();
					return;
				}
			}

		// 请求文件夹?
		if (path.length() > 0 && (!path.endsWith("/"))) {
			response.sendRedirect(request.getRequestURI() + "/");
			return;
		}
		for (ZipFile zipFile : files) {
			for (String index : indexs) {
				String myPath = path + index;
				ZipEntry entry = zipFile.getEntry(myPath);
				if (entry != null && (! entry.isDirectory())) {
					InputStream is = zipFile.getInputStream(entry);
					if (is == null)
						continue;
					OutputStream os = response.getOutputStream();
					Streams.write(os, is);
					os.flush();
					is.close();
					os.close();
					return;
				}
			}
		}

		LOG.info("文件找不到: " + request.getRequestURI() + "  " + path);
		response.setStatus(404);
	}

}
