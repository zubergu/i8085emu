package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;

public class XCHG implements Instruction {
	
	private Cpu cpu;
	
	public XCHG(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Register8Bit h = cpu.get8BitRegisterForName(Register8BitName.H);
		Register8Bit l = cpu.get8BitRegisterForName(Register8BitName.L);
		Register8Bit d = cpu.get8BitRegisterForName(Register8BitName.D);
		Register8Bit e = cpu.get8BitRegisterForName(Register8BitName.E);
		int temp = h.getValue();
		h.setValue(d.getValue());
		d.setValue(temp);
		temp = l.getValue();
		l.setValue(e.getValue());
		e.setValue(temp);
		return 4;
	}


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
