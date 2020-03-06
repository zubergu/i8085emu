package com.zubergu.i8085emu.emulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.devices.MemoryMappedDisplayDevice;
import com.zubergu.i8085emu.gui.views.CpuView;
import com.zubergu.i8085emu.gui.views.DisplayView;
import com.zubergu.i8085emu.gui.views.KeyboardView;
import com.zubergu.i8085emu.gui.views.MainMenuView;
import com.zubergu.i8085emu.gui.views.MemoryView;
import com.zubergu.i8085emu.io.MemoryMappedIODevice;
import com.zubergu.i8085emu.io.PortMappedIODevice;
import com.zubergu.i8085emu.io.RAM;
import com.zubergu.i8085emu.io.ROM;
import com.zubergu.i8085emu.io.SerialIODevice;
import com.zubergu.i8085emu.memory.Memory;

/**
 * Superclass for fully configured emulators, ready for emulation.
 * 
 * 
 * @author zubergu
 *
 */
public abstract class Emulator {

	// models
	protected Cpu cpu;
	protected Memory memory;
	protected MemoryMappedDisplayDevice display;

	// views
	private MainMenuView mainMenuView;
	private CpuView cpuView;
	private DisplayView displayView;
	private MemoryView memoryView;
	private KeyboardView keyboardView;

	// controller state
	protected volatile boolean run = false;
	protected volatile boolean debugMode = false;
	protected volatile boolean step = false;
	protected volatile Thread loopThread;
	protected int numberOfInstructionsIn1ms = 500;

	protected List<SerialIODevice> sIODeviceList = new ArrayList<SerialIODevice>();
	private List<String> breakpointMessages = new ArrayList<String>();

	protected Emulator() {
		init();
	}

	private void init() {
		initializeHardwareParts();

		this.cpu = configureCpu();
		this.memory = configureMemory();
		cpu.connectMemory(memory);
		this.sIODeviceList = configureSerialIODevices();
		this.mainMenuView = configureMainMenuView();
		this.cpuView = configureCpuView();
		this.memoryView = configureMemoryView();
		this.keyboardView = configureKeyboardView();
		this.displayView = configureDisplayView();

	}

	protected abstract void initializeHardwareParts();

	protected abstract KeyboardView configureKeyboardView();

	protected abstract MemoryView configureMemoryView();

	protected abstract CpuView configureCpuView();

	protected abstract MainMenuView configureMainMenuView();

	protected abstract DisplayView configureDisplayView();

	protected abstract Cpu configureCpu();

	protected abstract Memory configureMemory();

	protected abstract List<SerialIODevice> configureSerialIODevices();

	public List<ROM> getRomList() {
		return memory.getRomChipList();
	}

	public List<RAM> getRamList() {
		return memory.getRamChipList();
	}

	public List<MemoryMappedIODevice> getMemoryMappedIODeviceList() {
		return memory.getMemoryMappedIODeviceList();
	}

	public List<PortMappedIODevice> getPortMappedIODeviceList() {
		return memory.getPortMappedIODeviceList();
	}

	public List<SerialIODevice> getSerialIODeviceList() {
		return sIODeviceList;
	}

	public void setDebugMode(boolean debug) {
		this.debugMode = debug;
	}

	public void setRunMode(boolean run) {
		this.run = run;
	}

	public void setStep(boolean step) {
		this.step = step;
	}

	/**
	 * 
	 * @param fileName
	 * @param startAddress
	 * @param endAddress
	 * @throws IOException
	 */
	public void initializeMemory(String fileName, int startAddress,
			int endAddress) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		int address = startAddress;
		int data = 0;
		while (address <= endAddress && (data = fis.read()) != -1) {
			memory.initialize(address, data);
			address++;
		}
		fis.close();
	}

	/**
	 * actions for resetting emulator to starting state
	 */
	public void reset() {
		init();
	}

	public Memory getMemory() {
		return memory;
	}

	public Cpu getCpu() {
		return cpu;
	}

	public void connectCpuView(CpuView cpuView) {
		this.cpuView = cpuView;
	}

	public void connectMemoryView(MemoryView memoryView) {
		this.memoryView = memoryView;
	}

	public void connectDisplayDevice(MemoryMappedDisplayDevice display) {
		this.display = display;
	}

	public void displayBreakpointMessageIfReasonable() {
		if (breakpointMessages.size() > 0) { // if there were
			// any
			// breakpoint
			// messages we
			// need
			// to stop

			cpu.setHaltState(true); // halt emulation,
			// breakpoint
			// hit
			String message = "";
			for (String msg : breakpointMessages) {
				message += msg + "\n";
			}
			breakpointMessages.clear();

			mainMenuView.showBreakpointMessage(message);

		}
	}

	protected void go() {
		mainMenuView.setVisible(true);
		displayView.setVisible(true);
		keyboardView.setVisible(true);
		displayView.updateScreen(display.getFrame());
	}

	protected class RunRegularMode implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mainMenuView.getRunButton().setEnabled(false);
			mainMenuView.getPauseButton().setEnabled(true);
			cpuView.setVisible(false);
			memoryView.setVisible(false);

			Thread loopThread = new Thread(new Runnable() {
				@Override
				public void run() {
					cpu.setHaltState(false);
					while (!cpu.getHaltState()) {
						cpu.executeNextInstruction();

						for (MemoryMappedIODevice mmIO : getMemoryMappedIODeviceList()) {
							mmIO.update();
						}

						if (display.wasTouchedInLastInstruction()) {
							displayView.updateScreen(display.getFrame());
							display.setTouchedInLastInstruction(false);
						}

						for (PortMappedIODevice pmIO : getPortMappedIODeviceList()) {
							pmIO.update();
						}

						for (SerialIODevice sIO : sIODeviceList) {
							sIO.update();
						}
						LockSupport.parkNanos(300);
					}
				}

			});

			loopThread.start();
		}
	}

	protected class PauseButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cpu.setHaltState(true);
			mainMenuView.getPauseButton().setEnabled(false);
			mainMenuView.getRunButton().setEnabled(true);
		}

	}

	protected class StepButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Thread stepThread = new Thread(new Runnable() {

				@Override
				public void run() {
					mainMenuView.getPauseButton().setEnabled(true);

					cpu.setHaltState(false);
					int numberOfInstructions = mainMenuView
							.getNumberOfInstructionsPerStep();
					int i = 0;
					int step;

					if (numberOfInstructions == 0) {
						step = 0;
						numberOfInstructions = 1;
					} else {
						step = 1;
					}

					while (!cpu.getHaltState() && i < numberOfInstructions) {
						cpu.executeNextInstruction();

						for (MemoryMappedIODevice mmIO : getMemoryMappedIODeviceList()) {
							mmIO.update();
						}

						if (display.wasTouchedInLastInstruction()) {
							displayView.updateScreen(display.getFrame());
							display.setTouchedInLastInstruction(false);
						}

						for (PortMappedIODevice pmIO : getPortMappedIODeviceList()) {
							pmIO.update();
						}

						for (SerialIODevice sIO : sIODeviceList) {
							sIO.update();
						}

						Thread updateViewsThread = new UpdateViewsThread();
						updateViewsThread.start();

						try {
							updateViewsThread.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						displayBreakpointMessageIfReasonable();

						i += step;

						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			});

			stepThread.start();
		}

	}

	protected class DebugButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setDebugMode(true);
			mainMenuView.getPauseButton().setEnabled(true);
			mainMenuView.getStepButton().setEnabled(true);

			Thread updateViewsThread = new UpdateViewsThread();
			updateViewsThread.start();

			try {
				updateViewsThread.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			cpuView.setVisible(true);
			memoryView.setVisible(true);

		}

	}

	protected class ResetButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			cpuView.dispose();
			memoryView.dispose();
			mainMenuView.dispose();
			displayView.dispose();
			keyboardView.dispose();

			init();
			go();
		}

	}

	protected class MemoryDeviceInitializerActionListener implements
			ActionListener {

		private ROM memoryDevice;

		public MemoryDeviceInitializerActionListener(ROM memoryDevice) {
			this.memoryDevice = memoryDevice;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			int retVal = fc.showOpenDialog(mainMenuView);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				try {

					initializeMemory(fc.getSelectedFile().getAbsolutePath(),
							memoryDevice.getStartAddress(),
							memoryDevice.getEndAddress());

					Thread updateViewsThread = new UpdateViewsThread();
					updateViewsThread.start();

					try {
						updateViewsThread.join();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}

					memoryView.refreshDisplayedValuesForDevice(memoryDevice
							.getDescription());
					displayBreakpointMessageIfReasonable();

				} catch (IOException exception) {
					// log that initialization failed
					throw new RuntimeException("Couldn't initialize memory");
				}
			}

		}

	}

	protected class UpdateViewsThread extends Thread {

		@Override
		public void run() {
			for (Register8BitName regName : Register8BitName.values()) {
				if (cpuView.getBreakpointSet(regName)) {
					addDebugMessageIfCheckpointApplies(regName.name(),
							cpuView.getBreakpointCondition(regName),
							cpu.get8BitRegisterForName(regName).getValue(),
							cpuView.getRegisterValue(regName),
							cpuView.getBreakpointConditionValue(regName));
				}

				cpuView.setRegisterValue(regName,
						cpu.get8BitRegisterForName(regName).getValue());
			}

			for (Register16BitName regName : Register16BitName.values()) {
				if (cpuView.getBreakpointSet(regName)) {
					addDebugMessageIfCheckpointApplies(regName.name(),
							cpuView.getBreakpointCondition(regName), cpu
									.get16BitRegisterForName(regName)
									.getValue(),
							cpuView.getRegisterValue(regName),
							cpuView.getBreakpointConditionValue(regName));
				}

				cpuView.setRegisterValue(regName,
						cpu.get16BitRegisterForName(regName).getValue());
			}

			for (ROM device : memory.getAllNonPortMappedDevices()) {
				for (int i = device.getStartAddress(); i <= device
						.getEndAddress(); i++) {

					if (memoryView.getBreakpointForAddress(
							device.getDescription(), i)) {
						addDebugMessageIfCheckpointApplies(
								device.getDescription() + "at " + i,
								memoryView.getBreakpointConditionForAddress(
										device.getDescription(), i),
								device.readByte(i),
								memoryView.getDisplayedValueForAddress(
										device.getDescription(), i),
								memoryView
										.getDisplayedBreakpointValueForAddress(
												device.getDescription(), i));
					}

					memoryView.setDisplayedValueForAddress(
							device.getDescription(), i,
							Integer.toString(device.readByte(i)));

				}

				memoryView.refreshDisplayedValuesForDevice(device
						.getDescription());
			}
		}

		private void addDebugMessageIfCheckpointApplies(String name,
				String condition, int newValue, int oldValue,
				Integer conditionValue) {
			boolean conditionIsMet = false;
			String explanation = "";
			if (conditionValue != null) {
				explanation += newValue + condition + conditionValue;

				switch (condition) {
				case "==":
					conditionIsMet = newValue == conditionValue;
					break;
				case "!=":
					conditionIsMet = newValue != conditionValue;
					break;
				case "<":
					conditionIsMet = newValue < conditionValue;
					break;
				case "<=":
					conditionIsMet = newValue <= conditionValue;
					break;
				case ">":
					conditionIsMet = newValue > conditionValue;
					break;
				case ">=":
					conditionIsMet = newValue >= conditionValue;
					break;
				default:
					mainMenuView.sendTextToConsole("No condition for "
							+ condition);
					break;
				}
			} else {
				explanation = newValue + "!=" + oldValue;
				conditionIsMet = oldValue != newValue;
			}

			if (conditionIsMet) {
				breakpointMessages.add(name + " : " + explanation);
			}
		}

	}

}
