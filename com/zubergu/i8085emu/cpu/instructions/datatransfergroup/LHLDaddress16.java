package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class LHLDaddress16 implements Instruction {
	
	private Cpu cpu;
	
	public LHLDaddress16(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public int execute() {
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Memory m = cpu.getMemory();
		Register8Bit h = cpu.get8BitRegisterForName(Register8BitName.H);
		Register8Bit l = cpu.get8BitRegisterForName(Register8BitName.L);
		int byte2 = m.readByte(pc.getValue());
		pc.increment();
		int byte3 = m.readByte(pc.getValue());
		pc.increment();
		
		int address = ((byte3 << 8) | byte2) & 0xFFFF;
 		l.setValue(m.readByte(address));
 		h.setValue(m.readByte(address+1));
		return 16;
	}


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
