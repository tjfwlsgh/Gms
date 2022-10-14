package com.lgl.gms.webapi.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

/**
 * 파일 관련 처리 유틸
 */
public class FileUtil {

	public static byte[] readFileByteArray(File file) throws IOException {
		return Files.readAllBytes(file.toPath());
	}

	public static int readLineCountFile(String target) throws IOException {
		BufferedReader br = null;
		int cnt = 0;
		try {
			br = Files.newBufferedReader(Paths.get(target));
			while (br.readLine() != null) {
				cnt++;
			}
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return cnt;

	}

	public static ArrayList<String> readLineData(String target) throws IOException {
		ArrayList<String> data = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = Files.newBufferedReader(Paths.get(target));

			String line = "";
			while ((line = br.readLine()) != null) {
				data.add(line);
			}

		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return data;
	}

	public static void deleteDiretory(String path) {
		try {
			FileUtils.deleteDirectory(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> readLineDataWithNewLine(String target) throws IOException {
		ArrayList<String> data = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = Files.newBufferedReader(Paths.get(target));

			String line = "";
			while ((line = br.readLine()) != null) {
				data.add(line + "\r\n");
			}

		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return data;

	}

	public static void saveToFile(InputStream inStream, String target) throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}

	public static void saveToStringFile(String data, String target) throws IOException {
		BufferedWriter bufWriter = Files.newBufferedWriter(Paths.get(target), Charset.forName("UTF-8"));
		bufWriter.write(data);
		bufWriter.close();
	}

	public static void saveToStringFileArray(ArrayList<String> data, String target) throws IOException {
		BufferedWriter bufWriter = Files.newBufferedWriter(Paths.get(target), Charset.forName("UTF-8"));
		for (String string : data) {
			bufWriter.write(string + "\r\n");

		}
		bufWriter.close();
	}

	public static void saveToStringFile(byte[] data, String target) throws IOException {
		FileOutputStream out = new FileOutputStream(new File(target));
		out.write(data);
		out.close();
	}

	public static File[] getFileList(String dir, String filterName) {
		File f = new File(dir);
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(filterName);
			}
		});
		return matchingFiles;
	}

	public static File[] getFileList(String dir, String filterName, String fileName) {
		File f = new File(dir);
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {

				if (name.endsWith(filterName) && name.contains(fileName)) {
					return true;
				}
				return false;

			}
		});
		return matchingFiles;
	}

	public static boolean setFileDelete(File f) throws IOException {
		Files.deleteIfExists(Paths.get(f.getAbsolutePath()));
		return true;
	}

	public static boolean setFileMove(String targetPath, File f) throws IOException {

		File dir = new File(targetPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Files.move(Paths.get(f.getAbsolutePath()), Paths.get(targetPath + "/" + f.getName()),
				StandardCopyOption.REPLACE_EXISTING);

		return true;
	}

	public static boolean setFileMove(String target, String name, File f) throws IOException {

		File dir = new File(target);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Files.move(Paths.get(f.getAbsolutePath()), Paths.get(target + "/" + name),
				StandardCopyOption.REPLACE_EXISTING);

		return true;
	}

	public static boolean existCheck(String targetPath) throws IOException {

		File file = new File(targetPath);
		if (file.exists()) {
			return true;
		}

		return false;
	}

	public static boolean createDirectory(String targetPath) throws IOException {

		File dir = new File(targetPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		return true;
	}

	public static boolean deleteDirectory(String targetPath) throws IOException {

		File dir = new File(targetPath);
		if (dir.exists()) {
			String[] entries = dir.list();
			for (String s : entries) {
				File currentFile = new File(dir.getPath(), s);
				currentFile.delete();
			}
			dir.delete();
		}

		return true;
	}

	public static boolean deleteDirectoryFile(String targetPath) throws IOException {

		File dir = new File(targetPath);
		if (dir.exists()) {
			String[] entries = dir.list();
			for (String s : entries) {
				File currentFile = new File(dir.getPath(), s);
				currentFile.delete();
			}
		}

		return true;
	}

	public static boolean setFileCopy(String targetPath, File f) throws IOException {

		File dir = new File(targetPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Files.copy(Paths.get(f.getAbsolutePath()), Paths.get(targetPath + "/" + f.getName()),
				StandardCopyOption.REPLACE_EXISTING);

		return true;
	}

	public static File[] getListFile(String url, String fileName, boolean isOriginJpg) throws IOException {

		File dir = new File(url);
		File[] fileNameList = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (!isOriginJpg) {
					if (fileName.length() < name.length()) {
						return true;
					} else {
						return false;
					}
				}
				return name.startsWith(fileName);
			}
		});

		return fileNameList;
	}

	public static ArrayList<File> splitTextFiles(Path bigFile, String targetPath, int maxRows) throws IOException {
		ArrayList<File> flieList = new ArrayList<File>();
		File dir = new File(targetPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		int i = 1;
		try (BufferedReader reader = Files.newBufferedReader(bigFile)) {
			String line = null;
			int lineNum = 1;
			Path splitFile = Paths.get(targetPath + "/" + bigFile.getFileName() + "_" + i + ".split");
			BufferedWriter writer = Files.newBufferedWriter(splitFile, StandardOpenOption.CREATE);
			flieList.add(splitFile.toFile());
			while ((line = reader.readLine()) != null) {

				if (lineNum > maxRows) {
					writer.close();
					lineNum = 1;
					i++;
					splitFile = Paths.get(targetPath + "/" + bigFile.getFileName() + "_" + i + ".split");
					writer = Files.newBufferedWriter(splitFile, StandardOpenOption.CREATE);
					flieList.add(splitFile.toFile());
				}

				writer.append(line);
				writer.newLine();
				lineNum++;
			}

			writer.close();
		}
		return flieList;
	}

}
