package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class SHLDaddress16 implements Instruction {
	private String description = "";
	private Cpu cpu;

	public SHLDaddress16(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		Register8Bit h = cpu.get8BitRegisterForName(Register8BitName.H);
		Register8Bit l = cpu.get8BitRegisterForName(Register8BitName.L);
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		int byte2 = memory.readByte(pc.getValue());
		pc.increment();
		int byte3 = memory.readByte(pc.getValue());
		pc.increment();
		int address = ((byte3 << 8) | byte2) & 0xFFFF;
		memory.writeByte(address, l.getValue());
		memory.writeByte(address + 1, h.getValue());
		return 16;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
