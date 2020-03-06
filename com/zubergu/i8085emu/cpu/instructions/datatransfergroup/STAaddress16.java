package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.memory.Memory;

public class STAaddress16 implements Instruction {
	private Cpu cpu;
	
	public STAaddress16(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		
		int byte2 = memory.readByte(pc.getValue());
		pc.increment();
		int byte3 = memory.readByte(pc.getValue());
		pc.increment();
		int address = (byte3<<8) | byte2;
		memory.writeByte(address, accumulator.getValue());
		return 13;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
