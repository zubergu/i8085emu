package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class INRr implements Instruction {
	private Cpu cpu;
	private Register8BitName registerName;
	
	public INRr(Cpu cpu, Register8BitName registerName) {
		this.cpu = cpu;
		this.registerName = registerName;
	}

	@Override
	public int execute() {
		Register8Bit register = cpu.get8BitRegisterForName(registerName);
		cpu.setAuxiliaryCarryFlag(register.getValue(), 1, false);
		register.increment();

		cpu.setZeroFlag(register.getValue());
		cpu.setSignFlag(register.getValue());
		cpu.setParityFlag(register.getValue());
		
		return 4;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
