package com.zubergu.i8085emu.gui.views;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class CpuView extends JFrame {

	private final String[] conditionsValues = { "==", "!=", "<", "<=", ">",
			">=" };

	private Map<Register8BitName, JTextField> name8bitToField = new HashMap<Register8BitName, JTextField>();
	private Map<Register8BitName, JCheckBox> name8bitToBreakpoint = new HashMap<Register8BitName, JCheckBox>();
	private Map<Register8BitName, JComboBox<String>> name8bitToCondition = new HashMap<Register8BitName, JComboBox<String>>();
	private Map<Register8BitName, JTextField> name8bitBreakpointValue = new HashMap<Register8BitName, JTextField>();
	private Map<Register16BitName, JTextField> name16bitToField = new HashMap<Register16BitName, JTextField>();
	private Map<Register16BitName, JCheckBox> name16bitToBreakpoint = new HashMap<Register16BitName, JCheckBox>();
	private Map<Register16BitName, JComboBox<String>> name16bitToCondition = new HashMap<Register16BitName, JComboBox<String>>();
	private Map<Register16BitName, JTextField> name16bitBreakpointValue = new HashMap<Register16BitName, JTextField>();

	private JLabel lA = new JLabel("A");
	private JLabel lPSW = new JLabel("PSW");
	private JLabel lB = new JLabel("B");
	private JLabel lC = new JLabel("C");
	private JLabel lD = new JLabel("D");
	private JLabel lE = new JLabel("E");
	private JLabel lH = new JLabel("H");
	private JLabel lL = new JLabel("L");
	private JLabel lPC = new JLabel("PC");
	private JLabel lSP = new JLabel("SP");

	private JTextField fA = new JTextField();
	private JTextField fPSW = new JTextField();
	private JTextField fB = new JTextField();
	private JTextField fC = new JTextField();
	private JTextField fD = new JTextField();
	private JTextField fE = new JTextField();
	private JTextField fH = new JTextField();
	private JTextField fL = new JTextField();
	private JTextField fPC = new JTextField();
	private JTextField fSP = new JTextField();

	private JCheckBox breakpointA = new JCheckBox();
	private JCheckBox breakpointPSW = new JCheckBox();
	private JCheckBox breakpointB = new JCheckBox();
	private JCheckBox breakpointC = new JCheckBox();
	private JCheckBox breakpointD = new JCheckBox();
	private JCheckBox breakpointE = new JCheckBox();
	private JCheckBox breakpointH = new JCheckBox();
	private JCheckBox breakpointL = new JCheckBox();
	private JCheckBox breakpointPC = new JCheckBox();
	private JCheckBox breakpointSP = new JCheckBox();

	private JComboBox<String> condA = new JComboBox<String>(conditionsValues);
	private JComboBox<String> condPSW = new JComboBox<String>(conditionsValues);
	private JComboBox<String> condB = new JComboBox<String>(conditionsValues);
	private JComboBox<String> condC = new JComboBox<String>(conditionsValues);
	private JComboBox<String> condD = new JComboBox<String>(conditionsValues);
	private JComboBox<String> condE = new JComboBox<String>(conditionsValues);
	private JComboBox<String> condH = new JComboBox<String>(conditionsValues);
	private JComboBox<String> condL = new JComboBox<String>(conditionsValues);
	private JComboBox<String> condPC = new JComboBox<String>(conditionsValues);
	private JComboBox<String> condSP = new JComboBox<String>(conditionsValues);

	private JTextField breakpointValueFieldA = new JTextField();
	private JTextField breakpointValueFieldPSW = new JTextField();
	private JTextField breakpointValueFieldB = new JTextField();
	private JTextField breakpointValueFieldC = new JTextField();
	private JTextField breakpointValueFieldD = new JTextField();
	private JTextField breakpointValueFieldE = new JTextField();
	private JTextField breakpointValueFieldH = new JTextField();
	private JTextField breakpointValueFieldL = new JTextField();
	private JTextField breakpointValueFieldPC = new JTextField();
	private JTextField breakpointValueFieldSP = new JTextField();

	public CpuView() {
		setTitle("CPU VIEW");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		createFields();
	}

	private void createFields() {
		setLayout(new GridLayout(0, 5));

		add(new JLabel("Register"));
		add(new JLabel("Value"));
		add(new JLabel("Breakpoint"));
		add(new JLabel("Condition"));
		add(new JLabel("Break value"));
		
		add(lA);
		add(fA);
		name8bitToField.put(Register8BitName.A, fA);
		add(breakpointA);
		name8bitToBreakpoint.put(Register8BitName.A, breakpointA);
		add(condA);
		name8bitToCondition.put(Register8BitName.A, condA);
		add(breakpointValueFieldA);
		name8bitBreakpointValue.put(Register8BitName.A, breakpointValueFieldA);
		
		
		add(lPSW);
		add(fPSW);
		name8bitToField.put(Register8BitName.PSW, fPSW);
		add(breakpointPSW);
		name8bitToBreakpoint.put(Register8BitName.PSW, breakpointPSW);
		add(condPSW);
		name8bitToCondition.put(Register8BitName.PSW, condPSW);
		add(breakpointValueFieldPSW);
		name8bitBreakpointValue.put(Register8BitName.PSW, breakpointValueFieldPSW);
		
		
		add(lB);
		add(fB);
		name8bitToField.put(Register8BitName.B, fB);
		add(breakpointB);
		name8bitToBreakpoint.put(Register8BitName.B, breakpointB);
		add(condB);
		name8bitToCondition.put(Register8BitName.B, condB);
		add(breakpointValueFieldB);
		name8bitBreakpointValue.put(Register8BitName.B, breakpointValueFieldB);
		
		
		add(lC);
		add(fC);
		name8bitToField.put(Register8BitName.C, fC);
		add(breakpointC);
		name8bitToBreakpoint.put(Register8BitName.C, breakpointC);
		add(condC);
		name8bitToCondition.put(Register8BitName.C, condC);
		add(breakpointValueFieldC);
		name8bitBreakpointValue.put(Register8BitName.C, breakpointValueFieldC);
		
		
		
		add(lD);
		add(fD);
		name8bitToField.put(Register8BitName.D, fD);
		add(breakpointD);
		name8bitToBreakpoint.put(Register8BitName.D, breakpointD);
		add(condD);
		name8bitToCondition.put(Register8BitName.D, condD);
		add(breakpointValueFieldD);
		name8bitBreakpointValue.put(Register8BitName.D, breakpointValueFieldD);
		
		
		
		add(lE);
		add(fE);
		name8bitToField.put(Register8BitName.E, fE);
		add(breakpointE);
		name8bitToBreakpoint.put(Register8BitName.E, breakpointE);
		add(condE);
		name8bitToCondition.put(Register8BitName.E, condE);
		add(breakpointValueFieldE);
		name8bitBreakpointValue.put(Register8BitName.E, breakpointValueFieldE);
		
		
		
		add(lH);
		add(fH);
		name8bitToField.put(Register8BitName.H, fH);
		add(breakpointH);
		name8bitToBreakpoint.put(Register8BitName.H, breakpointH);
		add(condH);
		name8bitToCondition.put(Register8BitName.H, condH);
		add(breakpointValueFieldH);
		name8bitBreakpointValue.put(Register8BitName.H, breakpointValueFieldH);
		
		
		
		add(lL);
		add(fL);
		name8bitToField.put(Register8BitName.L, fL);
		add(breakpointL);
		name8bitToBreakpoint.put(Register8BitName.L, breakpointL);
		add(condL);
		name8bitToCondition.put(Register8BitName.L, condL);
		add(breakpointValueFieldL);
		name8bitBreakpointValue.put(Register8BitName.L, breakpointValueFieldL);
		
		
		
		add(lPC);
		add(fPC);
		name16bitToField.put(Register16BitName.PC, fPC);
		add(breakpointPC);
		name16bitToBreakpoint.put(Register16BitName.PC, breakpointPC);
		add(condPC);
		name16bitToCondition.put(Register16BitName.PC, condPC);
		add(breakpointValueFieldPC);
		name16bitBreakpointValue.put(Register16BitName.PC, breakpointValueFieldPC);
		
		
		
		
		
		add(lSP);
		add(fSP);
		name16bitToField.put(Register16BitName.SP, fSP);
		add(breakpointSP);
		name16bitToBreakpoint.put(Register16BitName.SP, breakpointSP);
		add(condSP);
		name16bitToCondition.put(Register16BitName.SP, condSP);
		add(breakpointValueFieldSP);
		name16bitBreakpointValue.put(Register16BitName.SP, breakpointValueFieldSP);

		fA.setEditable(false);
		fA.setHorizontalAlignment(JTextField.RIGHT);
		fPSW.setEditable(false);
		fPSW.setHorizontalAlignment(JTextField.RIGHT);
		fB.setEditable(false);
		fB.setHorizontalAlignment(JTextField.RIGHT);
		fC.setEditable(false);
		fC.setHorizontalAlignment(JTextField.RIGHT);
		fD.setEditable(false);
		fD.setHorizontalAlignment(JTextField.RIGHT);
		fE.setEditable(false);
		fE.setHorizontalAlignment(JTextField.RIGHT);
		fH.setEditable(false);
		fH.setHorizontalAlignment(JTextField.RIGHT);
		fL.setEditable(false);
		fL.setHorizontalAlignment(JTextField.RIGHT);
		fPC.setEditable(false);
		fPC.setHorizontalAlignment(JTextField.RIGHT);
		fSP.setEditable(false);
		fSP.setHorizontalAlignment(JTextField.RIGHT);
	}

	public void setRegisterValue(Register8BitName registerName, int value) {
		JTextField fieldToChange = name8bitToField.get(registerName);
		if (fieldToChange != null) {
			fieldToChange.setText(Integer.toString(value));
		}
	}

	public void setRegisterValue(Register16BitName registerName, int value) {
		JTextField fieldToChange = name16bitToField.get(registerName);
		if (fieldToChange != null) {
			fieldToChange.setText(Integer.toString(value));
		}
	}

	public int getRegisterValue(Register8BitName registerName) {
		JTextField fieldToCheck = name8bitToField.get(registerName);
		return Integer.parseInt(fieldToCheck.getText());
	}

	public int getRegisterValue(Register16BitName registerName) {
		JTextField fieldToCheck = name16bitToField.get(registerName);
		return Integer.parseInt(fieldToCheck.getText());
	}
	
	public boolean getBreakpointSet(Register8BitName registerName) {
		return name8bitToBreakpoint.get(registerName).isSelected();
	}
	
	public boolean getBreakpointSet(Register16BitName registerName) {
		return name16bitToBreakpoint.get(registerName).isSelected();
	}
	
	public String getBreakpointCondition(Register8BitName registerName) {
		return (String) name8bitToCondition.get(registerName).getSelectedItem();
	}
	
	public String getBreakpointCondition(Register16BitName registerName) {
		return (String) name16bitToCondition.get(registerName).getSelectedItem();
	}
	
	public Integer getBreakpointConditionValue(Register8BitName registerName) {
		Integer val = null;
		try {
			val = Integer.parseInt(name8bitBreakpointValue.get(registerName).getText());
		} catch (NumberFormatException ex){
			val = null;
		}
		return val;
	}
	
	public Integer getBreakpointConditionValue(Register16BitName registerName) {
		Integer val = null;
		try {
			val = Integer.parseInt(name16bitBreakpointValue.get(registerName).getText());
		} catch (NumberFormatException ex){
			val = null;
		}
		return val;
	}
	
}
