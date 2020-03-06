package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;

public class CMC implements Instruction {

	private Cpu cpu;
	
	public CMC(Cpu cpu){
		this.cpu = cpu;
	}
	@Override
	public int execute() {
		boolean carry = cpu.carryFlagIsSet();
		if(carry) {
			cpu.setCarryFlag(0x00, 0x01, false);
		} else {
			cpu.setCarryFlag(0xFF, 0x01, false);
		}
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
