package com.app;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Name;

import org.apache.poi.ss.formula.BaseFormulaEvaluator;
import org.apache.poi.ss.formula.WorkbookEvaluator;

import org.apache.poi.ss.formula.eval.*;

import org.apache.poi.ss.util.CellReference;

public class EvaluateExcelFunctions {

 static Object evaluateExcelFormula(String formula, Workbook workbookWithVariables) {
  if (workbookWithVariables.getNumberOfSheets() < 1) workbookWithVariables.createSheet();
  //CellReference reference = new CellReference(workbookWithVariables.getSheetName(0), 0 , 0, false, false);
  Cell cell = workbookWithVariables.getSheet(workbookWithVariables.getSheetName(0)).createRow(0).createCell(0);
  CellReference reference = new CellReference(cell);
  CreationHelper helper = workbookWithVariables.getCreationHelper();
  FormulaEvaluator formulaevaluator = helper.createFormulaEvaluator();
  WorkbookEvaluator workbookevaluator = ((BaseFormulaEvaluator)formulaevaluator)._getWorkbookEvaluator();
  ValueEval valueeval = null;
  try {
   valueeval = workbookevaluator.evaluate(formula, reference);
  } catch (Exception ex) {
   return ex.toString();
  }
  if (valueeval instanceof StringValueEval) {
   String result = ((StringValueEval)valueeval).getStringValue();
   return result;
  } else if (valueeval instanceof NumericValueEval) {
   double result = ((NumericValueEval)valueeval).getNumberValue();
   return result;
  } else if (valueeval instanceof ErrorEval) {
   String result = ((ErrorEval)valueeval).getErrorString();
   return result;
  }
  return null;  
 }

 public static void main(String[] args) throws Exception {

  Workbook workbook = 
   //new XSSFWorkbook();
   new HSSFWorkbook();

  Name name;
  String formula;
  Object result;

  // example 1 concatenating strings - your example
  name = workbook.createName();
  name.setNameName("_A1");
  name.setRefersToFormula("\"Text A \"");
  name = workbook.createName();
  name.setNameName("_B1");
  name.setRefersToFormula("\"Text B \"");
  name = workbook.createName();
  name.setNameName("_C1");
  name.setRefersToFormula("\"Text C \"");

  formula = "CONCATENATE(_A1, _B1, _C1)";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  // example 2 Pythagorean theorem
  name = workbook.getName("_A1"); 
  name.setRefersToFormula("12.34");
  name = workbook.getName("_B1");
  name.setRefersToFormula("56.78");

  formula = "SQRT(_A1^2 + _B1^2)";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  // example 3 complex math formula
  name = workbook.getName("_A1"); 
  name.setRefersToFormula("12.34");
  name = workbook.getName("_B1");
  name.setRefersToFormula("56.78");
  name = workbook.getName("_C1");
  name.setRefersToFormula("90.12");

  formula = "((_A1+_B1+_C1)*_C1/_B1-_A1)/2";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  // example 4 faulty formulas
  name = workbook.getName("_A1"); 
  name.setRefersToFormula("56.78");
  name = workbook.getName("_B1");
  name.setRefersToFormula("190.12");
  name = workbook.getName("_C1");
  name.setRefersToFormula("\"text\"");

  formula = "_A1 + _C1 ";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  formula = "((_A1 + _B1";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  formula = "_A1 \\ 2";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  formula = "_A1^_B1";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  formula = "_A1/(_B1-_B1)";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  formula = "CONCAT(_A1, _B1)";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  
  formula = "IF(1>20,\"YES\",\"NO\")";
  result = evaluateExcelFormula(formula, workbook);
  System.out.println(result);

  workbook.close();
 }  
}