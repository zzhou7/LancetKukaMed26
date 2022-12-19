package com.kuka.generated.io;

import com.kuka.generated.io.impl.MytestIOIoGroupImpl;
import com.kuka.inject.ImplementedBy;
import com.kuka.io.IIoDefinition;
import com.kuka.io.IIoDefinitionProvider;
import com.kuka.io.IIoValueProvider;

/**
 * Automatically generated interface to access to IOs of the sunrise IO group <b>MytestIO</b>.
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
 *
 * <p>To use your own mocked implementation, replace the class in the ImplementedBy annotation of
 * this interface.<br>
 * <i>Note: This will be reset if the code is newly generated after changing the IO
 * configuration.</i>
 */
@ImplementedBy(MytestIOIoGroupImpl.class)
public interface MytestIOIoGroup {

  /** The name of the IO group as it is configured in WorkVisual. */
  String NAME = "MytestIO";

  /**
   * Gets the value of the <b>input '<i>Input1</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input1'
   */
  boolean getInput1();

  /**
   * Gets the definition of the <b>input '<i>Input1</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input1'
   */
  IIoDefinition getInput1Definition();

  /**
   * Gets the value of the <b>input '<i>Input2</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input2'
   */
  boolean getInput2();

  /**
   * Gets the definition of the <b>input '<i>Input2</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input2'
   */
  IIoDefinition getInput2Definition();

  /**
   * Gets the value of the <b>input '<i>Input3</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input3'
   */
  boolean getInput3();

  /**
   * Gets the definition of the <b>input '<i>Input3</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input3'
   */
  IIoDefinition getInput3Definition();

  /**
   * Gets the value of the <b>input '<i>Input4</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input4'
   */
  boolean getInput4();

  /**
   * Gets the definition of the <b>input '<i>Input4</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input4'
   */
  IIoDefinition getInput4Definition();

  /**
   * Gets the value of the <b>input '<i>Input5</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input5'
   */
  boolean getInput5();

  /**
   * Gets the definition of the <b>input '<i>Input5</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input5'
   */
  IIoDefinition getInput5Definition();

  /**
   * Gets the value of the <b>input '<i>Input6</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input6'
   */
  boolean getInput6();

  /**
   * Gets the definition of the <b>input '<i>Input6</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input6'
   */
  IIoDefinition getInput6Definition();

  /**
   * Gets the value of the <b>input '<i>Input7</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input7'
   */
  boolean getInput7();

  /**
   * Gets the definition of the <b>input '<i>Input7</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input7'
   */
  IIoDefinition getInput7Definition();

  /**
   * Gets the value of the <b>input '<i>Input8</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input8'
   */
  boolean getInput8();

  /**
   * Gets the definition of the <b>input '<i>Input8</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input8'
   */
  IIoDefinition getInput8Definition();

  /**
   * Gets the value of the <b>input '<i>Input9</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input9'
   */
  boolean getInput9();

  /**
   * Gets the definition of the <b>input '<i>Input9</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input9'
   */
  IIoDefinition getInput9Definition();

  /**
   * Gets the value of the <b>input '<i>Input10</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input10'
   */
  boolean getInput10();

  /**
   * Gets the definition of the <b>input '<i>Input10</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input10'
   */
  IIoDefinition getInput10Definition();

  /**
   * Gets the value of the <b>input '<i>Input11</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input11'
   */
  boolean getInput11();

  /**
   * Gets the definition of the <b>input '<i>Input11</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input11'
   */
  IIoDefinition getInput11Definition();

  /**
   * Gets the value of the <b>input '<i>Input12</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input12'
   */
  boolean getInput12();

  /**
   * Gets the definition of the <b>input '<i>Input12</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input12'
   */
  IIoDefinition getInput12Definition();

  /**
   * Gets the value of the <b>input '<i>Input13</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input13'
   */
  boolean getInput13();

  /**
   * Gets the definition of the <b>input '<i>Input13</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input13'
   */
  IIoDefinition getInput13Definition();

  /**
   * Gets the value of the <b>input '<i>Input14</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input14'
   */
  boolean getInput14();

  /**
   * Gets the definition of the <b>input '<i>Input14</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input14'
   */
  IIoDefinition getInput14Definition();

  /**
   * Gets the value of the <b>input '<i>Input15</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input15'
   */
  boolean getInput15();

  /**
   * Gets the definition of the <b>input '<i>Input15</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input15'
   */
  IIoDefinition getInput15Definition();

  /**
   * Gets the value of the <b>input '<i>Input16</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital input
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the input 'Input16'
   */
  boolean getInput16();

  /**
   * Gets the definition of the <b>input '<i>Input16</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the input 'Input16'
   */
  IIoDefinition getInput16Definition();

  /**
   * Gets the value of the <b>output '<i>Output1</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output1'
   */
  boolean getOutput1();

  /**
   * Sets the value of the <b>output '<i>Output1</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output1'
   */
  void setOutput1(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output1</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output1'
   */
  IIoDefinition getOutput1Definition();

  /**
   * Gets the value of the <b>output '<i>Output2</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output2'
   */
  boolean getOutput2();

  /**
   * Sets the value of the <b>output '<i>Output2</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output2'
   */
  void setOutput2(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output2</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output2'
   */
  IIoDefinition getOutput2Definition();

  /**
   * Gets the value of the <b>output '<i>Output3</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output3'
   */
  boolean getOutput3();

  /**
   * Sets the value of the <b>output '<i>Output3</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output3'
   */
  void setOutput3(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output3</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output3'
   */
  IIoDefinition getOutput3Definition();

  /**
   * Gets the value of the <b>output '<i>Output4</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output4'
   */
  boolean getOutput4();

  /**
   * Sets the value of the <b>output '<i>Output4</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output4'
   */
  void setOutput4(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output4</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output4'
   */
  IIoDefinition getOutput4Definition();

  /**
   * Gets the value of the <b>output '<i>Output5</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output5'
   */
  boolean getOutput5();

  /**
   * Sets the value of the <b>output '<i>Output5</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output5'
   */
  void setOutput5(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output5</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output5'
   */
  IIoDefinition getOutput5Definition();

  /**
   * Gets the value of the <b>output '<i>Output6</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output6'
   */
  boolean getOutput6();

  /**
   * Sets the value of the <b>output '<i>Output6</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output6'
   */
  void setOutput6(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output6</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output6'
   */
  IIoDefinition getOutput6Definition();

  /**
   * Gets the value of the <b>output '<i>Output7</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output7'
   */
  boolean getOutput7();

  /**
   * Sets the value of the <b>output '<i>Output7</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output7'
   */
  void setOutput7(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output7</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output7'
   */
  IIoDefinition getOutput7Definition();

  /**
   * Gets the value of the <b>output '<i>Output8</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output8'
   */
  boolean getOutput8();

  /**
   * Sets the value of the <b>output '<i>Output8</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output8'
   */
  void setOutput8(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output8</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output8'
   */
  IIoDefinition getOutput8Definition();

  /**
   * Gets the value of the <b>output '<i>Output9</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output9'
   */
  boolean getOutput9();

  /**
   * Sets the value of the <b>output '<i>Output9</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output9'
   */
  void setOutput9(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output9</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output9'
   */
  IIoDefinition getOutput9Definition();

  /**
   * Gets the value of the <b>output '<i>Output10</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output10'
   */
  boolean getOutput10();

  /**
   * Sets the value of the <b>output '<i>Output10</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output10'
   */
  void setOutput10(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output10</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output10'
   */
  IIoDefinition getOutput10Definition();

  /**
   * Gets the value of the <b>output '<i>Output11</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output11'
   */
  boolean getOutput11();

  /**
   * Sets the value of the <b>output '<i>Output11</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output11'
   */
  void setOutput11(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output11</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output11'
   */
  IIoDefinition getOutput11Definition();

  /**
   * Gets the value of the <b>output '<i>Output12</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output12'
   */
  boolean getOutput12();

  /**
   * Sets the value of the <b>output '<i>Output12</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output12'
   */
  void setOutput12(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output12</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output12'
   */
  IIoDefinition getOutput12Definition();

  /**
   * Gets the value of the <b>output '<i>Output13</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output13'
   */
  boolean getOutput13();

  /**
   * Sets the value of the <b>output '<i>Output13</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output13'
   */
  void setOutput13(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output13</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output13'
   */
  IIoDefinition getOutput13Definition();

  /**
   * Gets the value of the <b>output '<i>Output14</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output14'
   */
  boolean getOutput14();

  /**
   * Sets the value of the <b>output '<i>Output14</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output14'
   */
  void setOutput14(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output14</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output14'
   */
  IIoDefinition getOutput14Definition();

  /**
   * Gets the value of the <b>output '<i>Output15</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output15'
   */
  boolean getOutput15();

  /**
   * Sets the value of the <b>output '<i>Output15</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output15'
   */
  void setOutput15(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output15</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output15'
   */
  IIoDefinition getOutput15Definition();

  /**
   * Gets the value of the <b>output '<i>Output16</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @return current value of the output 'Output16'
   */
  boolean getOutput16();

  /**
   * Sets the value of the <b>output '<i>Output16</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * <p><b>IO type and direction:</b>
   * digital output
   *
   * <p><b>User description of the IO:</b>
   * ./.
   *
   * <p><b>Range of the IO value:</b>
   * [false; true]
   *
   * @param value
   *        the value, which has to be written to the output 'Output16'
   */
  void setOutput16(boolean value);

  /**
   * Gets the definition of the <b>output '<i>Output16</i>'</b>.
   * <i>This method is automatically generated. Please, do not modify!</i>
   *
   * @return the definition of the output 'Output16'
   */
  IIoDefinition getOutput16Definition();

}
