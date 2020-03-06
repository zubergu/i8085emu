package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.memory.Memory;

public class INport implements Instruction {
	
	private Cpu cpu;
	
	public INport(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Register8Bit accumulator = cpu.get8BitRegisterForName(Register8BitName.A);
		Memory memory = cpu.getMemory();
		
		int port = memory.readByte(pc.getValue());
		pc.increment();
		
		int portValue = memory.readPort(port);
		accumulator.setValue(portValue);
		
		return 10;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
