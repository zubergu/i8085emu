package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;

public class HLT implements Instruction {
	private Cpu cpu;
	
	public HLT(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		cpu.setHaltState(true);
		return 5;
	}

	@Override
	public String getDescription() {
		return null;
	}

}
