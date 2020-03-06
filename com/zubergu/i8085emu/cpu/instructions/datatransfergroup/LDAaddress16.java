package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class LDAaddress16 implements Instruction {
	private final String description = "LDA:" + "Load Accumulator Directly\n"
			+ "(A) <- ((byte3)(byte2))";
	private Cpu cpu;

	public LDAaddress16(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Register16Bit regPC = cpu.get16BitRegisterForName(Register16BitName.PC);
		int byte2 = cpu.getMemory().readByte(regPC.getValue());
		regPC.increment();
		int byte3 = cpu.getMemory().readByte(regPC.getValue());
		regPC.increment();
		Register8Bit regA = cpu.get8BitRegisterForName(Register8BitName.A);
		int value = cpu.getMemory().readByte(byte3 << 8 | byte2);
		regA.setValue(value);
		return 13;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
