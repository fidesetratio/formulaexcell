package com.app.rule.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import com.app.ExcelParam;
import com.app.rule.ProcessRule;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class RuleUtility {
		private ArrayList<Rule> availableRules;
		private HashMap<String,ArrayList<Rule>> tagsAvailable;
		private ArrayList<String> keys;
		private HashMap<String,Object> inputs;
		private ArrayList<String> jsonKeys;
		private String referensiKey;
		private ExcelParam excelParam;
		private HashMap<String,String> excelTmpParam;
		public RuleUtility(String referensiKey) {
			availableRules = new ArrayList<Rule>();
			tagsAvailable = new HashMap<String, ArrayList<Rule>>();
			this.keys = new ArrayList<String>();
			this.inputs = new HashMap<String, Object>();
			this.referensiKey  = referensiKey;
			this.jsonKeys = new ArrayList<String>();
			this.excelParam = new ExcelParam();
			excelTmpParam = new HashMap<String, String>();
		}
		
		public void add(Rule rule) {
					String tag = rule.getTag();
					rule.setReferensiKey(this.referensiKey);
					if(rule.getTag().equals("input")) {
						rule.setType(0);
					};
					if(tagsAvailable.get(tag) ==  null) {
						tagsAvailable.put(tag, new ArrayList<Rule>());
					}
				tagsAvailable.get(tag).add(rule);
		}


		public HashMap<String,Object> getStdIn(){
			return this.inputs;
		}
		public ExcelParam getStdInExcel(){
			
			return this.excelParam;
		}
		
		public void sortAllRule() {
				for(String k:tagsAvailable.keySet()) {
						if(tagsAvailable.get(k)!=null) {
							ArrayList<Rule> t = tagsAvailable.get(k);
							t = sorted(t);
							tagsAvailable.put(k, t);
						}
				}
			
		}
		

		public ArrayList<String> getTags() {
			ArrayList<String> tags = new ArrayList<String>();
			for(String k: tagsAvailable.keySet()) {
					tags.add(k);
				}
			return tags;
		}
		
		
		
		public boolean consumeJson(String json) {
			Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
			boolean result = false;
			for(int counter = 1;counter<=this.keys.size();counter++) {
				try {
				String key = this.keys.get(counter);
				String jsonKey = this.jsonKeys.get(counter);
				Object object = JsonPath.read(document, "$."+jsonKey);
			    if(object != null) {
			    	this.inputs.put(key, object);
			    	excelParam.put(key, object);
			    	result = true;
			    }
				}catch(Exception e) {
					continue;
				}
				
			}
			
			return result;
			
		}
		
		public ArrayList<Rule> getTagByName(String tagName){
			ArrayList<Rule> t  = new ArrayList<Rule>();
			if(tagsAvailable.get(tagName)!=null) {
				return sorted(tagsAvailable.get(tagName));
				
			}
			return null;
			
		}
		
		
		public ArrayList<String> getJsonInput(){
			return this.jsonKeys;
		}
		
		public void addInput(String key,Object object) {
			if(keys.contains(key)) {
				this.inputs.put(key, object);
				this.excelParam.put(excelTmpParam.get(key), object);
			}else {
				throw new IllegalArgumentException("Key is not found");
			}
		}
		
		
		
		
		
		
		public ArrayList<String> getInputs(){
			return this.keys;
		}
		
		public void initPopulate() {
			ArrayList<Rule> input = getTagByName("input");
		
			for(Rule r:input) {
				keys.add(r.getVariableMvel());
			    jsonKeys.add(r.getJsonKey());
			    this.inputs.put(r.getVariableMvel(), r.getDefaultValue());
			    excelTmpParam.put(r.getVariableMvel(), r.getVariableExcell());
			}
			
			
		
		}
		
		
		private ArrayList<Rule> sorted(ArrayList<Rule> rules){
			Collections.sort(rules,Rule.RuleComparator);
			return rules;
		}
		
		public static Rule addRuleInput(Integer ruleId, String referensiKey, String variableMvel, String variableExcell, String jsonKey, int priority) {
			Rule rule = new Rule(ruleId, referensiKey, variableMvel, variableExcell, "input", 0, priority, true, jsonKey, null, 0, new Date());
			return rule;
		}
		public static Rule addRuleInput(Integer ruleId, String referensiKey, String variableMvel, String jsonKey, int priority, Object defaultValue) {
			Rule rule = new Rule(ruleId, referensiKey, variableMvel, null, "input", 0, priority, true, jsonKey, null, 0, new Date());
			if(defaultValue != null) {
				rule.setDefaultValue(defaultValue);
			}
			return rule;
		}
		public static Rule addRuleOutputMvel(Integer ruleId, String referensiKey,String tagName, String variableMvel,String variableExcell,String scriptValue,String jsonKey, int priority, Object defaultValue) {
			Rule rule = new Rule(ruleId, referensiKey, variableMvel, variableExcell, tagName, 1, priority, true, jsonKey, scriptValue, 0, new Date());
			if(defaultValue != null) {
				rule.setDefaultValue(defaultValue);
			}
			return rule;
		}
		
		public static Rule addRuleOutputExcel(Integer ruleId, String referensiKey,String tagName, String variableMvel,String variableExcell,String scriptValue,String jsonKey, int priority, Object defaultValue) {
			Rule rule = new Rule(ruleId, referensiKey, variableMvel, variableExcell, tagName, 1, priority, true, jsonKey, scriptValue, 1, new Date());
			if(defaultValue != null) {
				rule.setDefaultValue(defaultValue);
			}
			return rule;
		}
		
		public static void main(String args[]) {
			String referensiKey = "xxxxxxx";
			RuleUtility utility = new RuleUtility(referensiKey);
			
			Rule r = RuleUtility.addRuleInput(1, referensiKey, "a","A1", "amount", 0);
			utility.add(r);
			r = RuleUtility.addRuleInput(2, referensiKey, "b","B1", "database", 1);
			utility.add(r);
			r = RuleUtility.addRuleInput(3, referensiKey, "c","C1", "patartimotius", 2);
			utility.add(r);
			r = RuleUtility.addRuleInput(4, referensiKey, "d", "D1","keylogger", 3);
			utility.add(r);
			
			
			Rule outputRule = RuleUtility.addRuleOutputMvel(6, referensiKey, "input_error", "FUCK","A20", "return 2030+a;","fuck" ,0, "");
			
			utility.add(outputRule);
			 outputRule = RuleUtility.addRuleOutputMvel(7, referensiKey, "input_error", "YUCK","A21", "return FUCK+1;","fuck2" ,1, "");
			 utility.add(outputRule);
			 outputRule = RuleUtility.addRuleOutputExcel(8, referensiKey, "input_error", "YUCK2","A22", "A20+1","fuck3" ,2, "");
			 utility.add(outputRule);
			 outputRule = RuleUtility.addRuleOutputMvel(9, referensiKey, "input_error", "YUCK22","A23", "return c.toUpperCase();","fuck4" ,3, "");
			 utility.add(outputRule);
			 
			utility.sortAllRule();
			utility.initPopulate();
			
			utility.addInput("a", 500);
			utility.addInput("c", "Patar Timotius");
			
			for(Rule rt:utility.getTagByName("input")) {
					System.out.println(rt.getPriority());
			}
			utility.consumeJson("{\"database\" : 1411455611975,\"keylogger\":1}");
			
			
			System.out.println(utility.getInputs());
			System.out.println(utility.getJsonInput());
			System.out.println(utility.getStdIn());
			
			System.out.println("input nya dulu lah");

			ProcessRule processRule = new ProcessRule(utility,Arrays.asList("input_error","hidden","output"));
			
			processRule.processAsLog();
			
		}
}
