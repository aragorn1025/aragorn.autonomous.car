package aragorn.autonomous.car.zold.gui;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import aragorn.autonomous.car.action.listener.ChangeCarActionListener;
import aragorn.autonomous.car.action.listener.ChangeMazeActionListener;
import aragorn.autonomous.car.action.listener.ImportLinearMazeActionListener;
import aragorn.autonomous.car.action.listener.ResetActionListener;
import aragorn.autonomous.car.object.CircularCar;
import aragorn.autonomous.car.object.DefaultMaze;
import aragorn.autonomous.car.object.RandomGridMaze;
import aragorn.autonomous.car.object.RectangularCar;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystem;
import aragorn.autonomous.car.zold.gui.menu.items.ExportCarTrack4DMenuItem;
import aragorn.autonomous.car.zold.gui.menu.items.ExportCarTrack6DMenuItem;
import aragorn.autonomous.car.zold.gui.menu.items.ExportLinearMazeMenuItem;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenu;
import aragorn.gui.GuiMenuItem;
import aragorn.gui.action.listener.CloseGuiFrameActionListener;
import aragorn.gui.action.listener.PauseGuiFrameActionListener;
import aragorn.gui.action.listener.PlayGuiFrameActionListener;

@SuppressWarnings("serial")
class MainMenuBar extends JMenuBar {

	private JFileChooser file_chooser = new JFileChooser(MainFrame.DESKTOP);

	private GuiFrame frame;

	private AutonomousSystem autonomous_system;

	MainMenuBar(GuiFrame frame, AutonomousSystem autonomous_system) {
		this.frame = frame;
		this.autonomous_system = autonomous_system;
		file_chooser.setFileFilter(new FileNameExtensionFilter("text file", "txt"));

		add(getFileMenu());
		add(getPlayMenu());
		add(getMazeMenu());
		add(getCarMenu());
	}

	private GuiMenu getFileMenu() {
		GuiMenu menu = new GuiMenu("File");
		menu.add(getFileImportMenu());
		menu.add(getFileExportMenu());
		// menu.add(new JSeparator());
		// menu.add(new JMenuItem("Export All"));
		menu.addSeparator();
		menu.add(new GuiMenuItem("Exit", 'X'));
		menu.getItem(3).addActionListener(new CloseGuiFrameActionListener(frame));
		return menu;
	}

	private GuiMenu getFileImportMenu() {
		GuiMenu menu = new GuiMenu("Import");
		menu.add(new GuiMenuItem("Import Linear Maze", '\0'));
		menu.getItem(0).addActionListener(new ImportLinearMazeActionListener(frame, autonomous_system));
		return menu;
	}

	private GuiMenu getFileExportMenu() {
		GuiMenu menu = new GuiMenu("Export");
		menu.add(new ExportLinearMazeMenuItem(frame, file_chooser, autonomous_system));
		menu.add(new ExportCarTrack4DMenuItem(frame, file_chooser, autonomous_system));
		menu.add(new ExportCarTrack6DMenuItem(frame, file_chooser, autonomous_system));
		return menu;
	}

	private GuiMenu getPlayMenu() {
		GuiMenu menu = new GuiMenu("Play");
		menu.add(new GuiMenuItem("Play", 'Z'));
		menu.getItem(0).addActionListener(new PlayGuiFrameActionListener(frame));
		menu.add(new GuiMenuItem("Pause", 'X'));
		menu.getItem(1).addActionListener(new PauseGuiFrameActionListener(frame));
		menu.addSeparator();
		menu.add(new GuiMenuItem("Reset", 'C'));
		menu.getItem(3).addActionListener(new ResetActionListener(frame, autonomous_system));
		return menu;
	}

	private GuiMenu getMazeMenu() {
		GuiMenu menu = new GuiMenu("Maze");
		menu.add(new GuiMenuItem("Change to the default maze", '0'));
		menu.getItem(0).addActionListener(new ChangeMazeActionListener(frame, autonomous_system, DefaultMaze.class));
		menu.add(new GuiMenuItem("Change to a random grid maze", '1'));
		menu.getItem(1).addActionListener(new ChangeMazeActionListener(frame, autonomous_system, RandomGridMaze.class));
		return menu;
	}

	private GuiMenu getCarMenu() {
		GuiMenu menu = new GuiMenu("Car");
		menu.add(new GuiMenuItem("Change to a circular car", '1'));
		menu.getItem(0).addActionListener(new ChangeCarActionListener(frame, autonomous_system, CircularCar.class));
		menu.add(new GuiMenuItem("Change to a rectangular car", '2'));
		menu.getItem(1).addActionListener(new ChangeCarActionListener(frame, autonomous_system, RectangularCar.class));
		return menu;
	}
}