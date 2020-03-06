package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;

public class LDAXrp implements Instruction {

	private String description = "";

	private Cpu cpu;
	private RegisterPairName rpName;

	public LDAXrp(Cpu cpu, RegisterPairName rp) {
		this.cpu = cpu;
		this.rpName = rp;
	}

	@Override
	public int execute() {
		RegisterPair rp = cpu.getRegisterPairForName(rpName);
		Register8Bit regA = cpu.get8BitRegisterForName(Register8BitName.A);
		int address = rp.getValue();
		int value = cpu.getMemory().readByte(address);
		regA.setValue(value);
		return 7;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
