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

public class DADrpTest {
	
	@Test
	public void correctInstructionExecutionRPcarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		RegisterPair bc = Mockito.mock(RegisterPair.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(hl.getValue()).thenReturn(0xFFFF);
		when(cpu.getRegisterPairForName(RegisterPairName.BC)).thenReturn(bc);
		when(bc.getValue()).thenReturn(0x0101);
		
		DADrp instruction = new DADrp(cpu, RegisterPairName.BC);
		
		Assert.assertEquals(instruction.execute(), 10);
		
		verify(cpu).setCarryFlag(0xFF, 0x01, false);
		verify(hl).setValue(0xFFFF + 0x0101);
		
	}
	
	@Test
	public void correctInstructionExecutionRPcarryReset() {
		Cpu cpu = Mockito.mock(Cpu.class);
		RegisterPair bc = Mockito.mock(RegisterPair.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(hl.getValue()).thenReturn(0xFAFB);
		when(cpu.getRegisterPairForName(RegisterPairName.BC)).thenReturn(bc);
		when(bc.getValue()).thenReturn(0x0101);
		
		DADrp instruction = new DADrp(cpu, RegisterPairName.BC);
		
		Assert.assertEquals(instruction.execute(), 10);
		
		verify(cpu).setCarryFlag(0x00, 0x01, false);
		verify(hl).setValue(0xFBFC);
		
	}
	
	@Test
	public void correctInstructionExecutionSP() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register16Bit sp = Mockito.mock(Register16Bit.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(hl.getValue()).thenReturn(0xFAFB);
		when(cpu.get16BitRegisterForName(Register16BitName.SP)).thenReturn(sp);
		when(sp.getValue()).thenReturn(0x0101);
		
		DADrp instruction = new DADrp(cpu, Register16BitName.SP);
		
		Assert.assertEquals(instruction.execute(), 10);
		
		verify(cpu).setCarryFlag(0x00, 0x01, false);
		verify(hl).setValue(0xFBFC);
		
	}
	
	

}

