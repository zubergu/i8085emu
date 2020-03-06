package com.zubergu.i8085emu.cpu.instructions.branchgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;

public class PCHL implements Instruction {
	private Cpu cpu;
	
	public PCHL(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		RegisterPair hl = cpu.getRegisterPairForName(RegisterPairName.HL);
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		
		pc.setValue(hl.getValue());
		
		return 6;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
