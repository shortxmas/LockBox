package main;


import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Password {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel createKeyTitle;
	private JTextField createKeyDir;
	private JTextField createKeyKey;
	private JButton createKeyButton;
	private JLabel unlockTitle;
	private JTextField unlockKey;
	private JButton unlockButton;
	private JLabel lockTitle;
	private JButton lockButton;
	private JLabel picLabel;

	public Main() throws IOException {
		setTitle("LockBox");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250,425);
		setLocationRelativeTo(null);

		Font f = new Font("SansSerif", Font.BOLD, 17);
		JPanel panel = new JPanel();
		createKeyTitle = new JLabel("FOLDER KEY CREATION");
		createKeyTitle.setFont(f);
		createKeyDir = new JTextField("Directory of folder to hide", 20);
		createKeyKey = new JTextField("Key to lock folder with", 20);
		createKeyButton = new JButton("Create folder key");
		unlockTitle = new JLabel("UNLOCK/UN-HIDE FOLDER");
		unlockTitle.setFont(f);

		unlockKey = new JTextField("Key to unlock folder", 20);
		unlockButton = new JButton("Unlock/unhide folder");
		lockTitle = new JLabel("LOCK/HIDE FOLDER");
		lockTitle.setFont(f);

		lockButton = new JButton("Hide/lock folder");

		panel.add(createKeyTitle);
		panel.add(createKeyDir);
		panel.add(createKeyKey);
		panel.add(createKeyButton);

		panel.add(lockTitle);
		panel.add(lockButton);

		panel.add(unlockTitle);
		panel.add(unlockKey);
		panel.add(unlockButton);
		
		ImageIcon im = new ImageIcon(getClass().getClassLoader().getResource("j.png"));
		picLabel = new JLabel();
		picLabel.setIcon(im);
		
		panel.add(picLabel);

		Color color = new Color(138, 227, 168);
		panel.setBackground(color);

		add(panel);
		setVisible(true);
		setResizable(false);

		// create key listener
		createKeyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dir = createKeyDir.getText();
				String key = createKeyKey.getText();
				String dir2 = "";
				try {
					dir2 = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
				} catch (URISyntaxException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					createKey(key, dir, dir2);

				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});

		// unlock folder listener
		unlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = unlockKey.getText();

				try {
					String dir = "";
					try {
						dir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())
								.getPath();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					unlockFolder(dir, key);

				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});

		// hide folder listener
		lockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String dir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())
							.getPath();
					try {
						lockFolder(dir);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}

			}
		});

	}

	// creates key method
	private void createKey(String password, String dir, String dir2) throws IOException {
		try {
			Path pathHidFold = Paths.get(dir);
			if (Files.exists(pathHidFold) == true && dir.isEmpty() == false
					&& isSameFolder(new File(dir).getParentFile(), new File(dir2).getParentFile())) {
				// take this as input, directory for folder to hide ******
				String strHidFold = dir;
				// create file to check if pass exists
				File fileHidFold = new File(strHidFold);

				// take this as input, creates key ********
				String nKey = password;

				if (pasExis(fileHidFold) == false && password.isEmpty() != true && fNameExists(fileHidFold) == false) {
					File fileHidFoldParent = fileHidFold.getParentFile();
					fileHidFold.setWritable(true);
					fileHidFoldParent.setWritable(true);
					fileHidFold.setExecutable(true);
					fileHidFoldParent.setExecutable(true);
					fileHidFold.setReadable(true);
					fileHidFoldParent.setReadable(true);

					crePas(nKey, strHidFold);
					// create path to hide key
					Path key = Paths.get(strHidFold + "\\" + nKey + ".key");
					// hide key
					Files.setAttribute(key, "dos:system", true);
					Files.setAttribute(key, "dos:hidden", true);
					JOptionPane.showMessageDialog(this, "Key successfully created for this folder.");

				} else if (pasExis(fileHidFold) == false && password.isEmpty() == true) {
					JOptionPane.showMessageDialog(this, "No key to lock folder was inputed.");
				} else if (password.isEmpty() != true) {
					JOptionPane.showMessageDialog(this, "A key already exists for this folder.");
				}

			} else {
				JOptionPane.showMessageDialog(this, "Directory does not exist or is outside of LockBox directory.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// unlocks folder method
	private void unlockFolder(String dir, String password) throws IOException {

		File hidFolderDir = new File(dir);
		String hidFolderParent = hidFolderDir.getParent();
		File fileHidFolderParent = new File(hidFolderParent);
		String hidFolderName = "";

		String[] hidFolderParentList = fileHidFolderParent.list();
		for (int i = 0; i < hidFolderParentList.length; i++) {
			if (hidFolderParentList[i].contains(".f") == true) {
				String temp = hidFolderParentList[i].replace(".f", "");
				hidFolderName = temp;
				break;
			}
		}

		if (pasExis(new File(hidFolderParent + "\\" + hidFolderName)) == true) {
			String strHidFold = hidFolderName;
			// create path to hide folder
			Path pathHidFold = Paths.get(strHidFold);
			// create file to check if pass exists
			File fileHidFold = new File(strHidFold);
			String[] items = fileHidFold.list();
			for (int i = 0; i < items.length;) {
				if (password.equals((items[i]).replace(".key", ""))) {
					Files.setAttribute(pathHidFold, "dos:hidden", false);
					Files.setAttribute(pathHidFold, "dos:system", false);
					JOptionPane.showMessageDialog(this, "Folder has been successfully unlocked.");
					break;

				}
				JOptionPane.showMessageDialog(this, "Password incorrect, folder not unlocked.");
				break;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Entered folder does not have a key and is not yet locked.");

		}

	}

	// locks folder method
	public void lockFolder(String dir) throws IOException {

		File hidFolderDir = new File(dir);
		String hidFolderParent = hidFolderDir.getParent();
		File fileHidFolderParent = new File(hidFolderParent);
		String hidFolderName = "";

		String[] hidFolderParentList = fileHidFolderParent.list();
		for (int i = 0; i < hidFolderParentList.length; i++) {
			if (hidFolderParentList[i].contains(".f") == true) {
				String temp = hidFolderParentList[i].replace(".f", "");
				hidFolderName = temp;
				break;
			}
		}

		Path pathHidFold = Paths.get(hidFolderParent + "\\" + hidFolderName);

		if (pasExis(new File(hidFolderParent + "\\" + hidFolderName)) == true && Files.exists(pathHidFold)) {
			Files.setAttribute(pathHidFold, "dos:hidden", true);
			Files.setAttribute(pathHidFold, "dos:system", true);
			JOptionPane.showMessageDialog(this, "Folder has been successfully hidden.");

		} else {
			JOptionPane.showMessageDialog(this, "Entered folder does not have a key and is unable to be locked.");
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
