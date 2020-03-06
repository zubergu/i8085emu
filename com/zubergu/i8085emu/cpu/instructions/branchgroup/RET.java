package com.zubergu.i8085emu.cpu.instructions.branchgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.memory.Memory;

public class RET implements Instruction {
	private String description = "";
	private Cpu cpu;
	
	public RET(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Register16Bit sp = cpu.get16BitRegisterForName(Register16BitName.SP);
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Memory memory = cpu.getMemory();
		
		int byte2 = memory.readByte(sp.getValue());
		sp.increment();
		int byte3 = memory.readByte(sp.getValue());
		sp.increment(); // SP now points at the last byte that was written to it
		
		pc.setValue((byte3<<8) + byte2); // set PC to new value
		
		return 10;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
}
