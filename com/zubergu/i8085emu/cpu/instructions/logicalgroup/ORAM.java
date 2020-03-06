package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class ORAM implements Instruction {
	private Cpu cpu;

	
	public ORAM(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		RegisterPair hl = cpu.getRegisterPairForName(RegisterPairName.HL);
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		Memory memory = cpu.getMemory();
		accumulator.setValue(accumulator.getValue() | memory.readByte(hl.getValue()));
		
		cpu.setCarryFlag(0x00, 1, false);
		cpu.setAuxiliaryCarryFlag(0x00, 1, false);
		
		cpu.setZeroFlag(accumulator.getValue());
		cpu.setSignFlag(accumulator.getValue());
		cpu.setParityFlag(accumulator.getValue());
		
		
		return 7;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
