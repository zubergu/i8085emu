package com.zubergu.i8085emu.cpu.instructions.branchgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.instructions.JumpCondition;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.memory.Memory;

public class Rcondition implements Instruction {
	private String description = "";
	private Cpu cpu;
	private JumpCondition condition;
	
	public Rcondition(Cpu cpu, JumpCondition condition) {
		this.cpu = cpu;
		this.condition = condition;
	}

	@Override
	public int execute() {
		Register16Bit sp = cpu.get16BitRegisterForName(Register16BitName.SP);
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Memory memory = cpu.getMemory();
		boolean conditionIsMet = false;
		
		
		
		switch(condition) {
		case Z: 
			conditionIsMet = cpu.zeroFlagIsSet();
			break;
		case NZ: 
			conditionIsMet = !cpu.zeroFlagIsSet();
			break;
		case C: 
			conditionIsMet = cpu.carryFlagIsSet();
			break;
		case NC: 
			conditionIsMet = !cpu.carryFlagIsSet();
			break;
		case PE:
			conditionIsMet = cpu.parityFlagIsSet();
			break;
		case PO: 
			conditionIsMet = !cpu.parityFlagIsSet();
			break;
		case P:
			conditionIsMet = !cpu.signFlagIsSet();
			break;

		case M:
			conditionIsMet = cpu.signFlagIsSet();
			break;
		}
		if(conditionIsMet) {
			int byte2 = memory.readByte(sp.getValue());
			sp.increment();
			int byte3 = memory.readByte(sp.getValue());
			sp.increment(); // SP now points at the last byte that was written to it
			pc.setValue((byte3<<8) + byte2); // set PC to new value
			return 12;
		}
		
		return 6;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
}
