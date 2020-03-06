package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class CMPr implements Instruction {
	
	private Cpu cpu;
	private Register8BitName registerName;
	
	public CMPr(Cpu cpu, Register8BitName registerName) {
		this.cpu = cpu;
		this.registerName = registerName;
	}

	@Override
	public int execute() {
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		Register8Bit register = cpu.get8BitRegisterForName(registerName);
		
		int result = accumulator.getValue() - register.getValue();
		result &= 0xFF;
		cpu.setCarryFlag(accumulator.getValue(), register.getValue(), true);
		cpu.setAuxiliaryCarryFlag(accumulator.getValue(), register.getValue(), true);
		
		cpu.setZeroFlag(result);
		cpu.setParityFlag(result);
		cpu.setSignFlag(result);
		
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
