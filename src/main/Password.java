package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;

public class Password extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void crePas(String key, String dir) throws IOException {
		// create key file
		File temp = new File(dir + "\\" + key + ".key");
		temp.createNewFile();

		// create doc with hidden folder name
		File hidFold = new File(dir);
		String HidFoldParent = hidFold.getParent();
		String strHidFolderName = getFolderName(dir);
		File fileHidFolderName = new File(HidFoldParent + "\\" + strHidFolderName + ".f");
		fileHidFolderName.createNewFile();
		// hide folder name
		Path fName = Paths.get(HidFoldParent + "\\" + strHidFolderName + ".f");
		Files.setAttribute(fName, "dos:system", true);
		Files.setAttribute(fName, "dos:hidden", true);

	}

	public static String getFolderName(String in) {
		String r = "";
		for (int i = in.length() - 1; i >= 0; i--) {
			String temp = String.valueOf(in.charAt(i));
			if (temp.equals("\\")) {
				break;
			}
			r = r + in.charAt(i);

		}

		String ret = "";
		for (int x = r.length() - 1; x >= 0; x--) {
			ret = ret + r.charAt(x);
		}

		return ret.trim();

	}

	public static boolean pasExis(File in) {

		String arr[] = in.list();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].contains(".key")) {
				return true;
			}
		}

		return false;
	}

	public static boolean isSameFolder(File in1, File in2) {
		String in1str = in1.getName();
		String in2str = in2.getName();
		if (in1str.equals(in2str) == true) {
			return true;
		}
		return false;
	}

	public static boolean fNameExists(File in) {
		File hidFoldParent = in.getParentFile();
		String arr[] = hidFoldParent.list();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].contains(".key")) {
				return true;
			}
		}

		return false;

	}

	public static String printDetails(File in) {

		String ret = "";
		String[] items = in.list();
		{
			for (int i = 0; i < items.length; i++) {
				ret = ret + (items[i] + " ");
			}
		}
		return ret.trim();

	}

	public static String r(String in) {
		StringBuilder r = new StringBuilder();
		for (int i = in.length() - 1; i >= 0; i--) {
			r.append(in.charAt(i));
		}
		return r.toString();
	}

}
