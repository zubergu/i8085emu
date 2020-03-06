package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;

public class EI implements Instruction {
	private Cpu cpu;
	
	public EI(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		cpu.setInterruptsEnabled(true);
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
