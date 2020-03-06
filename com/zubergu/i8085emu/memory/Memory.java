package com.zubergu.i8085emu.memory;

import java.util.List;

import com.zubergu.i8085emu.io.MemoryMappedIODevice;
import com.zubergu.i8085emu.io.PortMappedIODevice;
import com.zubergu.i8085emu.io.RAM;
import com.zubergu.i8085emu.io.ROM;

/**
 * Interface for hiding memory implementation from cpu. CPU writes/reads to/from
 * object implementing this interface only. Means that every IO/M device is
 * transparent to CPU.
 * 
 * @author zubergu
 *
 */
public interface Memory {

	Integer readByte(Integer address);

	void writeByte(Integer address, Integer data);

	Integer readPort(Integer portAddress);

	void writeToPort(Integer portAddress, Integer data);

	void connect(ROM rom);

	void connect(RAM ram);

	void connect(MemoryMappedIODevice mmIO);

	void connect(PortMappedIODevice pmIO);

	List<MemoryMappedIODevice> getMemoryMappedIODeviceList();

	List<PortMappedIODevice> getPortMappedIODeviceList();

	List<RAM> getRamChipList();

	List<ROM> getRomChipList();

	List<ROM> getAllNonPortMappedDevices();

	public void initialize(int address, int data);

}
