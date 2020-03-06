package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class ORAr implements Instruction {
	private Cpu cpu;
	private Register8BitName registerName;
	
	public ORAr(Cpu cpu, Register8BitName registerName) {
		this.cpu = cpu;
		this.registerName = registerName;
	}

	@Override
	public int execute() {
		Register8Bit register = cpu.get8BitRegisterForName(registerName);
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		accumulator.setValue(accumulator.getValue() | register.getValue());
		
		cpu.setCarryFlag(0x00, 1, false);
		cpu.setAuxiliaryCarryFlag(0x00, 1, false);
		
		cpu.setZeroFlag(accumulator.getValue());
		cpu.setSignFlag(accumulator.getValue());
		cpu.setParityFlag(accumulator.getValue());
		
		
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
