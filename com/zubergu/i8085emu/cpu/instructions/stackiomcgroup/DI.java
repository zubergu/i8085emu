package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;

public class DI implements Instruction {
private Cpu cpu;
	
	public DI(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		cpu.setInterruptsEnabled(false);
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
