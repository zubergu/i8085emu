package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.memory.Memory;

public class CPIdata implements Instruction {
    private Cpu cpu;
	
	public CPIdata(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Memory memory = cpu.getMemory();
		int byte2 = memory.readByte(pc.getValue());
		pc.increment();
		
		int result = accumulator.getValue() - byte2;
		result &= 0xFF;
		cpu.setCarryFlag(accumulator.getValue(), byte2, true);
		cpu.setAuxiliaryCarryFlag(accumulator.getValue(), byte2, true);
		
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
