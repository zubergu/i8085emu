package com.zubergu.i8085emu.cpu.instructions.branchgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.memory.Memory;

public class RSTn implements Instruction {
	private Cpu cpu;
	private int n = 0;
	
	public RSTn(Cpu cpu, int n) {
		this.cpu = cpu;
		this.n = n;
	}
	

	@Override
	public int execute() {
		Register16Bit sp = cpu.get16BitRegisterForName(Register16BitName.SP);
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Memory memory = cpu.getMemory();
		
		sp.decrement(); // decrease SP, it points at place where last write WAS ALREADY done, stack grows down thus -1
		memory.writeByte(sp.getValue(), pc.getValue() >>8); // high 8 bits of PC first
		sp.decrement();
		memory.writeByte(sp.getValue(), pc.getValue() & 0xFF); // low 8 bits of PC next
		
		pc.setValue(n<<3); // PC is set to : (n * 8) thus n<<3
		
		return 12;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
