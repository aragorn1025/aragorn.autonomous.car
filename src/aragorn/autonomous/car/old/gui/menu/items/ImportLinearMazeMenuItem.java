package aragorn.autonomous.car.old.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.autonomous.car.old.gui.Main;
import aragorn.autonomous.car.old.math.operation.GeometryPolyline;
import aragorn.autonomous.car.old.objects.LinearMaze;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenuItem;

@SuppressWarnings("serial")
public class ImportLinearMazeMenuItem extends GuiMenuItem implements ActionListener {
	private GuiFrame			frame;
	private JFileChooser		fileChooser;
	private AutonomousSystem	autonomousSystem;

	private static final String	FILE_IMPORT_CANCELLED_MESSAGE	= new String("File import for maze has been cancelled.");
	private static final String	FILE_IMPORT_DONE_MESSAGE		= new String("File import for maze is done.");
	private static final String	FILE_NOT_FOUND_MESSAGE			= new String("File is not found.");

	public ImportLinearMazeMenuItem(GuiFrame frame, JFileChooser fileChooser, AutonomousSystem autonomousSystem) {
		super("Import Linear Maze", '\0');
		this.frame = frame;
		this.fileChooser = fileChooser;
		this.autonomousSystem = autonomousSystem;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fileChooser.setDialogTitle("Import Linear Maze");
		fileChooser.setCurrentDirectory(Main.DESKTOP);
		switch (fileChooser.showSaveDialog(frame)) {
			case JFileChooser.APPROVE_OPTION:
				try {
					File file = fileChooser.getSelectedFile();
					if (!file.exists()) {
						JOptionPane.showMessageDialog(frame, FILE_NOT_FOUND_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					Scanner input = new Scanner(file);
					GeometryPolyline leftWall = new GeometryPolyline();
					GeometryPolyline rightWall = new GeometryPolyline();
					GeometryPolyline endWall = new GeometryPolyline();
					GeometryPolyline wall;
					for (int j = 0; j < 3; j++) {
						switch (j) {
							case 0:
								wall = leftWall;
								break;
							case 1:
								wall = leftWall;
								break;
							case 2:
								wall = endWall;
								break;
							default:
								throw new UnknownError("Unknown error for file chooser.");
						}
						int n = input.nextInt();
						input.nextLine();
						for (int i = 0; i < n; i++) {
							wall.addPoint(parsePoint(input.nextLine()));
						}
					}
					input.close();
					autonomousSystem.setMaze(new LinearMaze(leftWall, rightWall, endWall));
					JOptionPane.showMessageDialog(frame, FILE_IMPORT_DONE_MESSAGE, "Message", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case JFileChooser.CANCEL_OPTION:
				JOptionPane.showMessageDialog(frame, FILE_IMPORT_CANCELLED_MESSAGE, "Cancel", JOptionPane.CANCEL_OPTION);
				return;
			default:
				throw new UnknownError("Unknown error for file chooser.");
		}
	}

	private static Point2D.Double parsePoint(String line) {
		if (line == null || line.length() == 0) {
			throw new NullPointerException("Input string should not be null.");
		}
		line = line.replaceAll(" ", "");
		int lx = 0, rx = 0, ly = 0, ry = 0;
		double x = 0, y = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '(') {
				lx = i + 1;
				break;
			}
		}
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ',') {
				rx = i;
				ly = i + 1;
				break;
			}
		}
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ')') {
				ry = i;
				break;
			}
		}
		x = Double.parseDouble(new String(line.toCharArray(), lx, rx - lx));
		y = Double.parseDouble(new String(line.toCharArray(), ly, ry - ly));
		return new Point2D.Double(x, y);
	}
}