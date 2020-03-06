package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class MOVRdRs implements Instruction {
	private String description = "";
	private Cpu cpu; // cpu instruction operates on
	private Register8BitName destination;
	private Register8BitName source;
	
	/*
	 * 
	 */
	public MOVRdRs(Cpu cpu, Register8BitName destination, Register8BitName source) {
		this.cpu = cpu;
		this.destination = destination;
		this.source = source;
	}
	

	@Override
	public int execute() {
		Register8Bit destinationRegister = cpu.get8BitRegisterForName(destination);
		Register8Bit sourceRegister = cpu.get8BitRegisterForName(source);
		destinationRegister.setValue(sourceRegister.getValue());
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
	

}
