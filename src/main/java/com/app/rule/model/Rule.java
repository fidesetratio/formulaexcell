package com.app.rule.model;

import java.util.Comparator;
import java.util.Date;

public class Rule {


	private Integer ruleId;
	private String referensiKey;
	private String variableMvel;
	private String variableExcell;
	private String tag;
	private Integer type; //input = 0, ouput = 1 ; hidden = 3;
	private Integer priority;
	private Boolean active;
	private String jsonKey;
	private String scriptValue;
	private Integer scriptType; // 0 mvel; 1; excell
	private Date createdDate;
	private Object defaultValue;
	
	public Rule() {
		this.priority = 0;
		this.defaultValue = null;
	}
	
	public Rule(Integer ruleId, String referensiKey, String variableMvel, String variableExcell, String tag,
			Integer type, Integer priority, Boolean active, String jsonKey, String scriptValue, Integer scriptType,
			Date createdDate) {
		super();
		this.ruleId = ruleId;
		this.referensiKey = referensiKey;
		this.variableMvel = variableMvel;
		this.variableExcell = variableExcell;
		this.tag = tag;
		this.type = type;
		this.priority = priority;
		this.active = active;
		this.jsonKey = jsonKey;
		this.scriptValue = scriptValue;
		this.scriptType = scriptType;
		this.createdDate = createdDate;
	}
	
	
	
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public String getReferensiKey() {
		return referensiKey;
	}
	public void setReferensiKey(String referensiKey) {
		this.referensiKey = referensiKey;
	}
	public String getVariableMvel() {
		return variableMvel;
	}
	public void setVariableMvel(String variableMvel) {
		this.variableMvel = variableMvel;
	}
	public String getVariableExcell() {
		return variableExcell;
	}
	public void setVariableExcell(String variableExcell) {
		this.variableExcell = variableExcell;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getJsonKey() {
		return jsonKey;
	}
	public void setJsonKey(String jsonKey) {
		this.jsonKey = jsonKey;
	}
	public String getScriptValue() {
		return scriptValue;
	}
	public void setScriptValue(String scriptValue) {
		this.scriptValue = scriptValue;
	}
	public Integer getScriptType() {
		return scriptType;
	}
	public void setScriptType(Integer scriptType) {
		this.scriptType = scriptType;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	

    public static Comparator<Rule> RuleComparator = new Comparator<Rule>() {

	public int compare(Rule rule1, Rule rule2) {
	 
		int result = -1;
		if(rule1.getPriority()== rule1.getPriority()) {
			result = 0;
		}
		if(rule1.getPriority()> rule1.getPriority()) {
			result = 1;
		}
		
		return result;
	
	}};

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}


}
