package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;


import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class DCXrpTest {
	
	@Test
	public void correctInstructionExecutionRP() {
		Cpu cpu = Mockito.mock(Cpu.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		
		DCXrp instruction = new DCXrp(cpu, RegisterPairName.HL);
		
		Assert.assertEquals(instruction.execute(), 6);
		
		verify(hl).decrement();
		
	}
	
	@Test
	public void correctInstructionExecutionSP() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register16Bit sp = Mockito.mock(Register16Bit.class);
		
		when(cpu.get16BitRegisterForName(Register16BitName.SP)).thenReturn(sp);
		
		DCXrp instruction = new DCXrp(cpu, Register16BitName.SP);
		
		Assert.assertEquals(instruction.execute(), 6);
		
		verify(sp).decrement();
		
	}

}

