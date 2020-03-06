package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class MOVRdM implements Instruction {
	private String description = "";
	private Cpu cpu;
	private Register8BitName destinationName;
	
	public MOVRdM(Cpu cpu, Register8BitName destinationName) {
		this.cpu = cpu;
		this.destinationName = destinationName;
	}

	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		Register8Bit register = cpu.get8BitRegisterForName(destinationName);
		int address =  cpu.getRegisterPairForName(RegisterPairName.HL).getValue();
		int value = memory.readByte(address);
		register.setValue(value);
		return 7;
	}


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}

}
