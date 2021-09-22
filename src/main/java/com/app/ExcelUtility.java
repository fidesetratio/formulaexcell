package com.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.BaseFormulaEvaluator;
import org.apache.poi.ss.formula.WorkbookEvaluator;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.formula.eval.NumericValueEval;
import org.apache.poi.ss.formula.eval.StringValueEval;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

public class ExcelUtility {

	public static Object evaluateExcelFormula(String formula, Workbook workbookWithVariables) {
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
	
	
	
	public static Object eval(HashMap<String,Object> params,String formula) throws IOException {
		  Workbook workbook = new HSSFWorkbook();
		  Name name;
		  Object result = null;

		  for(String k:params.keySet()) {
			  Object v = params.get(k);
			  if(v instanceof String) {
				  String t = (String)v;
				  name = workbook.createName();
				  name.setNameName(k);
				  name.setRefersToFormula("\""+t+"\"");
			  }else if (v instanceof Integer) {
				  String t = Integer.toString((Integer)v);
				  name = workbook.createName();
				  name.setNameName(k);
				  name.setRefersToFormula(t);
			  }else  if (v instanceof Double) {
				  String t = Double.toString((Double)v);
				  name = workbook.createName();
				  name.setNameName(k);
				  name.setRefersToFormula(t);
			
			  }
		  }
		  
		  result = ExcelUtility.evaluateExcelFormula(formula, workbook);
		  workbook.close(); 
		  
		  return result;
	}


	public static Object eval(ExcelParam param, String formula) throws IOException {
			return ExcelUtility.eval(param.convertToMap(), formuleLike(formula));
	}
	public static String formuleLike(String formula) {
		String d = formula;
		Pattern regex = Pattern.compile("([A-Z][0-9]+)");
		Matcher m = regex.matcher(formula);
		HashSet<String> s= new HashSet<String>();
		while(m.find()) {
			String id= m.group(1);
			s.add(id);
		}
		for(String ss:s) {
			d = d.replaceAll(ss, "_"+ss);
		}
		
		d = d.replaceAll(";",",");
		
		return d;
	}
	public static String extractDefined(String expression) {
		String result = null;
		
		Pattern regex = Pattern.compile("^[^\\[{(,]*");
		Matcher regexMatcher = regex.matcher(expression);
		if (regexMatcher.find()) {
			result = regexMatcher.group();
		    //System.out.println(regexMatcher.group());
		}
		
		return result;
	}
	
	
	/*public static void main(String args[]) throws IOException {
		
		System.out.println(formuleLike("IF(E10=1,12,IF(E10=2,4,IF(E10=3,2,1)))"));
		System.out.println(formuleLike("CONCATENATE(A1,B2,C3)"));
		
		System.out.println(ExcelUtility.extractDefined("_E11*2*IF(_E10=1,12,IF(_E10=2,4,IF(_E10=3,2,1)))"));
		HashMap<String,Object> params = new HashMap<String, Object>();
		params.put("_A1", "patar timotius tambunan");
		params.put("_B2", "is");
		params.put("_C3", "Ganteng");
		String formula = "CONCATENATE(_A1,_B2,_C3)";
		System.out.println(ExcelUtility.eval(params, formula));
		
		params.clear();
		params.put("_A22", 100);
		params.put("_A21", 10);
		formula = "_A22*_A21";
		System.out.println(ExcelUtility.eval(params, formula));
		
		params.clear();
		params.put("_A22", 100);
		params.put("_A21", 10);
		formula = "_A22+_A21";
		System.out.println(ExcelUtility.eval(params, formula));
			
		params.clear();
		params.put("_E10", 1);
		params.put("_E11", 100);
		params.put("_E12", 10);
		formula = "_E11*2*IF(_E10=1,12,IF(_E10=2,4,IF(_E10=3,2,1)))";
		System.out.println(ExcelUtility.eval(params, formula));
		
	}*/
	public static void main(String args[]) throws IOException {
		ExcelParam param = new ExcelParam();
		param.put("A1", "patartimoitus");
		param.put("A2", " is ");
		
		param.put("A3", "ganteng");
		
		System.out.println(ExcelUtility.eval(param, "CONCATENATE(A1,A2,A3)"));
		
		param.clear();
		param.put("E10", 1);
		param.put("E11", 100);
		param.put("E12", 10);
	
		System.out.println(ExcelUtility.eval(param, "E11*2*IF(E10=1,12,IF(E10=2,4,IF(E10=3,2,1)))"));
		param.clear();
		
		param.put("E10", 1.3);
		param.put("E11", 100.5);
		param.put("E12", 10.6);
		System.out.println(ExcelUtility.eval(param, "E11*2*IF(E10=1,12,IF(E10=2,4,IF(E10=3,2,1)))"));
			
	}
	
}
