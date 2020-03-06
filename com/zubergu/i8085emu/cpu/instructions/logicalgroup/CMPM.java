package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class CMPM implements Instruction {
	private Cpu cpu;
	
	public CMPM(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		RegisterPair hl = cpu.getRegisterPairForName(RegisterPairName.HL);
		Memory memory = cpu.getMemory();
		int value = memory.readByte(hl.getValue());
		
		int result = accumulator.getValue() - value;
		result &= 0xFF;
		cpu.setCarryFlag(accumulator.getValue(), value, true);
		cpu.setAuxiliaryCarryFlag(accumulator.getValue(), value, true);
		
		cpu.setZeroFlag(result);
		cpu.setParityFlag(result);
		cpu.setSignFlag(result);
		
		return 7;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
