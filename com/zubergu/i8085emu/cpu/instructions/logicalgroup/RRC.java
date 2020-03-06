package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class RRC implements Instruction {
	private Cpu cpu;
	
	public RRC(Cpu cpu) {
		this.cpu = cpu;
	}


	@Override
	public int execute() {
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		int value = accumulator.getValue();
		int shifted = value & 0x01;
		value = value >> 1;
		shifted = shifted << 7;
		
		value = value + shifted;
		accumulator.setValue(value);
		
		if(shifted == 0 ) {
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
