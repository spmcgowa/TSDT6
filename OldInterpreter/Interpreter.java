import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
//import java.additional.files;
// you may not import additional files

/**
 * An interpreter for the Gloom programming language.
 *
 * @author Justin Cain
 */
public class Interpreter {
	private Stack<Object> mainStack;
	private Stack<Object> retainStack;
	private static final Integer TRUE = -1;
	private static final Integer FALSE = 0;
	
	abstract class Operation {
		abstract public void operate();
		//abstract public static Operation getOp(String rep);
	}
	class EvaluationOperation extends Operation {
		public void operate() {
			stackList((List)mainStack.pop());
			turtles();
		}
	}
	abstract class TernaryOperation extends Operation {
		public void operate() {
			operate(poll(false),poll(false),poll(false));
		}
		abstract public void operate(Object a, Object b, Object c);
	}
	
	
	class TypeOperation<E> extends Operation {
		E t;
		public TypeOperation(E t) {
			this.t = t;
		}
		public void operate() {
			Object a = poll(false);
			mainStack.push((a.getClass().equals(t.getClass())) ? TRUE : FALSE); 
		}
	}
	
	class StackShiftOperation extends Operation {
		boolean toRetain;
		public StackShiftOperation(boolean dir) {
			toRetain = dir;
		}
		public void operate() {
			if (toRetain) {
				retainStack.push(mainStack.pop());
			}
			else {
				mainStack.push(retainStack.pop());
			}
		}
	}
	
	abstract class IntegerUnaryOperation extends Operation{
		public void operate() {
			operate((Integer)poll(true));
		}
		abstract public void operate(Integer a);
	}
	abstract class UnaryOperation extends Operation{
		public void operate() {
			operate(poll(false));
		}
		abstract public void operate(Object a);
	}
	
	class DupOperation extends UnaryOperation {
		public void operate(Object a) {
			mainStack.push(a);
			mainStack.push(a);
		}
	}
	
	class InsertListOperation extends TernaryOperation {
		public void operate(Object a, Object b, Object c) {
			((List<Object>)a).add((int)b,c);
		}
	}
	class IfListOperation extends TernaryOperation {
		public void operate(Object a, Object b, Object c) {
			if (TRUE.equals(c)) {
				mainStack.push(b);
			}
			else {
				mainStack.push(a);
			}
			(new EvaluationOperation()).operate();
		}
	}
	class LoopListOperation extends UnaryOperation {
		public void operate(Object a) {
			List<Object> t = (List<Object>)a;
			while(true) {
				mainStack.push(copyList(t));
				(new EvaluationOperation()).operate();
				if (FALSE.equals(mainStack.pop())) break;
			}
		}
	}
	
	
	class DropOperation extends UnaryOperation {
		public void operate(Object a) {
			//Lets just drop it
		}
	}
	class CopyListOperation extends UnaryOperation {
		public void operate(Object a) {
			List<Object> t = (List<Object>)a;
			mainStack.push(copyList(t));
		}
	}
	class SetListOperation extends TernaryOperation {
		public void operate(Object a, Object b, Object c) {
			((List<Object>)a).set((int)b,c);
		}
	}
	class RemoveListOperation extends BinaryOperation {
		public void operate(Object a, Object b) {
			mainStack.push(((List<Object>)a).remove((int)b));
		}
	}
	class GetListOperation extends BinaryOperation {
		public void operate(Object a, Object b) {
			mainStack.push(((List<Object>)a).get((int)b));
		}
	}
	class SizeListOperation extends UnaryOperation {
		public void operate(Object a) {
			mainStack.push(((List<Object>)a).size());
		}
	}
	class AppendListOperation extends BinaryOperation {
		public void operate(Object a, Object b) {
			List<Object> t1 = (List<Object>)b;
			List<Object> t2 = (List<Object>)a;
			List<Object> n = new List<>();
			for (int i = 0; i < t1.size(); i++) {
				Object c = t1.get(i);
				if (b instanceof Integer) {
					//Make sure it copies instead of adding reference since we are using Integer class rather than primitive
					n.add(Integer.valueOf((int)c));
				}
				else {
					n.add(c);
				}
			}
			//Do the same for second list
			for (int i = 0; i < t2.size(); i++) {
				Object c = t2.get(i);
				if (b instanceof Integer) {
					n.add(Integer.valueOf((int)c));
				}
				else {
					n.add(c);
				}
			}
			mainStack.push(n);

		}
	}
	
	class VariableOperation extends BinaryOperation {
		public void operate(Object a, Object b) {
			List<Object> t1 = (List<Object>)b;
			List<Object> t2 = (List<Object>)a;
			for (int i = 0; i < t1.size(); i++) {
				String key = (String)t1.get(i);
				Variables.put(key, t2);
			}
		}
	}
	
	
	abstract class IntegerBinaryOperation extends Operation {
		public void operate() {
			operate((Integer)poll(true),(Integer)poll(true));
		}
		abstract public void operate(Integer a,Integer b);
	}
	abstract class BinaryOperation extends Operation {
		public void operate() {
			operate(poll(false),poll(false));
		}
		abstract public void operate(Object a,Object b);
	}
	
	
	class PlusOperation extends IntegerBinaryOperation {
		public void operate(Integer a, Integer b) {
			mainStack.push(b + a);
		}
	}
	class SubOperation extends IntegerBinaryOperation {
		public void operate(Integer a, Integer b) {
			mainStack.push(b - a);
		}
	}
	class MulOperation extends IntegerBinaryOperation {
		public void operate(Integer a, Integer b) {
			mainStack.push(b * a);
		}
	}
	class DivOperation extends IntegerBinaryOperation {
		public void operate(Integer a, Integer b) {
			mainStack.push(b / a);
		}
	}
	class ModOperation extends IntegerBinaryOperation {
		public void operate(Integer a, Integer b) {
			while (b > a) b -= a;
			mainStack.push(b);
		}
	}
	class GreaterOperation extends IntegerBinaryOperation {
		public void operate(Integer a, Integer b) {
			// 0 is false -1 is true
			mainStack.push((a < b) ? TRUE : FALSE);
		}
	}
	class OverOperation extends BinaryOperation {
		public void operate(Object a, Object b) {
			mainStack.push(b);
			mainStack.push(a);
			mainStack.push(b);
		}
	}
	
	class SwapOperation extends BinaryOperation {
		public void operate(Object a, Object b) {
			mainStack.push(a);
			mainStack.push(b);
		}
	}
	
	public static HashMap<String, List<Object>> Variables;

	/**
	 * Constructs an interpreter!
	 */
	public Interpreter() {
		mainStack = new Stack<Object>();
		retainStack = new Stack<Object>();
		Variables = new HashMap<>();
		
		for (Object[] t : new Object[][] {	{"eval"		, new EvaluationOperation()						},
											{ "+"		, new PlusOperation()							},
											{ "-"		, new SubOperation()							},
											{ "*"		, new MulOperation()							},
											{ "/"		, new DivOperation()							},
											{ "mod"		, new ModOperation()							},
											{ ">"		, new GreaterOperation()						},
											{ "dup"		, new DupOperation()							},
											{ "drop"	, new DropOperation()							},
											{ "over"	, new OverOperation()							},
											{ "swap"	, new SwapOperation()							},
											{ "r>"		, new StackShiftOperation(false)				},
											{ ">r"		, new StackShiftOperation(true)					},
											{ "int?"	, new TypeOperation<Integer>(Integer.valueOf(0))},
											{ "list?"	, new TypeOperation<List>(new List())			},
											{ "insert"	, new InsertListOperation()						},
											{ "copy"	, new CopyListOperation()						},
											{ "set"		, new SetListOperation()						},
											{ "remove"	, new RemoveListOperation()						},
											{ "get"		, new GetListOperation()						},
											{ "size"	, new SizeListOperation()						},
											{ "append"	, new AppendListOperation()						},
											{ "if"		, new IfListOperation()							},
											{ "loop"		, new LoopListOperation()					},
											{ "!"		, new VariableOperation()						}} ) {
			List<Object> temp = new List<>();
			temp.add((Object)t[1]);
			Variables.put((String)t[0],temp);
		}
		
		
		//evaluate(new Scanner(""));
		//evaluate(new Scanner("1 -42 2 -12"));
		//System.out.println(mainStack.getClass());
	}
	private List<Object> copyList(List<Object> t) {
		List<Object> n = new List<>();
		for (int i = 0; i < t.size(); i++) {
				Object b = t.get(i);
				if (b instanceof Integer) {
					n.add(Integer.valueOf((int)b));
				}
				else {
					n.add(b);
				}
		}
		return n;
	}
	
	/**
	 * Evaluates the list of tokens given from an imported file or
	 * read-evaluate-print loop.
	 *
	 * @param scanner the tokenizer
	 */
	public void evaluate(Scanner scanner) {
		List<Object> main = parseList(scanner);
		evalList(main);
	}
	private List<Object> parseList(Scanner scanner) {
		List<Object> list = new List<>();
		while (scanner.hasNext()) {
			//System.out.println("IMA PARSING YOUR LIST");
			if (scanner.hasNextInt()) {
				list.add(Integer.valueOf(scanner.nextInt()));
			}
			else {
				Object token = scanner.next();
				if (("[").equals(token)) {
					list.add(parseList(scanner));
				}
				else if (("(").equals(token)){
					int commentCount = 1;
					while (commentCount != 0) {
						String burnToken = scanner.next();
						if (("(").equals(burnToken)) {
							commentCount++;
						} 
						else if ((")").equals(burnToken)) {
							commentCount--;
						}
					}
					
				}
				else if (("]").equals(token)) {
					break;
				}
				else {
					list.add(token);
				}
			}
		}
		return list;
	}
	
	public void evalList(List<Object> list) {
		stackList(list);
		
		turtles();
	}
	private void turtles() {
		if (mainStack.top() == null) { 
			return;
		}
		
		Stack<Object> backStack = new Stack<>();
		
		
		while (mainStack.isEmpty() == false) {
			backStack.push(mainStack.pop()); 
		}
		
		while (backStack.isEmpty() == false){
			if (backStack.top() instanceof String) {
				String variable = (String)backStack.pop();
				if (Variables.containsKey(variable)) {
					putVariable(variable);
				}
				else {
					throw new IllegalArgumentException();
				}
			}
			else if (backStack.top() instanceof Operation) {
				((Operation)backStack.pop()).operate();
			}
			else {
				mainStack.push(backStack.pop());
			}
		}
	}
	
	private void putVariable(String key) {
		mainStack.push(Variables.get(key));
		(new EvaluationOperation()).operate();
	}
	
	private void stackList(List<Object> list) {
		for (int i = 0; i < list.size(); i++) {
			mainStack.push(list.get(i));
		}
	}
	private Object poll(boolean intOnly) {
		// We get an integer off the stack at all costs!!!
		if (mainStack.top() instanceof Integer) {
			return mainStack.pop();
		}
		else if (mainStack.top() instanceof List) {
			if (intOnly) {
				stackList((List)mainStack.pop());
				return poll(intOnly);
			}
			else {
				return mainStack.pop();
			}
		} 
		else {
			turtles();
			return poll(intOnly);
		}
	}
	
	/**
	 * Return's the interpreter's stack for use by the read-evaluate-print loop.
	 *
	 * @return the primary stack (not the retain stack)
	 */
	public Stack<Object> stack() {
		return mainStack;
	}
}