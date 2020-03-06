package com.zubergu.i8085emu.emulator;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.devices.MemoryImpl;
import com.zubergu.i8085emu.devices.MemoryMappedDisplayDevice;
import com.zubergu.i8085emu.devices.PortMappedKeyboard;
import com.zubergu.i8085emu.devices.RamImpl;
import com.zubergu.i8085emu.devices.RomImpl;
import com.zubergu.i8085emu.gui.views.CpuView;
import com.zubergu.i8085emu.gui.views.DisplayView;
import com.zubergu.i8085emu.gui.views.KeyboardView;
import com.zubergu.i8085emu.gui.views.MainMenuView;
import com.zubergu.i8085emu.gui.views.MemoryView;
import com.zubergu.i8085emu.io.RAM;
import com.zubergu.i8085emu.io.ROM;
import com.zubergu.i8085emu.io.SerialIODevice;
import com.zubergu.i8085emu.memory.Memory;

public class Sol20PC extends Emulator {

	// sol20 pc parts
	private PortMappedKeyboard keyboard;
	private RAM programRam;
	private ROM systemRom;
	private RAM systemRam;
	private MemoryMappedDisplayDevice display;
	private RAM otherRam;

	public Sol20PC() {

	}

	@Override
	protected void initializeHardwareParts() {
		keyboard = new PortMappedKeyboard();
		display = new MemoryMappedDisplayDevice(0xCC00, 1024);
		programRam = new RamImpl(0x0000, 32768, "Program RAM");
		systemRom = new RomImpl(0xC000, 2048, "System ROM");
		systemRam = new RamImpl(0xC800, 1024, "System RAM");
		otherRam = new RamImpl(0xCD00, 0x3300, "Other Ram");
	}

	@Override
	protected KeyboardView configureKeyboardView() {
		KeyboardView keyboardView = new KeyboardView();
		keyboardView.setKeyboardListener(keyboard);
		return keyboardView;
	}

	@Override
	protected MemoryView configureMemoryView() {
		MemoryView memoryView = new MemoryView();
		for (ROM device : memory.getAllNonPortMappedDevices()) {
			memoryView.createTabForDevice(device.getDescription(),
					device.getStartAddress(), device.getMemorySize());
			memoryView.refreshDisplayedValuesForDevice(device.getDescription());
		}
		
		return memoryView;
	}

	@Override
	protected CpuView configureCpuView() {
		CpuView cpuView = new CpuView();

		return cpuView;
	}

	@Override
	protected MainMenuView configureMainMenuView() {
		MainMenuView mainMenuView = new MainMenuView();
		mainMenuView.getPauseButton().setEnabled(true);
		mainMenuView
				.setPauseButtonActionListener(new PauseButtonActionListener());
		mainMenuView.getRunButton().setEnabled(true);
		mainMenuView.setRunButtonActionListener(new RunRegularMode());
		mainMenuView.getDebugButton().setEnabled(true);
		mainMenuView
				.setDebugButtonActionListener(new DebugButtonActionListener());
		mainMenuView.getResetButton().setEnabled(true);
		mainMenuView
				.setResetButtonActionListener(new ResetButtonActionListener());
		mainMenuView.getStepButton().setEnabled(false);
		mainMenuView
				.setStepButtonActionListener(new StepButtonActionListener());

		for (ROM device : memory.getRomChipList()) {
			mainMenuView.addRomToMenu(device.getDescription(),
					new MemoryDeviceInitializerActionListener(device));
		}
		for (RAM device : memory.getRamChipList()) {
			mainMenuView.addRamToMenu(device.getDescription(),
					new MemoryDeviceInitializerActionListener(device));
		}
		return mainMenuView;
	}

	@Override
	protected DisplayView configureDisplayView() {
		DisplayView displayView = new DisplayView(new Dimension(display
				.getFrame().getWidth(), display.getFrame().getHeight()));
		connectDisplayDevice(display);
		return displayView;
	}

	@Override
	protected Cpu configureCpu() {
		return new Cpu();
	}

	@Override
	protected Memory configureMemory() {
		Memory memory = new MemoryImpl();
		memory.connect(programRam);
		memory.connect(systemRom);
		memory.connect(systemRam);
		memory.connect(display);
		memory.connect(otherRam);
		memory.connect(keyboard);

		return memory;
	}

	@Override
	protected List<SerialIODevice> configureSerialIODevices() {
		return new ArrayList<SerialIODevice>();
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Emulator emulator = new EmulatorMain(); // create emulated
				// machine
				new Sol20PC().go();
			}

		});
	}

}
