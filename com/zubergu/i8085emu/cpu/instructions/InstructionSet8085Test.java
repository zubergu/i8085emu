package com.zubergu.i8085emu.cpu.instructions;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.datatransfergroup.STAXrp;

public class InstructionSet8085Test {
	
	private static final Integer nonexistingOpcode = 0x08;
	private static final Integer existingOpcode = 0x02;
	
	@Test(expectedExceptions = {UnimplementedInstructionException.class})
	public void correctExceptionGetsThrown() throws UnimplementedInstructionException {
		Cpu cpu = Mockito.mock(Cpu.class);
		
		InstructionSet is = new InstructionSet8085(cpu);
		
		is.decode(nonexistingOpcode);
	}
	
	@Test
	public void decodingOfExestingOpcodeWorks() throws UnimplementedInstructionException {
		Cpu cpu = Mockito.mock(Cpu.class);
		InstructionSet is = new InstructionSet8085(cpu);
		
		Instruction instruction = is.decode(existingOpcode);
		
		Assert.assertTrue(instruction instanceof STAXrp);
	}

}
