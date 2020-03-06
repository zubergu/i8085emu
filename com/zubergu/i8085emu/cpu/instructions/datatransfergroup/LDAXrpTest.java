package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class LDAXrpTest {
	
	@Test
	public void executionWorksCorrectly() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory memory = Mockito.mock(Memory.class);
		RegisterPair rp = Mockito.mock(RegisterPair.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		
		when(cpu.getRegisterPairForName(RegisterPairName.BC)).thenReturn(rp);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.getMemory()).thenReturn(memory);
		
		when(rp.getValue()).thenReturn(0xFEBC);
		when(memory.readByte(0xFEBC)).thenReturn(0xBB);
		
		LDAXrp instruction = new LDAXrp(cpu, RegisterPairName.BC);
		
		Assert.assertTrue(instruction.execute() == 7);
		verify(a).setValue(0xBB);
		
	}

}
