package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

public class POPrpTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		RegisterPair de = Mockito.mock(RegisterPair.class);
		Register16Bit sp = Mockito.mock(Register16Bit.class);
		Register8Bit d = Mockito.mock(Register8Bit.class);
		Register8Bit e = Mockito.mock(Register8Bit.class);

		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get16BitRegisterForName(Register16BitName.SP)).thenReturn(sp);
		when(cpu.getRegisterPairForName(RegisterPairName.DE)).thenReturn(de);
		when(de.getRh()).thenReturn(d);
		when(de.getRl()).thenReturn(e);
		
		when(sp.getValue()).thenReturn(0xFF0A).thenReturn(0xFF0B);
		when(m.readByte(0xFF0A)).thenReturn(0x03);
		when(m.readByte(0xFF0B)).thenReturn(0x04);
		
		POPrp instruction = new POPrp(cpu, RegisterPairName.DE);
		
		Assert.assertEquals(instruction.execute(), 10);
		verify(e).setValue(0x03);
		verify(d).setValue(0x04);
		verify(sp, times(2)).increment();
		
		
		
	}

}
