package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.memory.Memory;

public class SBIdata implements Instruction {
	private String description = "";
	private Cpu cpu;
	
	public SBIdata(Cpu cpu) {
		this.cpu = cpu;
	}
	
	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		int byte2 = memory.readByte(pc.getValue());
		pc.increment();
		int borrow = cpu.carryFlagIsSet() ? 1:0;
		
		int result = accumulator.getValue() - byte2 - borrow;
		cpu.setCarryFlag(accumulator.getValue(), byte2 + borrow, true);
		cpu.setAuxiliaryCarryFlag(accumulator.getValue(), byte2 + borrow, true);
		
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
