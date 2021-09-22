package com.app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;
import org.mvel2.ParserConfiguration;
import org.mvel2.ParserContext;

import com.app.utils.F;
/**
 * 
 * tag  = pemegang, tertanggung, product
type = input 
       hidden
       output
script type = mvel,excell

rule_id, referensi_key,variable_mvel, variable_excell, tag, type (default = input), priority_execution,json_key
 script_value, script_type created_date
 * @author Patar.Tambunan
 *
 */
public class MvelUtility {

	public static Object eval(String str, Map<String, Object> vars) throws NoSuchMethodException, SecurityException {
		  ParserConfiguration pconf = new ParserConfiguration();
		 // pconf.addPackageImport("org.kie.internal.task.api.model");
		 // pconf.addPackageImport("org.jbpm.services.task");
		 // pconf.addPackageImport("org.jbpm.services.task.impl.model");
		 // pconf.addPackageImport("org.jbpm.services.task.query");
		 // pconf.addPackageImport("org.jbpm.services.task.internals.lifecycle");
		 // pconf.addImport(Status.class);
		 // pconf.addImport(Allowed.class);
		 // pconf.addPackageImport("java.util");
		  pconf.addPackageImport("com.app.utils");

		  pconf.addImport("time", System.class.getMethod("currentTimeMillis"));
		  pconf.addImport("HELLOSTRING", F.class.getMethod("helloString",String.class) );
		  pconf.addImport("GOODHELLO", F.class.getMethod("goodOhGood", Integer.class,Integer.class) );
		  
		  ParserContext context = new ParserContext(pconf);
		  Serializable s = MVEL.compileExpression(str.trim(), context);
		  if (vars != null) {
		    return  MVEL.executeExpression(s, vars);
		  } else {
	
			  return  MVEL.executeExpression(s);
		  }
		}
	
	
	public static void main(String args[]) throws NoSuchMethodException, SecurityException {
			Object b = MvelUtility.eval("System.out.println(HELLOSTRING('Patar Ganteng'));return 1;", new HashMap<String, Object>());
			System.out.println(b);
	};
	
}

