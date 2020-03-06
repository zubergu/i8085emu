package com.zubergu.i8085emu.cpu.instructions.branchgroup;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.Instruction;
import com.zubergu.i8085emu.cpu.instructions.JumpCondition;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.memory.Memory;

public class CConditionAddr implements Instruction{
	private String description = "";
	private Cpu cpu;
	private JumpCondition condition;
	
	public CConditionAddr(Cpu cpu, JumpCondition condition) {
		this.cpu = cpu;
		this.condition = condition;
	}

	@Override
	public int execute() {
		Register16Bit sp = cpu.get16BitRegisterForName(Register16BitName.SP);
		Register16Bit pc = cpu.get16BitRegisterForName(Register16BitName.PC);
		Memory memory = cpu.getMemory();
		
		int byte2 = memory.readByte(pc.getValue());
		pc.increment();
		int byte3 = memory.readByte(pc.getValue());
		pc.increment(); // PC now points at the next instruction address, value to save on stack
		
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
			
			
			sp.decrement(); // decrease SP, it points at place where last write WAS ALREADY done, stack grows down thus -1
			memory.writeByte(sp.getValue(), pc.getValue() >>8); // high 8 bits of PC first
			sp.decrement();
			memory.writeByte(sp.getValue(), pc.getValue() & 0xFF); // low 8 bits of PC next
			
			pc.setValue((byte3<<8) + byte2); // set PC to new value after saving it
			
			return 18;
		}
		
		return 9; // condition wasn't met so fewer cycles returned
			
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
}
