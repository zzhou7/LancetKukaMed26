package com.kuka.generated.io.impl;

import com.kuka.generated.io.MytestIOIoGroup;
import com.kuka.io.IIoDefinition;
import com.kuka.io.IIoDefinitionProvider;
import com.kuka.io.IIoValueProvider;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Automatically generated class to access to IOs of the sunrise IO group <b>MytestIO</b>.
 * <i>Please, do not modify!</i>
 *
 * <p><b>IO group user description:</b>
 * ./.
 *
 * <p><b>Hint:</b>
 * To work directly with IOs the interfaces {@link IIoDefinitionProvider} and
 * {@link IIoValueProvider} can be used. Both of them can be obtained via Dependency Injection.
 * The {@link IIoDefinitionProvider} allows to access the {@link IIoDefinition} which provides
 * all information of about an IO, e.g. its name, its related group name or the user description.
 * Via the {@link IIoValueProvider} it is possible to request the current value of an IO or set
 * a new value for connected outputs.
 */
@Singleton
public class MytestIOIoGroupImpl implements MytestIOIoGroup {
  private IIoDefinition input1Definition;
  private IIoDefinition input2Definition;
  private IIoDefinition input3Definition;
  private IIoDefinition input4Definition;
  private IIoDefinition input5Definition;
  private IIoDefinition input6Definition;
  private IIoDefinition input7Definition;
  private IIoDefinition input8Definition;
  private IIoDefinition input9Definition;
  private IIoDefinition input10Definition;
  private IIoDefinition input11Definition;
  private IIoDefinition input12Definition;
  private IIoDefinition input13Definition;
  private IIoDefinition input14Definition;
  private IIoDefinition input15Definition;
  private IIoDefinition input16Definition;
  private IIoDefinition output1Definition;
  private IIoDefinition output2Definition;
  private IIoDefinition output3Definition;
  private IIoDefinition output4Definition;
  private IIoDefinition output5Definition;
  private IIoDefinition output6Definition;
  private IIoDefinition output7Definition;
  private IIoDefinition output8Definition;
  private IIoDefinition output9Definition;
  private IIoDefinition output10Definition;
  private IIoDefinition output11Definition;
  private IIoDefinition output12Definition;
  private IIoDefinition output13Definition;
  private IIoDefinition output14Definition;
  private IIoDefinition output15Definition;
  private IIoDefinition output16Definition;

  private IIoValueProvider valueProvider;

  /**
   * Creates a new instance.
   * 
   * @param ioDefinitionProvider
   *        the provider for accessing IO definitions
   * @param ioValueProvider
   *        the provider for accessing IO values
   */
  @Inject
  public MytestIOIoGroupImpl(IIoDefinitionProvider ioDefinitionProvider,
      IIoValueProvider ioValueProvider) {
    valueProvider = ioValueProvider;

    input1Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input1");
    input2Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input2");
    input3Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input3");
    input4Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input4");
    input5Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input5");
    input6Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input6");
    input7Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input7");
    input8Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input8");
    input9Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input9");
    input10Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input10");
    input11Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input11");
    input12Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input12");
    input13Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input13");
    input14Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input14");
    input15Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input15");
    input16Definition = ioDefinitionProvider.getIoDefinition(NAME, "Input16");
    output1Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output1");
    output2Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output2");
    output3Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output3");
    output4Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output4");
    output5Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output5");
    output6Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output6");
    output7Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output7");
    output8Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output8");
    output9Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output9");
    output10Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output10");
    output11Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output11");
    output12Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output12");
    output13Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output13");
    output14Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output14");
    output15Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output15");
    output16Definition = ioDefinitionProvider.getIoDefinition(NAME, "Output16");
  }

  @Override
  public boolean getInput1() {
    return valueProvider.getIoValue(input1Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput1Definition() {
    return input1Definition;
  }

  @Override
  public boolean getInput2() {
    return valueProvider.getIoValue(input2Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput2Definition() {
    return input2Definition;
  }

  @Override
  public boolean getInput3() {
    return valueProvider.getIoValue(input3Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput3Definition() {
    return input3Definition;
  }

  @Override
  public boolean getInput4() {
    return valueProvider.getIoValue(input4Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput4Definition() {
    return input4Definition;
  }

  @Override
  public boolean getInput5() {
    return valueProvider.getIoValue(input5Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput5Definition() {
    return input5Definition;
  }

  @Override
  public boolean getInput6() {
    return valueProvider.getIoValue(input6Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput6Definition() {
    return input6Definition;
  }

  @Override
  public boolean getInput7() {
    return valueProvider.getIoValue(input7Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput7Definition() {
    return input7Definition;
  }

  @Override
  public boolean getInput8() {
    return valueProvider.getIoValue(input8Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput8Definition() {
    return input8Definition;
  }

  @Override
  public boolean getInput9() {
    return valueProvider.getIoValue(input9Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput9Definition() {
    return input9Definition;
  }

  @Override
  public boolean getInput10() {
    return valueProvider.getIoValue(input10Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput10Definition() {
    return input10Definition;
  }

  @Override
  public boolean getInput11() {
    return valueProvider.getIoValue(input11Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput11Definition() {
    return input11Definition;
  }

  @Override
  public boolean getInput12() {
    return valueProvider.getIoValue(input12Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput12Definition() {
    return input12Definition;
  }

  @Override
  public boolean getInput13() {
    return valueProvider.getIoValue(input13Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput13Definition() {
    return input13Definition;
  }

  @Override
  public boolean getInput14() {
    return valueProvider.getIoValue(input14Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput14Definition() {
    return input14Definition;
  }

  @Override
  public boolean getInput15() {
    return valueProvider.getIoValue(input15Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput15Definition() {
    return input15Definition;
  }

  @Override
  public boolean getInput16() {
    return valueProvider.getIoValue(input16Definition).getValueAsBoolean();
  }

  @Override
  public IIoDefinition getInput16Definition() {
    return input16Definition;
  }

  @Override
  public boolean getOutput1() {
    return valueProvider.getIoValue(output1Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput1(boolean value) {
    valueProvider.setValue(output1Definition, value);
  }

  @Override
  public IIoDefinition getOutput1Definition() {
    return output1Definition;
  }

  @Override
  public boolean getOutput2() {
    return valueProvider.getIoValue(output2Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput2(boolean value) {
    valueProvider.setValue(output2Definition, value);
  }

  @Override
  public IIoDefinition getOutput2Definition() {
    return output2Definition;
  }

  @Override
  public boolean getOutput3() {
    return valueProvider.getIoValue(output3Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput3(boolean value) {
    valueProvider.setValue(output3Definition, value);
  }

  @Override
  public IIoDefinition getOutput3Definition() {
    return output3Definition;
  }

  @Override
  public boolean getOutput4() {
    return valueProvider.getIoValue(output4Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput4(boolean value) {
    valueProvider.setValue(output4Definition, value);
  }

  @Override
  public IIoDefinition getOutput4Definition() {
    return output4Definition;
  }

  @Override
  public boolean getOutput5() {
    return valueProvider.getIoValue(output5Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput5(boolean value) {
    valueProvider.setValue(output5Definition, value);
  }

  @Override
  public IIoDefinition getOutput5Definition() {
    return output5Definition;
  }

  @Override
  public boolean getOutput6() {
    return valueProvider.getIoValue(output6Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput6(boolean value) {
    valueProvider.setValue(output6Definition, value);
  }

  @Override
  public IIoDefinition getOutput6Definition() {
    return output6Definition;
  }

  @Override
  public boolean getOutput7() {
    return valueProvider.getIoValue(output7Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput7(boolean value) {
    valueProvider.setValue(output7Definition, value);
  }

  @Override
  public IIoDefinition getOutput7Definition() {
    return output7Definition;
  }

  @Override
  public boolean getOutput8() {
    return valueProvider.getIoValue(output8Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput8(boolean value) {
    valueProvider.setValue(output8Definition, value);
  }

  @Override
  public IIoDefinition getOutput8Definition() {
    return output8Definition;
  }

  @Override
  public boolean getOutput9() {
    return valueProvider.getIoValue(output9Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput9(boolean value) {
    valueProvider.setValue(output9Definition, value);
  }

  @Override
  public IIoDefinition getOutput9Definition() {
    return output9Definition;
  }

  @Override
  public boolean getOutput10() {
    return valueProvider.getIoValue(output10Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput10(boolean value) {
    valueProvider.setValue(output10Definition, value);
  }

  @Override
  public IIoDefinition getOutput10Definition() {
    return output10Definition;
  }

  @Override
  public boolean getOutput11() {
    return valueProvider.getIoValue(output11Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput11(boolean value) {
    valueProvider.setValue(output11Definition, value);
  }

  @Override
  public IIoDefinition getOutput11Definition() {
    return output11Definition;
  }

  @Override
  public boolean getOutput12() {
    return valueProvider.getIoValue(output12Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput12(boolean value) {
    valueProvider.setValue(output12Definition, value);
  }

  @Override
  public IIoDefinition getOutput12Definition() {
    return output12Definition;
  }

  @Override
  public boolean getOutput13() {
    return valueProvider.getIoValue(output13Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput13(boolean value) {
    valueProvider.setValue(output13Definition, value);
  }

  @Override
  public IIoDefinition getOutput13Definition() {
    return output13Definition;
  }

  @Override
  public boolean getOutput14() {
    return valueProvider.getIoValue(output14Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput14(boolean value) {
    valueProvider.setValue(output14Definition, value);
  }

  @Override
  public IIoDefinition getOutput14Definition() {
    return output14Definition;
  }

  @Override
  public boolean getOutput15() {
    return valueProvider.getIoValue(output15Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput15(boolean value) {
    valueProvider.setValue(output15Definition, value);
  }

  @Override
  public IIoDefinition getOutput15Definition() {
    return output15Definition;
  }

  @Override
  public boolean getOutput16() {
    return valueProvider.getIoValue(output16Definition).getValueAsBoolean();
  }

  @Override
  public void setOutput16(boolean value) {
    valueProvider.setValue(output16Definition, value);
  }

  @Override
  public IIoDefinition getOutput16Definition() {
    return output16Definition;
  }

}
