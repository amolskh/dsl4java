package org.dsl;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;


/**
 * Hello world!
 *
 */
public class App extends Object
{
    public static void main( String[] args )
    {
    	//ReflectionUtils.getAll("org.dsl", Predicates.equalTo(true));
    	Reflections reflections = new Reflections(new ConfigurationBuilder().filterInputsBy(new FilterBuilder().includePackage("org.dsl"))
    			.setUrls(ClasspathHelper.forPackage("org.dsl"))
    			.setScanners(new SubTypesScanner(),new MethodAnnotationsScanner(),new TypeElementsScanner(),new TypeAnnotationsScanner()));
    	 /* Set<Class<? extends TestCase>> subTypes = 
                  reflections.getSubTypesOf(junit.framework.TestCase.class);
    	  Iterator it = subTypes.iterator();
		while(it.hasNext())
		{
			Class c = (Class) it.next();
			System.out.println(c.getName());
		}*/
		//reflections = new Reflections("org.dsl");
		System.out.println(reflections.getMethodsAnnotatedWith(Deprecated.class).size());
    }
}
