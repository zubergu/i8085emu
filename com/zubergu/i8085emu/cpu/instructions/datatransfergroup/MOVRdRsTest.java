package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import org.mockito.Mockito;

import static org.mockito.Mockito.*;

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

public class MOVRdRsTest {
	
	@Test
	public void executionWorksCorrectly() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit b = Mockito.mock(Register8Bit.class);
		Register8Bit c = Mockito.mock(Register8Bit.class);

		when(cpu.get8BitRegisterForName(Register8BitName.B)).thenReturn(b);
		when(cpu.get8BitRegisterForName(Register8BitName.C)).thenReturn(c);
		
		when(c.getValue()).thenReturn(0xAB);
		
		MOVRdRs instruction = new MOVRdRs(cpu, Register8BitName.B, Register8BitName.C);
		
		Assert.assertTrue(instruction.execute() == 4);
		verify(b).setValue(0xAB);
		
		
		
	}

}
