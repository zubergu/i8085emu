package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class MVIMdata implements Instruction {
	
	private Cpu cpu;
	
	public MVIMdata(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Memory memory = cpu.getMemory();
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		RegisterPair hl = cpu.getRegisterPairForName(RegisterPairName.HL);
		
		int byte2 = memory.readByte(pc.getValue());
		pc.increment();
		memory.writeByte(hl.getValue(), byte2);
		return 10;
	}


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
