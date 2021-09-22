package com.app.rule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.app.ExcelParam;
import com.app.ExcelUtility;
import com.app.MvelUtility;
import com.app.rule.model.Rule;
import com.app.rule.model.RuleUtility;

public class ProcessRule {
	private RuleUtility ruleutility;
	private List<String> tags;
	
	public ProcessRule(RuleUtility ruleUtility, List<String> tags) {
		this.ruleutility = ruleUtility;
		this.tags = tags;
	}

	
	public void processAsLog() {
		/**
		 * 1. yang tipe input akan di jalan kan duluan sesuai dengan urutannya masuk ke mvel
		 */
		
		
		HashMap<String,Object> input = ruleutility.getStdIn();
		ExcelParam excelparam = ruleutility.getStdInExcel();
		HashMap<String,HashMap<String,Object>> output = new HashMap<String, HashMap<String,Object>>();
		
		
		for(String tag:tags) {
			ArrayList<Rule> rs = ruleutility.getTagByName(tag);
			if(rs == null) {
				continue;
			}
				if(rs.size()>0) {
				if(output.get(tag) == null) {
					output.put(tag, new HashMap<String, Object>());
				}
				HashMap<String,Object> outputTag = output.get(tag);
				for(Rule r:rs) {
					String variableMvel = r.getVariableMvel();
					String scriptMvel = r.getScriptValue();
					if(r.getScriptType() == 0 ) { // mvel
						if(input.get(variableMvel)==null) {
						Object val = null;
						try {
							val = MvelUtility.eval(scriptMvel, input);
							input.put(variableMvel, val);
							excelparam.put(r.getVariableExcell(), val);
							outputTag.put(variableMvel, val);
							
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						};
					}else if(r.getScriptType() == 1) { // excell
						if(input.get(variableMvel)==null) {
							Object val = null;
							try {
								val = ExcelUtility.eval(excelparam, scriptMvel);
								input.put(variableMvel, val);
								outputTag.put(variableMvel, val);
								excelparam.put(r.getVariableExcell(), val);
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							};
					}
					
					
				}
			}
		}
		
		
		for(String k:output.keySet()) {
			System.out.println("tag : "+k);
			HashMap<String,Object> t = output.get(k);
			for(String ks:t.keySet()) {
				System.out.println("       "+ks+" "+t.get(ks));
					
			}
		}
		
		
	}
	public String processAsJson() {
		String json = "";
		return json;
	}
	public HashMap<String,Object> processAsMap() {
			HashMap<String,Object> maps = new HashMap<String, Object>();
			return maps;
	}
}
