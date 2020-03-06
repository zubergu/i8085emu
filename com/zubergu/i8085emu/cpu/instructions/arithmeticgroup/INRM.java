package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class INRM implements Instruction {
	private Cpu cpu;
	
	public INRM(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		RegisterPair hl = cpu.getRegisterPairForName(RegisterPairName.HL);
		Memory memory = cpu.getMemory();
		
		int value = memory.readByte(hl.getValue());
		cpu.setAuxiliaryCarryFlag(value, 1, false);
		memory.writeByte(hl.getValue(), value+1);

		cpu.setZeroFlag(memory.readByte(hl.getValue()));
		cpu.setSignFlag(memory.readByte(hl.getValue()));
		cpu.setParityFlag(memory.readByte(hl.getValue()));
		
		return 10;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
