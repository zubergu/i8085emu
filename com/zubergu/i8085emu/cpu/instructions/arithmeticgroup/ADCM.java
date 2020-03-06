package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class ADCM implements Instruction {
	private String description = "";
	private Cpu cpu;
	
	public ADCM(Cpu cpu) {
		this.cpu = cpu;
	}
	
	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		RegisterPair hl = cpu.getRegisterPairForName(RegisterPairName.HL);
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		int carry = cpu.carryFlagIsSet() ? 1:0;
		int result = accumulator.getValue() + memory.readByte(hl.getValue()) + carry;
		cpu.setCarryFlag(accumulator.getValue(), memory.readByte(hl.getValue()) + carry, false);
		cpu.setAuxiliaryCarryFlag(accumulator.getValue(), memory.readByte(hl.getValue()) + carry, false);
		accumulator.setValue(result);
		

		cpu.setZeroFlag(accumulator.getValue());
		cpu.setSignFlag(accumulator.getValue());
		cpu.setParityFlag(accumulator.getValue());
		
		return 7;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
}
