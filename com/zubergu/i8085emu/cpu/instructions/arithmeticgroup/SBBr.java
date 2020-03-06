package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class SBBr implements Instruction {
	private Cpu cpu;
	private Register8BitName registerName;
	
	public SBBr(Cpu cpu, Register8BitName registerName) {
		this.cpu = cpu;
		this.registerName = registerName;
	}

	@Override
	public int execute() {
		Register8Bit register = cpu.get8BitRegisterForName(registerName);
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		int borrow = cpu.carryFlagIsSet() ? 1:0;
		int result = accumulator.getValue() - register.getValue() - borrow;
		
		cpu.setCarryFlag(accumulator.getValue(), register.getValue() + borrow, true);
		cpu.setAuxiliaryCarryFlag(accumulator.getValue(), register.getValue() + borrow, true);
		
		accumulator.setValue(result);

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
