package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class MOVMRs implements Instruction {
	private String description = "";
	private Cpu cpu;
	private Register8BitName registerName;
	
	public MOVMRs(Cpu cpu, Register8BitName registerName) {
		this.cpu = cpu;
		this.registerName = registerName;
	}

	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		Register8Bit register = cpu.get8BitRegisterForName(registerName);
		int address =  cpu.getRegisterPairForName(RegisterPairName.HL).getValue();
		memory.writeByte(address, register.getValue());
		return 7;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}

}
