package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class XTHL implements Instruction {
	private Cpu cpu;
	
	public XTHL(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		Register16Bit sp = cpu.get16BitRegisterForName(Register16BitName.SP);
		Register8Bit h = cpu.get8BitRegisterForName(Register8BitName.H);
		Register8Bit l = cpu.get8BitRegisterForName(Register8BitName.L);
		
		l.setValue(memory.readByte(sp.getValue()));
		h.setValue(memory.readByte(sp.getValue()+1));
		
		return 16;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
