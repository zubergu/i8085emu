package com.zubergu.i8085emu.cpu.instructions.branchgroup;

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

public class PCHLTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);

		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(hl.getValue()).thenReturn(0xFAFB);
		
		PCHL instruction = new PCHL(cpu);
		
		Assert.assertEquals(instruction.execute(), 6);

		verify(pc).setValue(0xFAFB);
		
	}

}
