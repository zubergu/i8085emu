package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;

public class STC implements Instruction {
private Cpu cpu;
	
	public STC(Cpu cpu){
		this.cpu = cpu;
	}
	@Override
	public int execute() {
		cpu.setCarryFlag(0xFF,0x01, false); // set carry flag to 1
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
