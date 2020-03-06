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

public class XCHGTest {
	
	@Test
	public void executionWorksCorrectly() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit h = Mockito.mock(Register8Bit.class);
		Register8Bit l = Mockito.mock(Register8Bit.class);
		Register8Bit d = Mockito.mock(Register8Bit.class);
		Register8Bit e = Mockito.mock(Register8Bit.class);
		
		when(cpu.get8BitRegisterForName(Register8BitName.H)).thenReturn(h);
		when(cpu.get8BitRegisterForName(Register8BitName.L)).thenReturn(l);
		when(cpu.get8BitRegisterForName(Register8BitName.D)).thenReturn(d);
		when(cpu.get8BitRegisterForName(Register8BitName.E)).thenReturn(e);
		
		
		when(h.getValue()).thenReturn(0xAB);
		when(l.getValue()).thenReturn(0xCD);
		when(d.getValue()).thenReturn(0xFA);
		when(e.getValue()).thenReturn(0xFB);
		
		XCHG instruction = new XCHG(cpu);
		
		Assert.assertTrue(instruction.execute() == 4);
		verify(h).setValue(0xFA);
		verify(l).setValue(0xFB);
		verify(d).setValue(0xAB);
		verify(e).setValue(0xCD);
		
		
	}

}
