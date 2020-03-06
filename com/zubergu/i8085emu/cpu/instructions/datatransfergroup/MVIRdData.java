package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.memory.Memory;

public class MVIRdData implements Instruction {
	
	private Cpu cpu;
	private Register8BitName destination;
	
	public MVIRdData(Cpu cpu, Register8BitName destination) {
		this.cpu = cpu;
		this.destination = destination;
	}

	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Register8Bit destinationRegister = cpu.get8BitRegisterForName(destination);
		int value = memory.readByte(pc.getValue());
		pc.increment();
		destinationRegister.setValue(value);
		return 7;
	}


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
